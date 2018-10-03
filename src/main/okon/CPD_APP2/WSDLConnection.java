package okon.CPD_APP2;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WSDLConnection implements Closeable {

    private final HttpURLConnection connection;

    public WSDLConnection(String url) {
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /*public String response() {
        validateResponse();
        StringBuilder response = new StringBuilder();
        try (BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
        return response.toString();
    }*/

    @Override
    public void close() throws IOException {

    }
}
