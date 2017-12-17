import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.URISyntaxException;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class cartTest {

    private String baseUrl;
    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/";

    }
    @Test
    public void chrome() throws URISyntaxException {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }


    private void parallelBrowsers(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(baseUrl);

        int numberOfItems = 3;

        addToCartProcedure(numberOfItems, wait, driver);

        driver.findElement(By.cssSelector("#cart a.link")).click();

        for(WebElement checkoutTable = wait.until(visibilityOfElementLocated(
                By.cssSelector("div[id='checkout-summary-wrapper']"))); checkoutTable.isDisplayed();
                checkoutTable = driver.findElement(By.cssSelector("div[id='checkout-summary-wrapper']"))){

            wait.until(visibilityOf(checkoutTable.findElement(By.tagName("tr"))));
            WebElement removeItemButton = driver.findElement(By.name("remove_cart_item"));
            removeItemButton.click();
            wait.until(ExpectedConditions.stalenessOf(checkoutTable.findElement(By.tagName("tr"))));

        }

        System.out.println("done");

    }

    private void addToCartProcedure(int numberOfPurchases, WebDriverWait wait, WebDriver driver){
        int i;
        for( i = 0; i < numberOfPurchases; i++){
            WebElement firstProduct = wait.until(visibilityOfElementLocated(
                    By.cssSelector("#box-most-popular li:nth-child(1)")));
            firstProduct.click();
            wait.until(stalenessOf(firstProduct));


            WebElement addToCart = wait.until(visibilityOfElementLocated(
                    By.cssSelector("td[class = 'quantity'] button[type = 'submit']")));

            if (!driver.findElements(By.cssSelector("td.options")).isEmpty()){
                Select options = new Select(driver.findElement(By.name("options[Size]")));
                options.selectByValue("Small");
            }

            String cartItems = driver.findElement(By.cssSelector("span.quantity")).getAttribute("innerText");


            addToCart.click();

            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("span.quantity"), cartItems));
            driver.findElement(By.id("logotype-wrapper")).click();
        }

    }


    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }
}
