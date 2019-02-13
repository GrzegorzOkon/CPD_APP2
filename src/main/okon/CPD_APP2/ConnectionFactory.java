package okon.CPD_APP2;

public abstract class ConnectionFactory {
    public abstract Connection build(HttpDetailsJob details);
}
