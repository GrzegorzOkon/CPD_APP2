package okon.CPD_APP2;

public class DomainHttpsConnection extends HttpsConnection {
    DomainHttpsConnection(HttpDetails details) {
        super(details);
        authorize(details.getLogin(), details.getPassword());
    }

    void authorize(String login, String password) {
        String userpass = login + ":" + password;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        connection.setRequestProperty ("Authorization", basicAuth);
    }
}