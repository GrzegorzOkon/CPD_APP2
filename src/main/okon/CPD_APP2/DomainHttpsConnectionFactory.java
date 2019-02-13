package okon.CPD_APP2;

public class DomainHttpsConnectionFactory extends ConnectionFactory {
    public Connection build(HttpDetailsJob details) {
        return new DomainHttpsConnection(details);
    }
}