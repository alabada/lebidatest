import org.junit.Test;

import java.util.Properties;

public class WhichOSTest {

    @Test
    public void test1() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            System.out.println(os.toLowerCase());
        } else {
            System.out.println(os.toLowerCase());
        }
    }
}
