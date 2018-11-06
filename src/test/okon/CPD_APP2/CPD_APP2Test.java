package okon.CPD_APP2;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CPD_APP2Test {
    private static CpdApp2App application;

    @BeforeClass
    public static void setUpClass() {
        application = new CpdApp2App();
    }

    @Test
    public void onPresentTwoValues_shouldSayThatHttpDetailsIsEquals() {
        String description = "Opis";
        String values = "http://1234";

        assertEquals(application.deformat(description, values).getDescription(), "Opis");
    }

    @Test
    public void onPresentAllValues_shouldSayThatUrlIsEquals() {
        String description = "Opis";
        String values = "http://1234,login,password";

        assertEquals(application.deformat(description, values).getUrl(), "http://1234");
    }

    @Test
    public void onPresentAllValues_shouldSayThatLoginIsEquals() {
        String description = "Opis";
        String values = "http://1234,login,password";

        assertEquals(application.deformat(description, values).getLogin(), "login");
    }

    @Test
    public void onPresentAllValues_shouldSayThatPasswordIsEquals() {
        String description = "Opis";
        String values = "http://1234,login,password";

        assertEquals(application.deformat(description, values).getPassword(), "password");
    }
}
