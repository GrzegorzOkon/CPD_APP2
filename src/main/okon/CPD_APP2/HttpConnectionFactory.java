package okon.CPD_APP2;

public class HttpConnectionFactory extends ConnectionFactory {
    public Connection build(HttpDetailsJob details) {
        return new HttpConnection(details);
    }
}
