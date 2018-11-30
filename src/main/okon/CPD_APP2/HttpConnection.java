package okon.CPD_APP2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection implements Connection {

    protected final HttpURLConnection connection;

    HttpConnection(HttpDetails details) {
        try {
            connection = (HttpURLConnection) new URL(details.getUrl()).openConnection();
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    @Override
    public String response() throws AppException {
        validateResponse();
        StringBuilder response = new StringBuilder();
        try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
        return response.toString();
    }

    @Override
    public void close() {
        connection.disconnect();
    }

    void validateResponse() {
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                String responseMessage = connection.getResponseMessage();
                throw new AppException(String.format("Something went wrong! [%d] %s", responseCode, responseMessage));
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
