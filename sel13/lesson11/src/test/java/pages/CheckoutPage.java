package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    public CheckoutPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }
    public static String url = "http://localhost/litecart/en/checkout";

    @FindBy(css = "div[id='checkout-summary-wrapper")
    public static WebElement checkoutTable;



}
