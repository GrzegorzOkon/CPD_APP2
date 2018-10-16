package okon.CPD_APP2;

public class ConnectionFactory {
    public HttpConnection build(String url) {
        return new HttpConnection(url);
    }
}
