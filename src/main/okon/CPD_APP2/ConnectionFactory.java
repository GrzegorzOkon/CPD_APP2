package okon.CPD_APP2;

public class ConnectionFactory {
    public Connection build(String url) {
        if (url.contains("http://")) {

            return new HttpConnection(url);
        } else {

            return new HttpsConnection(url);
        }
    }
}
