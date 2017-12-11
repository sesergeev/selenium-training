import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.concurrent.TimeUnit;




public class atrributesComparer {


    private String baseUrl;

    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/";

    }
  @Test
  public void test1IE() {
     parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.internetExplorer()));
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

        //WebDriverWait wait = new WebDriverWait(driver,5);
        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        driver.get(baseUrl);

        String mainPageDuck = duckCheckerMainPage(driver.findElement(By.cssSelector("#box-campaigns ul li")));
        if (mainPageDuck.equals("!!!!EXCEPTION!!!!"))  WebDriverPool.DEFAULT.dismissDriver(driver);


        // ------------Following the duck--------------------------
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(By.cssSelector("#box-campaigns ul li")));

        //--------------------------------------------------------

        driver.findElement(By.cssSelector("#box-campaigns ul li")).click();

        String productMainPageDuck = duckCheckerProductPage(driver.findElement(By.cssSelector("#box-product")));
        if (productMainPageDuck.equals("!!!!EXCEPTION!!!!"))  WebDriverPool.DEFAULT.dismissDriver(driver);
        if (mainPageDuck.equals(productMainPageDuck)) {System.out.println("Утки одинаковые!");}
        else {System.out.println("Данные не одинаковые!");}

    }

    //The functions stores the attributes of the clicked product item and compares them with analogies elements in the product page
    public String duckCheckerMainPage(WebElement duckChecked){
        String exception = "!!!!EXCEPTION!!!!";
        String productName = duckChecked.findElement(By.className("name")).getAttribute("innerText");

        String regularPrice = duckChecked.findElement(By.className("regular-price")).getAttribute("innerText");
        String regularPriceColor = duckChecked.findElement(By.className("regular-price")).getCssValue("color");
        String regularPriceTextStyle = duckChecked.findElement(By.className("regular-price")).getCssValue("text-decoration");
        String regularPriceTextSize = duckChecked.findElement(By.className("regular-price")).getCssValue("font-size");


        String campaignPrice = duckChecked.findElement(By.className("campaign-price")).getAttribute("innerText");
        String campaignPriceColor = duckChecked.findElement(By.className("campaign-price")).getCssValue("color");
        String campaignPriceTextStyle = duckChecked.findElement(By.className("campaign-price")).getCssValue("font-weight");
        String campaignPriceTextSize = duckChecked.findElement(By.className("campaign-price")).getCssValue("font-size");

        //Check color and attributes of price numbers
        float regularPTS = Float.parseFloat(regularPriceTextSize.replaceAll("px",""));
        float campaignPTS = Float.parseFloat(campaignPriceTextSize.replaceAll("px",""));

        if (regularPTS >= campaignPTS) {
            System.out.println("campaign Price number must be bigger");
            return exception;
        }

        regularPriceTextStyle = regularPriceTextStyle.substring(0, 12);
        if (!(regularPriceTextStyle.equals("line-through"))) {
            System.out.println("regular price number is not slashed");
            return exception;
        }


        if (!(campaignPriceTextStyle.equals("Bold") ||
                campaignPriceTextStyle.equals("700") ||
                campaignPriceTextStyle.equals("600") ||
                campaignPriceTextStyle.equals("900")
                ))        {
            System.out.println("Campaign price number is not Bold");
            return exception;
        }

        String[] colorNumbers = regularPriceColor.replace("rgba(", "").replace(")", "").split(",");

        int r = Integer.parseInt(colorNumbers[0].trim());
        int g = Integer.parseInt(colorNumbers[1].trim());
        int b = Integer.parseInt(colorNumbers[2].trim());


        if (!(r == g && g == b)) {
            System.out.println("Color of regular price not gray");
            return exception;

        }

         colorNumbers = campaignPriceColor.replace("rgba(", "").replace(")", "").split(",");

         r = Integer.parseInt(colorNumbers[0].trim());
         g = Integer.parseInt(colorNumbers[1].trim());
         b = Integer.parseInt(colorNumbers[2].trim());

        if (!(r > g && (g + b) == 0)) {
            System.out.println("Color of campaign price not red");
            return exception;
        }

            return productName + regularPrice + campaignPrice;

    }

    public String duckCheckerProductPage(WebElement duckChecked){
        String exception = "!!!!EXCEPTION!!!!";
        String productName = duckChecked.findElement(By.tagName("h1")).getAttribute("innerText");

        String regularPrice = duckChecked.findElement(By.className("regular-price")).getAttribute("innerText");
        String regularPriceColor = duckChecked.findElement(By.className("regular-price")).getCssValue("color");
        String regularPriceTextStyle = duckChecked.findElement(By.className("regular-price")).getCssValue("text-decoration");
        String regularPriceTextSize = duckChecked.findElement(By.className("regular-price")).getCssValue("font-size");

        String campaignPrice = duckChecked.findElement(By.className("campaign-price")).getAttribute("innerText");
        String campaignPriceColor = duckChecked.findElement(By.className("campaign-price")).getCssValue("color");
        String campaignPriceTextStyle = duckChecked.findElement(By.className("campaign-price")).getCssValue("font-weight");
        String campaignPriceTextSize = duckChecked.findElement(By.className("campaign-price")).getCssValue("font-size");
        //Check color and attributes of price numbers
        float regularPTS = Float.parseFloat(regularPriceTextSize.replaceAll("px",""));
        float campaignPTS = Float.parseFloat(campaignPriceTextSize.replaceAll("px",""));

        if (regularPTS >= campaignPTS) {
            System.out.println("campaign Price number must be bigger");
            return exception;
        }




        regularPriceTextStyle = regularPriceTextStyle.substring(0, 12);
        if (!(regularPriceTextStyle.equals("line-through"))) {
            System.out.println("regular price number is not slashed");
            return exception;
        }


        if (!(campaignPriceTextStyle.equals("Bold") ||
                campaignPriceTextStyle.equals("700") ||
                campaignPriceTextStyle.equals("600") ||
                campaignPriceTextStyle.equals("900")
        ))        {
            System.out.println("Campaign price number is not Bold");
            return exception;
        }

        String[] colorNumbers = regularPriceColor.replace("rgba(", "").replace(")", "").split(",");

        int r = Integer.parseInt(colorNumbers[0].trim());
        int g = Integer.parseInt(colorNumbers[1].trim());
        int b = Integer.parseInt(colorNumbers[2].trim());


        if (!(r == g && g == b)) {
            System.out.println("Color of regular price not gray");
            return exception;

        }

        colorNumbers = campaignPriceColor.replace("rgba(", "").replace(")", "").split(",");

        r = Integer.parseInt(colorNumbers[0].trim());
        g = Integer.parseInt(colorNumbers[1].trim());
        b = Integer.parseInt(colorNumbers[2].trim());

        if (!(r > g && (g + b) == 0)) {
            System.out.println("Color of campaign price not red");
            return exception;

        }

        return productName + regularPrice + campaignPrice;


    }


    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}

