package okon.CPD_APP2;

import java.util.ArrayList;
import java.util.List;

public class MessageFormatter {
    private final Message message;

    public MessageFormatter(Message message) {
        this.message = message;
    }

    public List<String> format() {
        List<String> result = new ArrayList<>();
        result.add(message.getDescription());
        result.add((message.getUrl() + " ****** " + (int) (((float) message.getCorrectChecks() / (float) message.getAllChecks()) * 100) + " %"));
        return result;
    }
}
