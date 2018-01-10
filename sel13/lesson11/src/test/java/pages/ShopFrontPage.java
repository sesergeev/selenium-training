package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *
 */
public class ShopFrontPage {

    public ShopFrontPage(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }


    public static String url = "http://localhost/litecart/";

    @FindBy(css = "#box-most-popular li:nth-child(1)")
    public WebElement firstProduct;

    @FindBy(css = "td[class = 'quantity'] button[type = 'submit']")
    public WebElement addToCart;

    @FindBy(id = "logotype-wrapper")
    public WebElement logotype;

   /* @FindBy(css = "#cart a.link")
    public static WebElement cartLink;
*/

}
