package tests;

import app.Application;
import org.junit.AfterClass;
import org.junit.Test;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.URISyntaxException;

public class PageObjectCartTest {

   @Test
   public void chrome() throws URISyntaxException {
       //Колличество покупок
       int numberOfPurchases = 20;

       new Application().addToCartProcedure(Application.chromeDriver, numberOfPurchases);
       new Application().deleteFromCartProcedure(Application.chromeDriver);
   }

    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}
