import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class linksChecker {

    private String baseUrl;


    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/admin/login.php";

    }

    @Test
    public void internetExplorer() throws URISyntaxException {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.internetExplorer()));
    }
    @Test
    public void chrome() throws URISyntaxException {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }
    @Test
    public void firefox() throws URISyntaxException {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.firefox()));
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
        //идём на страницу Countries
        driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child(3)")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("h1")));

        driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(5) > a")).click();

        List<WebElement> urlRow = driver.findElements(By.cssSelector("i[class='fa fa-external-link']"));

        for (WebElement url:urlRow){
            String mainWindow = driver.getWindowHandle();
            Set<String> oldWindows = driver.getWindowHandles();
            url.click(); // открывает новое окно
            String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
            //String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));

            driver.switchTo().window(newWindow);
            ((JavascriptExecutor) driver).executeScript("window.focus();");
// ...
            driver.close();

            driver.switchTo().window(mainWindow);
            ((JavascriptExecutor) driver).executeScript("window.focus();");
        }

        //i[class="fa fa-external-link"]
    }

    public ExpectedCondition<String> thereIsWindowOtherThan(final Set<String> oldWindows){
        return new ExpectedCondition<String>(){
            public String apply(WebDriver driver){

                Set<String> handles = driver.getWindowHandles();



                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next():null;

            }
        };
    }

    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}
