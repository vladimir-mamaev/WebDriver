package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;


public class BasketPage extends AbstractPage {

    @FindBy(xpath = "//dd[text()='FREE']")
    private WebElement deliveryCost;
    @FindBy(xpath = "//dd[text()='85,62 €']")
    private WebElement total;
    @FindBy(xpath = "//a[@class='checkout-btn btn first-variant']")
    private WebElement checkoutButton;

    public WebElement getCheckoutButton() {
        return checkoutButton;
    }

    public WebElement getDeliveryCost() {
        return deliveryCost;
    }

    public WebElement getTotal() {
        return total;
    }
}
