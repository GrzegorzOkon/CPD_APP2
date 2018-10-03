package okon.CPD_APP2;

import okon.CPD_APP2.entities.WsdlDetails;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
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

        List<WsdlDetails> services = cpd_app2.checkAllServices(properties);

        cpd_app2.saveToFile("CPD_APP2.txt", services);
    }

    public List<WsdlDetails> checkAllServices(Properties properties) {
        List<WsdlDetails> servicesDetails = new ArrayList<>();

        for (Object key : properties.keySet()) {
            servicesDetails.add(checkSingleService((String)key, (String)properties.get(key)));
        }

        return servicesDetails;
    }

    public WsdlDetails checkSingleService(String description, String url) {
        try (WsdlConnection connection = connectionFactory.build(url)) {
            try {
                String response = connection.response();
                if (response != null) {
                    return new WsdlDetails(description, url, true);
                }
            } catch (AppException e) {}
        }

        return new WsdlDetails(description, url, false);
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

    public void saveToFile(String fileName, List<WsdlDetails> content) {
        try (FileOutputStream out = new FileOutputStream(new java.io.File(fileName))) {
            for(WsdlDetails item : content) {
                byte[] firstLine = item.getDescription().getBytes();
                byte[] secondLine;

                if (item.isCorrect()) {
                    secondLine = (item.getUrl() + " ****** 100 %").getBytes();
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