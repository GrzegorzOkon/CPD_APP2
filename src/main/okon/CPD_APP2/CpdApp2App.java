package okon.CPD_APP2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class CpdApp2App {
    static final ConnectionFactory httpConnectionFactory = new HttpConnectionFactory();
    static final ConnectionFactory httpsConnectionFactory = new HttpsConnectionFactory();
    static final ConnectionFactory domainHttpConnectionFactory = new DomainHttpConnectionFactory();
    static final ConnectionFactory domainHttpsConnectionFactory = new DomainHttpsConnectionFactory();

    static final Queue<HttpDetailsJob> webserviceQueue = new LinkedList();
    static final List<Message> messageList = new ArrayList();

    static final int checkingSumForWebservice = 5;

    public static void main(String[] args) {
        CpdApp2App cpd_app2 = new CpdApp2App();

        Properties properties = cpd_app2.loadProperties();
        cpd_app2.rewriteToWebservicesQueue(properties);

        cpd_app2.startThreadPool(4);

        Comparator<Message> byUrlComparator = (m1, m2) -> m1.url.compareTo(m2.url);
        Collections.sort(messageList, byUrlComparator);

        cpd_app2.save(cpd_app2.getJarFileName() + ".txt", messageList);
    }

    public void startThreadPool(int threadSum) {
        Thread[] threads = new Thread[threadSum];

        for (int i = 0; i < threadSum; i++) {
            threads[i] = new MessageProducerThread();
        }

        for (int i = 0; i < threadSum; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadSum; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
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

    private void rewriteToWebservicesQueue(Properties properties) {

        for (Object key : properties.keySet()) {
            HttpDetailsJob job = deformat((String)key, (String)properties.get(key));
            webserviceQueue.add(job);
        }
    }

    private String getJarFileName() {
        String path = CpdApp2App.class.getResource(CpdApp2App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));

        return path;
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

    public HttpDetailsJob deformat(String key, String value) {
        if (!value.contains(",")) {
            return new HttpDetailsJob(key, value);
        } else {
            String url = value.substring(0, value.indexOf(','));
            String login = value.substring(value.indexOf(',') + 1, value.lastIndexOf(','));
            String password = value.substring(value.lastIndexOf(',') + 1);

            return new HttpDetailsJob(key, url, login, password);
        }
    }
}