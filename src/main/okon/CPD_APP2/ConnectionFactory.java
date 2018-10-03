package okon.CPD_APP2;

public class ConnectionFactory {
    public WsdlConnection build(String url) {
        return new WsdlConnection(url);
    }
}
