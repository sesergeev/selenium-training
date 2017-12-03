
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;


public class threeBrowsers {

    private String baseUrl;

    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/admin/login.php";

    }

    @Test
    public void testIE() {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.internetExplorer()));
    }

    @Test
    public void testChrome() {

        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }

    @Test
    public void testFirefox() {

        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.firefox()));
    }

    public void parallelBrowsers(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.get(baseUrl + "/litecart/admin/login.php");
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector(".fa-sign-out")).click();

    }

    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}
