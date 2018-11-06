package okon.CPD_APP2;

public class ConnectionFactory {
    public Connection build(HttpDetails details) {
        if (details.getUrl().contains("http://")) {

            return new HttpConnection(details);
        } else {

            return new HttpsConnection(details);
        }
    }
}
