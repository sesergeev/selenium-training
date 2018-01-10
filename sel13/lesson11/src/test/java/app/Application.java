package app;

import org.openqa.selenium.remote.DesiredCapabilities;
import pages.ShopFrontPage;
import pages.CheckoutPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Application {
    public static WebDriver chromeDriver = WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome());

    private ShopFrontPage shopFrontPage;
    private CheckoutPage checkoutPage;

    public Application(){
        shopFrontPage = new ShopFrontPage(chromeDriver);
        checkoutPage = new CheckoutPage(chromeDriver);
    }

    public void deleteFromCartProcedure(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(CheckoutPage.url);



        //driver.findElement(By.cssSelector("#cart a.link")).click();

        for(WebElement checkoutTable = wait.until(visibilityOf(checkoutPage.checkoutTable)); checkoutTable.isDisplayed();
            /*checkoutTable = driver.findElement(By.cssSelector("div[id='checkout-summary-wrapper']"))*/
            checkoutTable = checkoutPage.checkoutTable){

            wait.until(visibilityOf(checkoutTable.findElement(By.tagName("tr"))));
            WebElement removeItemButton = driver.findElement(By.name("remove_cart_item"));
            removeItemButton.click();
            wait.until(ExpectedConditions.stalenessOf(checkoutTable.findElement(By.tagName("tr"))));

        }

        System.out.println("done");

    }
    public void quit() {
        chromeDriver.quit();
    }


    public void addToCartProcedure(WebDriver driver, int numberOfPurchases){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(ShopFrontPage.url);
        //WebElement firstProduct = shopFrontPage.FirstProduct();
        int i;
        for( i = 0; i < numberOfPurchases; i++){
            wait.until(visibilityOf(shopFrontPage.firstProduct));
            /*
            WebElement firstProduct = wait.until(visibilityOfElementLocated(
                     By.cssSelector("#box-most-popular li:nth-child(1)")));
            */
            shopFrontPage.firstProduct.click();
            //wait.until(stalenessOf(shopFrontPage.firstProduct));

            /*
            WebElement addToCart = wait.until(visibilityOfElementLocated(
                    By.cssSelector("td[class = 'quantity'] button[type = 'submit']")));
            */
            wait.until(visibilityOf(shopFrontPage.addToCart));

            if (!driver.findElements(By.cssSelector("td.options")).isEmpty()){
                Select options = new Select(driver.findElement(By.name("options[Size]")));
                options.selectByValue("Small");
            }

            String cartItems = driver.findElement(By.cssSelector("span.quantity")).getAttribute("innerText");

            shopFrontPage.addToCart.click();

            wait.until(ExpectedConditions.invisibilityOfElementWithText(By.cssSelector("span.quantity"), cartItems));
             //driver.findElement(By.id("logotype-wrapper")).click();

            shopFrontPage.logotype.click();
        }

    }



}
