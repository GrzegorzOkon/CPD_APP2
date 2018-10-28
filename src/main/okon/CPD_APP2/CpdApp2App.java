package okon.CPD_APP2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class CpdApp2App {
    private final ConnectionFactory connectionFactory;

    public CpdApp2App() {
        this(new ConnectionFactory());
    }

    public CpdApp2App(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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
            Message message = null;

            message = checkService((String)properties.get(key), (String)key, allChecks);
            checkingDetails.add(message);
        }

        return checkingDetails;
    }

    public Message checkService(String url, String description, int allChecks) {
        int correctChecksCounter = 0;

        for (int i = 0; i < allChecks; i++) {
            if (isCorrectService(url, description)) {
                correctChecksCounter++;
            }
        }

        return new Message(url, description, correctChecksCounter, allChecks);
    }

    public boolean isCorrectService(String url, String description) {
        try (HttpConnection connection = connectionFactory.build(url)) {
            try {
                String response = connection.response();
                if (response != null) {
                    return true;
                }
            } catch (AppException e) {}
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
}