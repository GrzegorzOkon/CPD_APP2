package okon.CPD_APP2;

public class HttpsConnectionFactory extends ConnectionFactory {
    public Connection build(HttpDetails details) {
        return new HttpsConnection(details);
    }
}
