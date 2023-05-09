package pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.DeliveryAddress;
import page.AbstractPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static helper.WebDriverWaiter.waitForVisibilityOfElement;


public class CheckoutPage extends AbstractPage {

    private SelenideElement buyNowButton = $x("//button[@id='buyNowButton']");

    private SelenideElement emailErrorMessage = $(byId("email-errors"));

    private SelenideElement fullNameErrorMessage = $(byId("delivery-fullName-errors"));

    private SelenideElement deliveryAddressLine1ErrorMessage = $(byId("delivery-addressLine1-errors"));

    private SelenideElement deliveryCityErrorMessage = $(byId("delivery-city-errors"));

    private SelenideElement deliveryPostCodeErrorMessage = $(byId("delivery-postCode-errors"));

    private SelenideElement paymentErrorMessages = $(byClassName("buynow-error-msg"));

    private SelenideElement subTotal = $x("//div[@aria-label='Sub-total 74,28 €']//dd[contains(text(),'74')]");

    private SelenideElement delivery = $x("//div[@aria-label='Delivery FREE']//strong[contains(text(),'FREE')]");

    private SelenideElement vat = $x("//div[@aria-label='VAT 0,00 €']//dd[contains(text(),'0,00 €')]");

    private SelenideElement total = $x("//div[@aria-label='Total 74,28 €']//dd[contains(text(),'74')]");

    private SelenideElement email = $x("//input[@aria-label='Email address (for order confirmation)']");

    private SelenideElement deliveryFullNameField = $x("//input[@name='delivery-fullName']");

    private SelenideElement deliveryAddressLine2 = $x("//input[@name='delivery-addressLine2']");

    private SelenideElement deliveryCity = $x("//input[@name='delivery-city']");

    private SelenideElement deliveryCounty = $x("//input[@name='delivery-county']");

    private SelenideElement deliveryPostCode = $x("//input[@name='delivery-postCode']");

    private SelenideElement deliveryAddressLine1 = $x("//input[@name='delivery-addressLine1']");

    private SelenideElement deliveryCountry = $x("//select[@name='deliveryCountry']");

    private SelenideElement expiryDate = $x("//label[@for='expiration-date']");
    private ElementsCollection cvv = $$x("//label[@for='cvv']");

    private SelenideElement cardNumber = $x("//label[@for='card-number']");

    public SelenideElement getSubTotal() {
        return subTotal;
    }

    public SelenideElement getDelivery() {
        return delivery;
    }

    public SelenideElement getVat() {
        return vat;
    }

    public SelenideElement getTotal() {
        return total;
    }

    public SelenideElement getEmail() {
        return email;
    }

    public CheckoutPage fillDeliveryAddress(DeliveryAddress deliveryAddress) {
        waitForVisibilityOfElement(deliveryAddressLine1);
        deliveryFullNameField.sendKeys(deliveryAddress.getFullName());
        deliveryAddressLine1.sendKeys(deliveryAddress.getAddressLine1());
        deliveryAddressLine2.sendKeys(deliveryAddress.getAddressLine2());
        deliveryCity.sendKeys(deliveryAddress.getTownCity());
        deliveryCounty.sendKeys(deliveryAddress.getCountyState());
        deliveryPostCode.sendKeys(deliveryAddress.getPostCode());
        return this;
    }

    public CheckoutPage getDeliveryCountryAndorra(String country) {
        chooseOption(getElementByLocator(byXpath(String.format("//li[@name='option-AD']//a[contains(text(), '%s')]", country))));
        return this;
    }

    public void waitForDeliveryCountry(DeliveryAddress deliveryAddres) {
        waitForVisibilityOfElement($x("//li[@name='option-AD']//a[contains(text(), '" + deliveryAddres.getDeliveryCountry() + "')]"));
    }

    public SelenideElement getDeliveryCountry() {
        return deliveryCountry;
    }

    public List<String> getListFromWebElements() {
        return Stream.of(emailErrorMessage, fullNameErrorMessage
                        , deliveryAddressLine1ErrorMessage, deliveryCityErrorMessage, deliveryPostCodeErrorMessage).
                map(SelenideElement::getText).collect(Collectors.toList());
    }

    public SelenideElement getPaymentErrorMessages() {
        return paymentErrorMessages;
    }

    public SelenideElement getExpiryDate() {
        return expiryDate;
    }

    public SelenideElement getCardNumber() {
        return cardNumber;
    }

    public void clickOnBuyNowButton() {
        clickOnWebElement(buyNowButton);
    }

    public List<SelenideElement> getCvv() {
        return cvv.shouldHave(CollectionCondition.sizeGreaterThan(0));
    }
}

