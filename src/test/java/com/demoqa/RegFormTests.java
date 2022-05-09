package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.config.CredentialsConfig;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Tag("systemProperties")
public class RegFormTests {

    static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    static String login = config.login();
    static String password = config.password();

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.baseUrl = System.getProperty("baseUrl"); // https://demoqa.com
        Configuration.browserSize = System.getProperty("browserSize"); // "1600x900"
        Configuration.remote = String.format("https://%s:%s"+"@"+ System.getProperty("remoteSelenideUrl") +"/wd/hub", login, password); //login=user1 password=1234
        Configuration.browser = System.getProperty("browser"); // chrome
    }


    @Test
    @Owner("stikheeva")
    @DisplayName("Successful fill registration test")
    void fillFormTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivan Ivanov");
        $("#userEmail").setValue("Ivan@ya.ru");
        $("#genterWrapper").$(byText("Other")).click();
        $("#userNumber").setValue("1234567891");
        $("#dateOfBirthInput").setValue("10 Apr 1990");
        $(".react-datepicker__input-container").click();
        $(".react-datepicker__month-select").selectOption("April");
        $(".react-datepicker__year-select").selectOption("1990");
        $(".react-datepicker__day--010").click();
        $("#subjectsInput").click();
        $("#subjectsInput").setValue("m");
        $(byText("Maths")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("file.png");
        $("#currentAddress").setValue("smth");
        //executeJavaScript("window.scrollBy(0,500)"); works too
        $(byText("Select State")).scrollTo();
        $(byText("Select State")).click();
        $(byText("NCR")).click();
        $(byText("Select City")).click();
        $(byText("Delhi")).click();
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        $("#submit").click();

        //Asserts
        $(".table-responsive").shouldHave(text("Ivan Ivan Ivanov"), text("Ivan@ya.ru"), text("Other"), text("10 April,1990"), text("Music"), text("file.png"),text("smth"), text("NCR Delhi"));





    }
}
