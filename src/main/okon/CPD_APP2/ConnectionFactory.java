package okon.CPD_APP2;

public class ConnectionFactory {
    public Connection build(HttpDetails details) {
        if (details.getUrl().contains("http://") && details.getLogin() == null) {
            return new HttpConnection(details);
        } else if (details.getUrl().contains("http://") && details.getLogin() != null) {
            return new DomainHttpConnection(details);
        } else if (details.getUrl().contains("https://") && details.getLogin() == null) {
            return new HttpsConnection(details);
        } else {
            return new DomainHttpsConnection(details);
        }
    }
}
