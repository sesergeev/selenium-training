import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class litecartLogin {
        private WebDriver driver;
        private String baseUrl;
        private StringBuffer verificationErrors = new StringBuffer();

        @Before
        public void setUp() throws Exception {
            driver = new ChromeDriver();
            baseUrl = "http://localhost/litecart/admin/login.php";
            driver.manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);

        }

        @Test
        public void testUntitled() throws Exception {

            driver.get(baseUrl + "/litecart/admin/login.php");
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys("admin");
            driver.findElement(By.name("login")).click();
            driver.findElement(By.cssSelector(".fa-sign-out")).click();

        }

        @After
        public void tearDown() throws Exception {
            driver.quit();
                              }


    }


