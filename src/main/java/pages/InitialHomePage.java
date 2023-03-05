package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

public class InitialHomePage extends AbstractPage {
    @FindBy(xpath = "//input[@name = 'searchTerm']")
    private WebElement searchField;
    public void enterSearchField(String bookName) {
        searchField.sendKeys(bookName, Keys.ENTER);
    }
}