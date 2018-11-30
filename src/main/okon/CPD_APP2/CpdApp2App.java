package okon.CPD_APP2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class CpdApp2App {
    private final ConnectionFactory httpConnectionFactory;
    private final ConnectionFactory httpsConnectionFactory;
    private final ConnectionFactory domainHttpConnectionFactory;
    private final ConnectionFactory domainHttpsConnectionFactory;

    public CpdApp2App() {
        this(new HttpConnectionFactory(), new HttpsConnectionFactory(), new DomainHttpConnectionFactory(), new DomainHttpsConnectionFactory());
    }

    public CpdApp2App(HttpConnectionFactory httpConnectionFactory, HttpsConnectionFactory httpsConnectionFactory,
        DomainHttpConnectionFactory domainHttpConnectionFactory, DomainHttpsConnectionFactory domainHttpsConnectionFactory) {
        this.httpConnectionFactory = httpConnectionFactory;
        this.httpsConnectionFactory = httpsConnectionFactory;
        this.domainHttpConnectionFactory = domainHttpConnectionFactory;
        this.domainHttpsConnectionFactory = domainHttpsConnectionFactory;
    }

    public static void main(String[] args) {
        CpdApp2App cpd_app2 = new CpdApp2App();

        Properties properties = cpd_app2.loadProperties();

        List<Message> services = cpd_app2.checkAllServices(properties, 5);

        Comparator<Message> byUrlComparator = (m1, m2) -> m1.url.compareTo(m2.url);
        Collections.sort(services, byUrlComparator);

        cpd_app2.save("CPD_APP2.txt", services);
    }

    public List<Message> checkAllServices(Properties properties, int allChecks) {
        List<Message> checkingDetails = new ArrayList<>();

        for (Object key : properties.keySet()) {
            HttpDetails details = deformat((String)key, (String)properties.get(key));

            Message message = checkService(details, allChecks);
            checkingDetails.add(message);
        }

        return checkingDetails;
    }

    public Message checkService(HttpDetails details, int allChecks) {
        int correctChecksCounter = 0;
        ConnectionFactory connectionFactory = chooseFactory(details);

        for (int i = 0; i < allChecks; i++) {
            if (isCorrectService(details, connectionFactory)) {
                correctChecksCounter++;
            }
        }

        return new Message(details.getUrl(), details.getDescription(), correctChecksCounter, allChecks);
    }

    public ConnectionFactory chooseFactory(HttpDetails details) {
        if (details.getUrl().contains("http://") && details.getLogin() == null) {
            return httpConnectionFactory;
        } else if (details.getUrl().contains("http://") && details.getLogin() != null) {
            return domainHttpConnectionFactory;
        } else if (details.getUrl().contains("https://") && details.getLogin() == null) {
            return httpsConnectionFactory;
        } else {
            return domainHttpsConnectionFactory;
        }
    }

    public boolean isCorrectService(HttpDetails details, ConnectionFactory connectionFactory) {
        try (Connection connection = connectionFactory.build(details)) {
            try {
                String response = connection.response();
                if (response != null) {
                    return true;
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("params/addresses.properties"));
        } catch (Exception e) {
            throw new AppException(e);
        }

        return properties;
    }

    public void save(String fileName, List<Message> content) {
        try (FileOutputStream out = new FileOutputStream(new java.io.File(fileName))) {
            for(Message message : content) {
                List<byte[]> formattedText = new TxtFormatter(message).format();

                out.write(formattedText.get(0));
                out.write(System.getProperty("line.separator").getBytes());
                out.write(formattedText.get(1));
                out.write(System.getProperty("line.separator").getBytes());
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public HttpDetails deformat(String key, String value) {
        if (!value.contains(",")) {
            return new HttpDetails(key, value);
        } else {
            String url = value.substring(0, value.indexOf(','));
            String login = value.substring(value.indexOf(',') + 1, value.lastIndexOf(','));
            String password = value.substring(value.lastIndexOf(',') + 1);

            return new HttpDetails(key, url, login, password);
        }
    }
}