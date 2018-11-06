package okon.CPD_APP2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection implements Connection {

    private final HttpURLConnection connection;

    public HttpConnection(HttpDetails details) {
        try {
            connection = (HttpURLConnection) new URL(details.getUrl()).openConnection();

            if (details.getLogin() != null)
                authorize(details.getLogin(), details.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    @Override
    public String response() throws AppException {
        //validateResponse();
        StringBuilder response = new StringBuilder();
        try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
        return response.toString();
    }

    @Override
    public void close() {
        connection.disconnect();
    }

    private void authorize(String login, String password) {
        String userpass = login + ":" + password;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        connection.setRequestProperty ("Authorization", basicAuth);
    }
}
