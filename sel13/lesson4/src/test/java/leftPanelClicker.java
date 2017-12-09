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


import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class leftPanelClicker {

    private String baseUrl;

    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/admin/login.php";


    }


    @Test
    public void testChrome() {

        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }



    public void parallelBrowsers(WebDriver driver){

        WebDriverWait wait = new WebDriverWait(driver,5);
       // driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        driver.get(baseUrl);

        //логин
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();


        //построение массива из всех кнопок левой панели
        List<WebElement> links = driver.findElements(By.cssSelector("#box-apps-menu > li"));
        for (int i = 1; i <= links.size(); i++)
        {
            driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child("+i+")")).click();
            if (driver.findElements(By.cssSelector("#box-apps-menu > li:nth-child("+i+") li")).size() != 0)
            {
                for (int y = 1; y <= driver.findElements(By.cssSelector("#box-apps-menu > li:nth-child("+i+") li")).size(); y++)
                {
                    driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child("+i+") > ul > li:nth-child("+y+")")).click();
                    wait.until(presenceOfElementLocated(By.cssSelector("h1")));
                }

            }
            else {
                wait.until(presenceOfElementLocated(By.cssSelector("h1")));
            }
        }

        //кнопка выход
        driver.findElement(By.cssSelector(".fa-sign-out")).click();

    }

    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}
