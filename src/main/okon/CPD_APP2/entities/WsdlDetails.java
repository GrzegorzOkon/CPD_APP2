package okon.CPD_APP2.entities;

public class WsdlDetails {
    private String description;
    private String url;
    private boolean isCorrect;

    public WsdlDetails(String description, String url, Boolean isCorrect) {
        this.description = description;
        this.url = url;
        this.isCorrect = isCorrect;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}