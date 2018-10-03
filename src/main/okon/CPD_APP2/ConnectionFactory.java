package okon.CPD_APP2;

public class ConnectionFactory {
    public WSDLConnection build(String url) {
        return new WSDLConnection(url);
    }
}
