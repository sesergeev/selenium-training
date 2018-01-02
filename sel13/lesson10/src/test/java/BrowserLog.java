import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserLog {
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
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        driver.get(baseUrl);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //идём на страницу Countries и кликаем на добавить товар

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=2");



        List<WebElement> allProducts = driver.findElements(By.cssSelector("tr[class^='row'] td:nth-child(3) > a"));

        for (WebElement link: allProducts){
            String mainWindow = driver.getWindowHandle();
            Set<String> oldWindows = driver.getWindowHandles();
            link.sendKeys(selectLinkOpeninNewTab);;
            String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);
            for (LogEntry logs: driver.manage().logs().get("browser").getAll()) {
                System.out.println(logs);

                if(logs.toString().length() > 0 ) {
                    System.out.println("Log is not empty");
                    driver.close();
                }

            }
            ((JavascriptExecutor) driver).executeScript("window.focus();");
            driver.close();

            driver.switchTo().window(mainWindow);
            ((JavascriptExecutor) driver).executeScript("window.focus();");
        }
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