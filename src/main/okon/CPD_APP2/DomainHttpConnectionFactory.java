package okon.CPD_APP2;

public class DomainHttpConnectionFactory extends ConnectionFactory {
    public Connection build(HttpDetailsJob details) {
        return new DomainHttpConnection(details);
    }
}