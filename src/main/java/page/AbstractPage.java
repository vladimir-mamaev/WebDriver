package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static constant.Constants.INITIAL_PAGE_NAME;
import static constant.Constants.INITIAL_PAGE_URL;
import static singltoneDriver.SingletonDriver.getDriver;


public abstract class AbstractPage {

    public AbstractPage() { PageFactory.initElements(getDriver(), this);}

    public void openPage(String name){
   if(INITIAL_PAGE_NAME.equals(name)){ getDriver().get(INITIAL_PAGE_URL);}
}

    public boolean isElementPresent(String string) {
        try {
           getDriver().findElement(By.className(string));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public  void clickButtonUsingJS(WebElement button) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].click();", button);
    }

    public  void scrollDownThePage() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,350)");
    }

    public static void chooseOption(WebElement element) {
        Actions action = new Actions(getDriver());
        action.click(element)
                .perform();
    }

    public void clickAndFillCardFields(WebElement element, String string) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element)
                .click(element)
                .sendKeys(Keys.chord(string))
                .perform();
    }

    public void clickAndHold(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element)
                .clickAndHold(element)
                .release()
                .perform();
    }

    public void selectOptions(WebElement element, String value) {
        Select statusesSelect = new Select(element);
        statusesSelect.selectByVisibleText(value);
    }

}
