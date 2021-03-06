import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;


public class stickers {


    private String baseUrl;

    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/";

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

        WebDriverWait wait = new WebDriverWait(driver,5);
        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        driver.get(baseUrl);

        List<WebElement> wrapperProduct = driver.findElements(By.cssSelector("ul.listing-wrapper > li"));

            for (WebElement element: wrapperProduct) {
            List<WebElement> sticker = element.findElements(By.cssSelector("div.sticker"));
            if (sticker.size() != 1) {
                System.out.println("Колличество стикеров не ровно одному!");
                driver.close();
            }

               }

              }





    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}

