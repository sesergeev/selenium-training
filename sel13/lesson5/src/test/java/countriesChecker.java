import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;


import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class countriesChecker {

    private String baseUrl;
    Comparator mycomparator = Comparator.naturalOrder();
    @Before
    public void setUp() throws Exception {

        baseUrl = "http://localhost/litecart/admin/login.php";

    }
    @Test
    public void testChrome() {

        parallelBrowsers(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }

    public void parallelBrowsers(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver,5);
        // driver.manage().timeouts().implicitlyWait(5L, TimeUnit.SECONDS);
        driver.get(baseUrl);
        //логин
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //идём на страницу Countries
        driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child(3)")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("h1")));
        //получаем массив всех стран
        List<String> countriesByAbc = new ArrayList<>();
        List<String> countriesInTheSite = new ArrayList<>();
        List<String> countiesWithNotZeroZones = new ArrayList<>();
        List<WebElement> countriesRow = driver.findElements(By.cssSelector("tr.row"));
        for(WebElement county:countriesRow){

             WebElement countryLink = county.findElement(By.cssSelector("td:nth-child(5) a"));
             countriesByAbc.add(countryLink.getAttribute("innerText"));
             countriesInTheSite.add(countryLink.getAttribute("innerText"));

             WebElement zoneId = county.findElement(By.cssSelector("td:nth-child(6)"));
             String zoneIdAttribute = zoneId.getAttribute("innerText");

             if (!zoneIdAttribute.equals("0")){
                countiesWithNotZeroZones.add(countryLink.getAttribute("href"));

             }

        }

        //в countriesByAbc сортируем страны по алфавиту
        Collections.sort(countriesByAbc, mycomparator);
        //сравнимаем что списки стран одинаковые и завершаем тест если это не так
            if (!countriesInTheSite.equals(countriesByAbc)) {
                System.out.println("Страны не по алфавиту!");
                driver.quit();
            }

        else {System.out.println("Страны в алфавитном порядке!");

        //проверка стран у которых есть зоны
            for (String zoneLink: countiesWithNotZeroZones ){
                countriesInTheSite.clear();
                countriesByAbc.clear();
                driver.get(zoneLink);
                //#table-zones tbody tr td:nth-child(3)
                countriesRow = driver.findElements(By.cssSelector("#table-zones tbody tr td:nth-child(3)"));
                for(WebElement countryLink:countriesRow) {
                   // System.out.println(countryLink.findElement(By.tagName("input")).getAttribute("type"));
                    if( countryLink.findElement(By.tagName("input")).getAttribute("type").equals("hidden")) {

                        countriesByAbc.add(countryLink.getAttribute("innerText"));
                        countriesInTheSite.add(countryLink.getAttribute("innerText"));
                    }
                }

                Collections.sort(countriesByAbc, mycomparator);
                //сравнимаем что списки стран одинаковые и завершаем тест если это не так
                if (!countriesInTheSite.equals(countriesByAbc)) {
                    System.out.println("Зоны не по алфавиту!");


                }
                else System.out.println("Зоны в алфавитном порядке!");


            }



        }

        //-------Гео Зоны------

        //идём на страницу GeoZones
        driver.findElement(By.cssSelector("#box-apps-menu > li:nth-child(6)")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("h1")));
        //получаем массив всех стран
        countriesByAbc = new ArrayList<>();
        countriesInTheSite = new ArrayList<>();
        countiesWithNotZeroZones = new ArrayList<>();
        countriesRow = driver.findElements(By.cssSelector("tr.row"));
        for(WebElement county:countriesRow){

            WebElement countryLink = county.findElement(By.cssSelector("td:nth-child(3) a"));
            countriesByAbc.add(countryLink.getAttribute("innerText"));
            countriesInTheSite.add(countryLink.getAttribute("innerText"));

            WebElement zoneId = county.findElement(By.cssSelector("td:nth-child(4)"));
            String zoneIdAttribute = zoneId.getAttribute("innerText");

            if (!zoneIdAttribute.equals("0")){
                countiesWithNotZeroZones.add(countryLink.getAttribute("href"));

            }

        }

        //в countriesByAbc сортируем страны по алфавиту
        Collections.sort(countriesByAbc, mycomparator);
        //сравнимаем что списки стран одинаковые и завершаем тест если это не так
        if (!countriesInTheSite.equals(countriesByAbc)) {
            System.out.println("Геозоны не по алфавиту!");
            driver.quit();
        }

        else {System.out.println("Геозоны в алфавитном порядке!");

            //проверка стран у которых есть зоны
            for (String zoneLink: countiesWithNotZeroZones ){
                countriesInTheSite.clear();
                countriesByAbc.clear();
                driver.get(zoneLink);
                //#table-zones tbody tr td:nth-child(3)
                countriesRow = driver.findElements(By.cssSelector("#table-zones tbody tr td:nth-child(3) select"));
                for(WebElement countryLink:countriesRow) {
                       Select selectZone = new Select(countryLink);
                       countriesByAbc.add(selectZone.getFirstSelectedOption().getAttribute("innerText"));
                       countriesInTheSite.add(selectZone.getFirstSelectedOption().getAttribute("innerText"));


                }

                Collections.sort(countriesByAbc, mycomparator);

                //сравнимаем что списки стран одинаковые и завершаем тест если это не так
                if (!countriesInTheSite.equals(countriesByAbc)) {
                    System.out.println("Зоны не по алфавиту!");


                }
                else System.out.println("Зоны в алфавитном порядке!");


            }



        }



        driver.quit();
    }


    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }


}
