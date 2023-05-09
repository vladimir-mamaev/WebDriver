package page;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static constant.Constants.INITIAL_PAGE_NAME;
import static constant.Constants.INITIAL_PAGE_URL;


public abstract class AbstractPage {


    public void openPage(String name) {
        if (INITIAL_PAGE_NAME.equals(name)) {
            open(INITIAL_PAGE_URL);
        }
    }

    public boolean isElementVisible(String string) {
        try {
            $(By.className(string)).should(visible);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void clickButtonUsingJS(SelenideElement button) {
        executeJavaScript("arguments[0].click();", button);
    }

    public void scrollDownThePage() {
        Selenide.executeJavaScript("window.scrollBy(0,350)");
    }

    public void chooseOption(SelenideElement element) {
        actions().click(element)
                .perform();
    }

    public void clickAndFillCardFields(SelenideElement element, String string) {

        actions().moveToElement(element)
                .click(element)
                .sendKeys(Keys.chord(string))
                .perform();
    }

    public void clickAndHold(SelenideElement element) {

        actions().moveToElement(element)
                .clickAndHold(element)
                .release()
                .perform();
    }

    public void selectOptions(SelenideElement element, String value) {
        element.selectOption(value);
    }

    public void clickOnWebElement(SelenideElement element) {
        element.click();
    }

    public SelenideElement getElementByLocator(By locator) {
        return $(locator);
    }

    public String getTextOfElement(SelenideElement element) {
        return element.getText();
    }

}
