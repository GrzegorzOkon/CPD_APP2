package okon.CPD_APP2;

public class Message {
    private String description;
    private String url;
    private int correctChecks;
    private int allChecks;

    public Message(String description, String url, int correctChecks, int allChecks) {
        this.description = description;
        this.url = url;
        this.correctChecks = correctChecks;
        this.allChecks = allChecks;
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

    public int getCorrectChecks() {
        return correctChecks;
    }

    public void setCorrectChecks(int correctChecks) {
        this.correctChecks = correctChecks;
    }

    public int getAllChecks() {
        return allChecks;
    }

    public void setAllChecks(int allChecks) {
        this.allChecks = allChecks;
    }
}