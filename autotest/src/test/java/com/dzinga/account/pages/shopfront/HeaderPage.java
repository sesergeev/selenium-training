package com.dzinga.account.pages.shopfront;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.dzinga.account.conf.Configurator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Общий хедер витрины, старый стиль
 */


public class HeaderPage {

    public HeaderPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    //url страницы
    public static String url =  "https://dzinga.com/";

    /*
    Основное меню
     */


    //Логотип Дзинги, переход на DzingaPage
    @FindBy (className = "nav-main__header")
    public static WebElement dzingaLogo;

    //Переход на ProductsPage, при наведении вызывает вслывающее меню с элементами продуктов
    @FindBy (css = "ul.nav-main__nav > li:nth-child(1)")
    public static WebElement productsLink;

    //Переход на UseCasesPage
    @FindBy (css = "ul.nav-main__nav > li:nth-child(2)")
    public static WebElement useCasesLink;

    //Переход на PricingPage
    @FindBy (css = "ul.nav-main__nav > li:nth-child(3)")
    public static WebElement pricingLink;

    //Переход на AboutPage, при наведении вызывает вслывающее меню с элементами about
    @FindBy (css = "ul.nav-main__nav > li:nth-child(4)")
    public static WebElement aboutLink;

    //Переход на BlogPage
    @FindBy (css = "ul.nav-main__nav > li:nth-child(5)")
    public static WebElement blogLink;

    /*
    Всплывающее меню Продуктов productsLink
     */

    //Переход на ProductsVoipPage
    @FindBy (css = "li:nth-child(1)")
    public static WebElement productsLinkVoip;

    //Переход на ProductsHpbxPage
    @FindBy (css = "li:nth-child(2)")
    public static WebElement productsLinkHpbx;

    //Переход на ProductsTollfreePage
    @FindBy (css = "li:nth-child(3)")
    public static WebElement productsLinkTollfree;

    //Переход на ProductsLandlinePage
    @FindBy (css = "li:nth-child(4)")
    public static WebElement productsLinkLandline;

    //Переход на ProductsCallRecordingPage
    @FindBy (css = "li:nth-child(5)")
    public static WebElement productsLinkCallRecording;

    //Переход на ProductsCallbackPage
    @FindBy (css = "li:nth-child(6)")
    public static WebElement productsLinkCallback;

    /*
    Всплывающее меню aboutLink
     */

    //Переход на AboutPartnersPage
    @FindBy (css = "li:nth-child(1)")
    public static WebElement aboutLinkPartners;

    //Переход на AboutPaymentsPage
    @FindBy (css = "li:nth-child(2)")
    public static WebElement aboutLinkPayments;

    //Переход на AboutSupportPage
    @FindBy (css = "li:nth-child(3)")
    public static WebElement aboutLinkSupport;

    //Переход на AboutContactsPage
    @FindBy (css = "li:nth-child(4)")
    public static WebElement aboutLinkContacts;

    /*
    Иконки и флашки
     */

    //Иконка переход на accounts.dzinga.com
    @FindBy (className = "nav-main__tools-li nav-main__tools-li--login")
    public static WebElement loginIcon;

    //Список выбора локализации
    @FindBy (className = "nav-main__tools-li nav-main__tools-li--flag nav-main__tools-li--desktop")
    public static WebElement localisationList;

    //Список выбора валюты
    @FindBy (className = "nav-main__tools-li nav-main__tools-li--currency nav-main__tools-li--desktop")
    public static WebElement currencyList;

    /*
    Список языков локализации
     */

    @FindBy (id = "en")
    public static WebElement englishLocal;

    @FindBy (id = "pl")
    public static WebElement pollandLocal;

    @FindBy (id = "cz")
    public static WebElement czechLocal;

    @FindBy (id = "ee")
    public static WebElement esttoniaLocal;

    @FindBy (id = "lt")
    public static WebElement lithuaniaLocal;

    @FindBy (id = "lv")
    public static WebElement latviaLocal;

    @FindBy (id = "ru")
    public static WebElement russianLocal;

    /*
    Список валют
     */

    @FindBy (id = "eur")
    public static WebElement eurCurrency;

    @FindBy (id = "pln")
    public static WebElement plnCurrency;

    @FindBy (id = "czk")
    public static WebElement czkCurrency;

    public static List<WebElement> allElements;

}
