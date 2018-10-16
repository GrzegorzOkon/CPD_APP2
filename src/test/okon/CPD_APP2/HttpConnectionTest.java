package okon.CPD_APP2;

import org.junit.Test;

public class HttpConnectionTest {

    @Test(expected = AppException.class)
    public void onWrongParameters_ShouldThrownApiException() {
        new HttpConnection("test");
    }

    /*private static TAR1NameBuilder nameBuilder;
    private static ExcelFileBuilder fileBuilder;

    @Before
    public void setUp() {
        nameBuilder = new TAR1NameBuilder();
    }

    @BeforeClass
    public static void setUpClass() {
        fileBuilder = new ExcelFileBuilder();
        fileBuilder.buildFile();
        fileBuilder.buildSheet("TT_centr");
    }

    @Test
    public void shouldSayThatFirstPartNameIsEquals() {
        nameBuilder.buildFirstPartName();

        assertEquals(nameBuilder.getName(), "taryfa");
    }

    @Test
    public void shouldSayThatThirdPartNameIsEquals() {
        nameBuilder.buildThirdPartName(fileBuilder.getFile());

        assertEquals(nameBuilder.getName(), "0cf25cf6a0981694d4e5d5fdbd8e36325a3f6fe7");
    }*/
}