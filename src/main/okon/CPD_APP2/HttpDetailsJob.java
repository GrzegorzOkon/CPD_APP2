package okon.CPD_APP2;

public class HttpDetailsJob {
    private String description;
    private String url;
    private String login;
    private String password;

    public HttpDetailsJob(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public HttpDetailsJob(String description, String url, String login, String password) {
        this.description = description;
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
