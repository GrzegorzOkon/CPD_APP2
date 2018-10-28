package okon.CPD_APP2;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.Test;

public class TxtFormatterTest {
    @Test
    public void onPresentDescription_shouldSayThatFormattedMessageIsEquals() {
        Message message = new Message("www", "Opis", 0, 3);

        assertEquals(new String(new TxtFormatter(message).format().get(0), StandardCharsets.UTF_8), "Opis");
    }

    @Test
    public void onZeroCorrectChecks_shouldSayThatFormattedMessageIsEquals() {
        Message message = new Message("www", "Opis", 0, 3);

        assertEquals(new String(new TxtFormatter(message).format().get(1), StandardCharsets.UTF_8), "www ****** 0 %");
    }

    @Test
    public void onAllCorrectChecks_shouldSayThatFormattedMessageIsEquals() {
        Message message = new Message("www", "Opis", 3, 3);

        assertEquals(new String(new TxtFormatter(message).format().get(1), StandardCharsets.UTF_8), "www ****** 100 %");
    }

    @Test
    public void onFractionChecks_shouldSayThatFormattedMessageIsEquals() {
        Message message = new Message("www", "Opis", 2, 3);

        assertEquals(new String(new TxtFormatter(message).format().get(1), StandardCharsets.UTF_8), "www ****** 66 %");
    }
}