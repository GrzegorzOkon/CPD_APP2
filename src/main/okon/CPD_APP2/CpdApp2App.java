package okon.CPD_APP2;

import okon.CPD_APP2.Message;

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

        Properties properties = cpd_app2.loadPropertiesFromFile();

        List<Message> services = cpd_app2.checkAllServices(properties);

        Comparator<Message> byUrlComparator = (m1, m2) -> m1.url.compareTo(m2.url);
        Collections.sort(services, byUrlComparator);

        cpd_app2.saveToFile("CPD_APP2.txt", services);
    }

    public List<Message> checkAllServices(Properties properties) {
        List<Message> checkingDetails = new ArrayList<>();

        for (Object key : properties.keySet()) {
            Message message = null;

            message = checkService((String)properties.get(key), (String)key, 5);
            checkingDetails.add(message);
        }

        return checkingDetails;
    }

    public Message checkService(String url, String description, int allChecksNumber) {
        int correctChecksCounter = 0;

        for (int i = 0; i < allChecksNumber; i++) {
            if (isCorrectService(url, description)) {
                correctChecksCounter++;
            }
        }

        return new Message(url, description, correctChecksCounter, allChecksNumber);
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

    private Properties loadPropertiesFromFile() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("params/addresses.properties"));
        } catch (Exception e) {
            throw new AppException(e);
        }

        return properties;
    }

    public void saveToFile(String fileName, List<Message> content) {
        try (FileOutputStream out = new FileOutputStream(new java.io.File(fileName))) {
            for(Message item : content) {
                byte[] firstLine = item.getDescription().getBytes();
                byte[] secondLine;

                if (item.getCorrectChecks() == 5) {
                    secondLine = (item.getUrl() + " ****** 100 %").getBytes();
                } else if (item.getCorrectChecks() == 4){
                    secondLine = (item.getUrl() + " ****** 80 %").getBytes();
                } else if (item.getCorrectChecks() == 3){
                    secondLine = (item.getUrl() + " ****** 60 %").getBytes();
                } else if (item.getCorrectChecks() == 2){
                    secondLine = (item.getUrl() + " ****** 40 %").getBytes();
                } else if (item.getCorrectChecks() == 1){
                    secondLine = (item.getUrl() + " ****** 20 %").getBytes();
                } else {
                    secondLine = (item.getUrl() + " ****** 0 %").getBytes();
                }

                out.write(firstLine);
                out.write(System.getProperty("line.separator").getBytes());
                out.write(secondLine);
                out.write(System.getProperty("line.separator").getBytes());
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}