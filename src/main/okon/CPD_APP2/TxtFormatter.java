package okon.CPD_APP2;

import java.util.ArrayList;
import java.util.List;

public class TxtFormatter {
    private final Message message;

    public TxtFormatter(Message message) {
        this.message = message;
    }

    public List<byte[]> format() {
        List<byte[]> text = new ArrayList<>();

        text.add(message.getDescription().getBytes());
        text.add((message.getUrl() + " ****** " + (int) (((float) message.getCorrectChecks() / (float) message.getAllChecks()) * 100) + " %").getBytes());

        return text;
    }
}
