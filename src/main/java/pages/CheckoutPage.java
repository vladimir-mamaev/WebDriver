package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static singltoneDriver.SingletonDriver.getDriver;

public class CheckoutPage extends AbstractPage {
    @FindBy(xpath = "//button[@id='buyNowButton']")
    private WebElement buyNowButton;
    @FindBy(id = "email-errors")
    private WebElement emailErrorMessage;
    @FindBy(id = "delivery-fullName-errors")
    private WebElement fullNameErrorMessage;
    @FindBy(id = "delivery-addressLine1-errors")
    private WebElement deliveryAddressLine1ErrorMessage;
    @FindBy(id = "delivery-city-errors")
    private WebElement deliveryCityErrorMessage;
    @FindBy(id = "delivery-postCode-errors")
    private WebElement deliveryPostCodeErrorMessage;
    @FindBy(className = "buynow-error-msg")
    private WebElement paymentErrorMessages;
    @FindBy(xpath = "//div[@aria-label='Sub-total 85,62 €']//dd[contains(text(),'85')]")
    private WebElement subTotal;
    @FindBy(xpath = "//div[@aria-label='Delivery FREE']//strong[contains(text(),'FREE')]")
    private WebElement delivery;
    @FindBy(xpath = "//div[@aria-label='VAT 0,00 €']//dd[contains(text(),'0,00 €')]")
    private WebElement vat;
    @FindBy(xpath = "//div[@aria-label='Total 85,62 €']//dd[contains(text(),'85')]")
    private WebElement total;
    @FindBy(xpath = "//input[@aria-label='Email address (for order confirmation)']")
    private WebElement email;
    @FindBy(xpath = "//input[@name='delivery-fullName']")
    private WebElement deliveryFullNameField;
    @FindBy(xpath = "//input[@name='delivery-addressLine2']")
    private WebElement deliveryAddressLine2;
    @FindBy(xpath = "//input[@name='delivery-city']")
    private WebElement deliveryCity;
    @FindBy(xpath = "//input[@name='delivery-county']")
    private WebElement deliveryCounty;
    @FindBy(xpath = "//input[@name='delivery-postCode']")
    private WebElement deliveryPostCode;
    @FindBy(xpath = "//input[@name='delivery-addressLine1']")
    private WebElement deliveryAddressLine1;
    @FindBy(xpath = "//select[@name='deliveryCountry']")
    private WebElement deliveryCountry;
    @FindBy(xpath = "//label[@for='expiration-date']")
    private WebElement expiryDate;
    @FindBy(xpath = "//label[@for='cvv']")
    private List<WebElement> cvv;
    @FindBy(xpath = "//label[@for='card-number']")
    private WebElement cardNumber;
    public WebElement getSubTotal() {
        return subTotal;
    }
    public WebElement getDelivery() {
        return delivery;
    }
    public WebElement getVat() {
        return vat;
    }
    public WebElement getTotal() {
        return total;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getDeliveryFullNameField() {
        return deliveryFullNameField;
    }

    public WebElement getDeliveryAddressLine2() {
        return deliveryAddressLine2;
    }
    public WebElement getDeliveryCity() {
        return deliveryCity;
    }
    public WebElement getDeliveryCounty() {
        return deliveryCounty;
    }
    public WebElement getDeliveryPostCode() {
        return deliveryPostCode;
    }
    public WebElement getDeliveryAddressLine1() {
        return deliveryAddressLine1;
    }

    public WebElement getDeliveryCountryAndorra(String country) {
        return getDriver().findElement(By
                .xpath("//li[@name='option-AD']//a[contains(text(), '" + country + "')]"));
    }

    public WebElement getDeliveryCountry() {
        return deliveryCountry;
    }

    public List<String> getListFromWebElements() {
        return Stream.of(emailErrorMessage, fullNameErrorMessage
                        , deliveryAddressLine1ErrorMessage, deliveryCityErrorMessage, deliveryPostCodeErrorMessage).
                map(WebElement::getText).collect(Collectors.toList());
    }

    public WebElement getPaymentErrorMessages() {
        return paymentErrorMessages;
    }
    public WebElement getExpiryDate() {
        return expiryDate;
    }
    public WebElement getCardNumber() {
        return cardNumber;
    }
    public void clickOnBuyNowButton() {
        buyNowButton.click();
    }
    public List<WebElement> getCvv() {
        return cvv;
    }
}

