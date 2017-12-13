import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.selenium.factory.WebDriverPool;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;


public class userRegistration {
    private String baseUrl;
    private File userIDFile = new File("userID.txt");

    @Before
    public void setUp() throws Exception {

       baseUrl = "http://localhost/litecart/";



    }

    @Test
    public void chrome() throws IOException {
        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }




    public void parallelBrowsers(WebDriver driver) throws IOException {
        driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        //--------Customer Data---------
        String userFName = "test";
        String userLName = "test";
        String userAddress = "test";
        String userPostCode = "12345";
        String userId =  ReadLastLine(userIDFile);
        String userEMail = "test" + userId +"@mail.test";
        String userPassword = "test1345";
        String userCity = "Saint Peter";
        String userPhone = "123456789";
        //---------------------------

        //---------Registration process--------
        driver.get(baseUrl);
        driver.findElement(By.cssSelector("#box-account-login a")).click();
        WebElement createAccountBox = driver.findElement(By.cssSelector("#create-account > div > form > table > tbody"));
        createAccountBox.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(1) > input")).sendKeys(userFName);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(2) > input")).sendKeys(userLName);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(3) > td:nth-child(1) > input")).sendKeys(userAddress);

        Select coutrySelector = new Select(createAccountBox.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(1) select")));
        coutrySelector.selectByVisibleText("United States");

        Select stateSelector = new Select(createAccountBox.findElement(By.cssSelector("tr:nth-child(5) > td:nth-child(2) select")));
        stateSelector.selectByVisibleText("Alaska");

        createAccountBox.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(1) > input")).sendKeys(userPostCode);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(4) > td:nth-child(2) > input")).sendKeys(userCity);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(1) > input")).sendKeys(userEMail);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2) > input")).sendKeys(userPhone);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(8) > td:nth-child(1) > input")).sendKeys(userPassword);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(8) > td:nth-child(2) > input")).sendKeys(userPassword);
        createAccountBox.findElement(By.cssSelector("tr:nth-child(9) > td:nth-child(1) button")).click();
        //---------------------------

        //----------Logout/login/logout process-----------------
        driver.findElement(By.cssSelector("#box-account li:nth-child(4) a")).click();
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys(userEMail);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(userPassword);
        driver.findElement(By.cssSelector("span[class='button-set'] button:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#box-account li:nth-child(4) a")).click();


        //-----------------------------------------
}

   private static String ReadLastLine(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        String result = null;
        long startIdx = file.length();
        while (result == null || result.length() == 0) {
            raf.seek(startIdx--);
            raf.readLine();
            result = raf.readLine();
            //result = last line text
        }
             int resultInt = Integer.valueOf(result);
       ++resultInt;
       result = String.valueOf(resultInt);
       System.out.println(result);
       raf.write('\r');
       raf.write('\n');
       //raf.write(resultInt);
       raf.writeBytes(result);
       //result = last line text + 1
       return result;
    }



    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }
}
