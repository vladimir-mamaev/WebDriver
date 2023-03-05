package helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static constant.Constants.IMPLICITLY_WAIT_TIMEOUT;
import static singltoneDriver.SingletonDriver.getDriver;

public class WebDriverWaiter {
    protected WebDriverWait wait;

    public WebDriverWaiter() {
        wait = new WebDriverWait(getDriver(), IMPLICITLY_WAIT_TIMEOUT);
    }

    public static void waitForPageReadyState(int timeToWait) {
        new WebDriverWait(getDriver(), timeToWait).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
    public static void waitForVisibilityOfElement(int timeToWait, WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeToWait);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
