import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import javax.tools.JavaFileManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class addNewItem{

        private String baseUrl;


        @Before
        public void setUp() throws Exception {

            baseUrl = "http://localhost/litecart/admin/login.php";


        }

        @Test
        public void chrome() throws URISyntaxException {
            parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
        }

        private void parallelBrowsers(WebDriver driver) throws URISyntaxException {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
            driver.get(baseUrl);
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys("admin");
            driver.findElement(By.name("login")).click();

            //идём на страницу Countries и кликаем на добавить товар
            driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child(2)")).click();
            driver.findElement(By.cssSelector("#content > div:nth-child(2) > a:nth-child(2)")).click();

            //-----General-------------
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("label:nth-child(3) > input[type='radio']")));
            String pName = "test duck";
            String pCode = "test001";
            driver.findElement(By.cssSelector("label:nth-child(3) > input[type='radio']")).click();
            driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(pName);
            driver.findElement(By.cssSelector("input[name='code']")).sendKeys(pCode);
            driver.findElement(By.cssSelector("tr:nth-child(4) tr:nth-child(1)  input")).click();
            driver.findElement(By.cssSelector("tr:nth-child(4) tr:nth-child(2)  input")).click();
            driver.findElement(By.cssSelector("tr:nth-child(7) tr:nth-child(4)  input")).click();
            driver.findElement(By.cssSelector("tr:nth-child(7) tr:nth-child(4)  input")).click();
            driver.findElement(By.cssSelector("input[name='quantity']")).clear();
            driver.findElement(By.cssSelector("input[name='quantity']")).sendKeys("30");

            //----Получение асболютного пути и добавление картинки--------
            URL pImage = addNewItem.class.getResource("pirateduck.jpg");
            String imagePath = String.valueOf(Paths.get(pImage.toURI()).toFile());
            driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(imagePath);
            //-------------------------------------------------------------
            driver.findElement(By.cssSelector("tr:nth-child(10) input")).sendKeys("12/01/2017");
            driver.findElement(By.cssSelector("tr:nth-child(11) input")).sendKeys("12/01/2018");

            //------------------

            //-----information-------------
            driver.findElement(By.cssSelector("#content ul li:nth-child(2) a")).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("manufacturer_id")));

            Select manufacturerId = new Select(driver.findElement(By.name("manufacturer_id")));
            manufacturerId.selectByValue("1");

            WebElement shortDesk = driver.findElement(By.cssSelector("tr:nth-child(4) > td > span"));

            Actions actions = new Actions(driver);
            actions.moveToElement(shortDesk);
            actions.click();
            actions.sendKeys("Wried by using actions");
            actions.build().perform();

            driver.findElement(By.cssSelector("div.trumbowyg-editor"))
                    .sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. Cras scelerisque dui non consequat sollicitudin. Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst. Phasellus ut pretium justo, sit amet bibendum urna. Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a. Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.");
            //---------------------------------

            //---------------prices-----------
            driver.findElement(By.cssSelector("#content ul li:nth-child(4) a")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("purchase_price")));

            driver.findElement(By.name("purchase_price")).clear();
            driver.findElement(By.name("purchase_price")).sendKeys("10");

            Select pCurrencyCode = new Select(driver.findElement(By.name("purchase_price_currency_code")));
            pCurrencyCode.selectByValue("USD");
            driver.findElement(By.name("prices[USD]")).sendKeys("20.0000");

            //-----save click
            driver.findElement(By.name("save")).click();
            //------------


            List<WebElement> allProducts = driver.findElements(By.cssSelector("tr.row td a:nth-child(2)"));

            for (WebElement element: allProducts) {
               if (Objects.equals(element.getAttribute("innerText"), pName)) {
                   driver.get(element.getAttribute("href"));
                   System.out.println("Новый продукт присутствует");
                    break;

            }

            }
        }



        @AfterClass
        public static void stopAllBrowsers() {
            WebDriverPool.DEFAULT.dismissAll();
        }
}
