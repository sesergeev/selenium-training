
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Google {

        private WebDriver driver;
        private String baseUrl;


        @Before
        public void setUp() throws Exception {

            driver = new FirefoxDriver();
            baseUrl = "https://www.google.ru/";
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        @Test
        public void googleOpener() throws Exception {
            driver.get(baseUrl + "/");

            driver.quit();

        }


}
