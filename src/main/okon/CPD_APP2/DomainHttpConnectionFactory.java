package okon.CPD_APP2;

public class DomainHttpConnectionFactory extends ConnectionFactory {
    public Connection build(HttpDetails details) {
        return new DomainHttpConnection(details);
    }
}