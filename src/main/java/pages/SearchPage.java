package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

import java.util.List;

public class SearchPage extends AbstractPage {
    @FindBy(xpath = "//div[@class='book-item']//h3[@class='title']//a")
    private List<WebElement> listOfBooks;
    @FindBy(xpath = "//a[contains(text(), 'Add to basket')]")
    private List<WebElement> listOfButton;
    @FindBy(xpath = "//select[@id='filterPrice']")
    private WebElement filterPrice;
    @FindBy(xpath = "//select[@id='filterAvailability']")
    private WebElement filterAvailability;
    @FindBy(xpath = "//select[@id='filterLang']")
    private WebElement filterLang;
    @FindBy(xpath = "//select[@id='filterFormat']")
    private WebElement filterFormat;
    @FindBy(xpath = "//button[contains(text(),'Refine results')]")
    private WebElement buttonRefineResults;
    @FindBy(xpath = "//a[contains(text(), 'Basket / Checkout')]")
    private WebElement basketCheckoutButton;

    public WebElement getFilterFormat() {return filterFormat;}
    public WebElement getBasketCheckoutButton() {
        return basketCheckoutButton;
    }
    public WebElement getFilterAvailability() {
        return filterAvailability;
    }
    public WebElement getFilterLang() {
        return filterLang;
    }
    public void clickRefineResultsButton() {
        buttonRefineResults.click();
    }
    public WebElement getFilterPrice() {return filterPrice;}
    public List<WebElement> getListOfBooks() {return listOfBooks;}
    public List<WebElement> getListOfButton() {return listOfButton;}

}
