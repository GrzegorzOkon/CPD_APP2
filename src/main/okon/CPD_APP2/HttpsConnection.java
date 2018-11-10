package okon.CPD_APP2;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpsConnection implements Connection {

    private static SSLSocketFactory sslSocketFactory = null;

    private static final TrustManager[] ALL_TRUSTING_TRUST_MANAGER = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
    };

    private static final HostnameVerifier ALL_TRUSTING_HOSTNAME_VERIFIER  = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private final HttpsURLConnection connection;

    HttpsConnection(HttpDetails details) {
        try {
            connection = (HttpsURLConnection) new URL(details.getUrl()).openConnection();
            if (details.getLogin() != null)
                authorize(details.getLogin(), details.getPassword());
            setAcceptAllVerifier(connection);
        } catch (Exception e) {
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

    static void setAcceptAllVerifier(HttpsURLConnection connection) throws NoSuchAlgorithmException, KeyManagementException {
        if( null == sslSocketFactory) {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, ALL_TRUSTING_TRUST_MANAGER, new java.security.SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
        }
        connection.setSSLSocketFactory(sslSocketFactory);
        connection.setHostnameVerifier(ALL_TRUSTING_HOSTNAME_VERIFIER);
    }

    void authorize(String login, String password) {
        String userpass = login + ":" + password;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        connection.setRequestProperty ("Authorization", basicAuth);
    }
}