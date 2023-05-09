package pages;

import com.codeborne.selenide.SelenideElement;
import page.AbstractPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class BasketPage extends AbstractPage {
    private SelenideElement deliveryCost = $x("//dd[text()='FREE']");
    private SelenideElement total = $x("//dd[text()='74,28 €']");

    private SelenideElement checkoutButton = $("//a[@class='checkout-btn btn first-variant']");

    public SelenideElement getCheckoutButton() {
        return checkoutButton;
    }

    public SelenideElement getDeliveryCost() {
        return deliveryCost;
    }

    public SelenideElement getTotal() {
        return total;
    }
}
