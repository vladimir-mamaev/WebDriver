package helper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.Waiter;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static constant.Constants.TIME_TO_WAIT_ELEMENT;


public class WebDriverWaiter {
    private static final String COMPLETE = "complete";
    private static final long INTERVAL = 3;

    public static void waitForPageReadyState() {
        new Waiter().wait(TIME_TO_WAIT_ELEMENT, INTERVAL, () ->
                COMPLETE.equals(executeJavaScript("return document.readyState")));
    }

    public static void waitForVisibilityOfElement(SelenideElement element) {
        element.shouldBe(Condition.interactable, Duration.ofSeconds(10));
    }

}
