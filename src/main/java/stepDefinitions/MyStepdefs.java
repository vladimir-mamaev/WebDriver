package stepDefinitions;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import dto.Error;
import dto.*;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import pages.BasketPage;
import pages.CheckoutPage;
import pages.InitialHomePage;
import pages.SearchPage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;
import static helper.WebDriverWaiter.waitForPageReadyState;
import static helper.WebDriverWaiter.waitForVisibilityOfElement;
import static org.assertj.core.api.Assertions.assertThat;


public class MyStepdefs {


    public MyStepdefs() {
    }

    @After
    public void CreateScreenshotAfterTest(Scenario scenario) {
        if (scenario.isFailed()) {
            String fileName = "Screenshot of" + scenario.getName();
            scenario.attach(screenshot(OutputType.BYTES), "image/png", fileName);
        }
    }


    InitialHomePage initialHomePage = new InitialHomePage();
    SearchPage searchPage = new SearchPage();
    BasketPage basketPage = new BasketPage();
    CheckoutPage checkoutPage = new CheckoutPage();

    @DataTableType
    public CardDetails getCardDetails(@Transpose Map<String, String> entry) {
        return new CardDetails(
                entry.get("cardNumber"),
                entry.get("Expiry Year"),
                entry.get("Expiry Month"),
                entry.get("Cvv")
        );
    }

    @DataTableType
    public Filters convertFilters(@Transpose Map<String, String> entry) {
        return new Filters(
                entry.get("Price range"),
                entry.get("Availability"),
                entry.get("Language"),
                entry.get("Format")
        );
    }

    @DataTableType
    public CheckoutOrderSummary getCheckoutOrderSummary(@Transpose Map<String, String> entry) {
        return new CheckoutOrderSummary(
                entry.get("Sub-total"),
                entry.get("Delivery"),
                entry.get("VAT"),
                entry.get("Total")
        );
    }

    @DataTableType
    public DeliveryAddress getDeliveryAddress(@Transpose Map<String, String> entry) {

        return new DeliveryAddress(
                entry.get("Full name"),
                entry.get("Delivery country"),
                entry.get("Address line 1"),
                entry.get("Address line 2"),
                entry.get("Town/City"),
                entry.get("County/State"),
                entry.get("Postcode")
        );
    }

    @DataTableType
    public Error getListOfError(@Transpose Map<String, String> entry) {
        return new Error(
                entry.get("Form field name"),
                entry.get("validaton error message")
        );
    }

    @DataTableType
    public Basket convertBasket(Map<String, String> entry) {
        return new Basket(
                entry.get("Delivery cost"),
                entry.get("Total")
        );
    }

    @Given("I am an anonymous customer with clear cookies")
    public void clearCookiesAsAnon() {
        Selenide.clearBrowserCookies();
    }

    @When("I open the {string}")
    public void openInitialHomePage(String pageName) {

        initialHomePage.openPage(pageName);
        waitForPageReadyState();
    }

    @And("I search for {string}")
    public void searchForThinkingInJava(String bookName) {
        initialHomePage.enterSearchField(bookName);
        waitForPageReadyState();
    }

    @Then("I am redirected to the {string}")
    @And("I am redirected to a {string}")
    public void redirectToPage(String pageName) {
        assertThat(WebDriverRunner.getWebDriver().getCurrentUrl().contains(pageName.substring(0, 6).toLowerCase()))
                .as("Current url doesn't match").isTrue();
        waitForPageReadyState();
    }

    @And("Search results contain the following products")
    public void checkResultsContainsFollowingBook(List<String> books) {
        List<String> searchList = getListOfValue(searchPage.getListOfBooks());
        assertThat((searchList).containsAll(books));
    }

    @And("I apply the following search filters")
    public void applyFollowingSearchFilters(Filters filters) {

        searchPage.applyPriceFilter(filters);
        searchPage.clickRefineResultsButton();
    }

    @Then("Search results contain only the following products")
    public void searchResultsContainOnlyTheFollowingProducts(List<String> listOfBooks) {
        List<String> searchList = getListOfValue(searchPage.getListOfBooks());
        assertThat(searchList).as("Book model change").isEqualTo(listOfBooks);
    }

    @When("I click 'Add to basket' button for product with name {string}")
    public void productAddToBasket(String bookName) {
        searchPage.clickOnButton(bookName);
    }

    @And("I select 'Basket Checkout' in basket pop-up")
    public void clickOnCheckout() {
        waitForPageReadyState();
        waitForVisibilityOfElement(searchPage.getBasketCheckoutButton());
        searchPage.clickButtonUsingJS(searchPage.getBasketCheckoutButton());
    }

    @And("Basket order summary is as following:")
    public void checkBasketOrderSummary(@Transpose Map<String, String> entry) {
        waitForPageReadyState();
        Basket basket = convertBasket(entry);
        assertThat(basketPage.getTextOfElement(basketPage.getDeliveryCost()))
                .as("Delivery cost changed")
                .isEqualTo(basket.getDeliveryCost());
        assertThat(basketPage.getTextOfElement(basketPage.getTotal()))
                .as("Total cost changed")
                .isEqualTo(basket.getTotal());
    }

    @When("I click 'Checkout' button on 'Basket' page")
    public void clickOnCheckoutBasketPage() {
        basketPage.clickButtonUsingJS(basketPage.getCheckoutButton());
    }

    @When("I click 'Buy now' button")
    public void clickOnBuyNowButton() {
        checkoutPage.clickOnBuyNowButton();
        waitForPageReadyState();
    }

    @Then("the following validation error messages are displayed on 'Delivery Address' form:")
    public void validationErrorMessages(List<Map<String, String>> maps) {
        List<String> list = maps.stream().map(this::getListOfError)
                .map(Error::getValidationErrorMessage).collect(Collectors.toList());
        assertThat(list).as("Error messages changed")
                .isEqualTo(checkoutPage.getListFromWebElements());
    }

    @Then("the following validation error messages are displayed on 'Payment' form:")
    public void checkValidationErrorsArePresent(String error) {
        checkoutPage.getPaymentErrorMessages().isDisplayed();
        String expected = checkoutPage.getTextOfElement(checkoutPage.getPaymentErrorMessages())
                .replaceAll("\n", ", ");
        assertThat(expected.equals(error));
    }

    @And("Checkout order summary is as following:")
    public void checkCheckoutOrderSummary(List<Map<String, String>> maps) {
        CheckoutOrderSummary checkoutOrderSummary = maps.stream().map(this::getCheckoutOrderSummary).findAny()
                .orElseThrow(() -> new NullPointerException("Mapping DataTable does not work"));

        assertThat(checkoutPage.getTextOfElement(checkoutPage.getSubTotal()))
                .as("Sub total changed")
                .isEqualTo(checkoutOrderSummary.getSubTotal());
        assertThat(checkoutPage.getTextOfElement(checkoutPage.getDelivery()))
                .as("Delivery cost changed")
                .isEqualTo(checkoutOrderSummary.getDelivery());
        assertThat(checkoutPage.getTextOfElement(checkoutPage.getVat()))
                .as("Vat changed")
                .isEqualTo(checkoutOrderSummary.getVat());
        assertThat(checkoutPage.getTextOfElement(checkoutPage.getTotal()))
                .as("Total price changed")
                .isEqualTo(checkoutOrderSummary.getTotal());
    }

    @And("I checkout as a new customer with email {string}")
    public void checkoutAsANewCustomer(String email) {
        refresh();
        checkoutPage.getEmail().sendKeys(email);
    }

    @When("I fill delivery address information manually:")
    public void fillDeliveryAddress(List<Map<String, String>> maps) {
        DeliveryAddress deliveryAddress = maps.stream().map(this::getDeliveryAddress).findAny()
                .orElseThrow(() -> new NullPointerException("Mapping DataTable does not work"));
        checkoutPage.clickAndHold(checkoutPage.getDeliveryCountry());
        checkoutPage.waitForDeliveryCountry(deliveryAddress);
        checkoutPage.getDeliveryCountryAndorra(deliveryAddress.getDeliveryCountry());
        checkoutPage.fillDeliveryAddress(deliveryAddress);

    }

    @When("I enter my card details")
    public void enterCardDetails(Map<String, String> maps) {
        checkoutPage.scrollDownThePage();
        CardDetails cardDetails = getCardDetails(maps);
        checkoutPage.clickAndFillCardFields(checkoutPage.getCardNumber(), cardDetails.getCardNumber());
        checkoutPage.clickAndFillCardFields(checkoutPage.getExpiryDate(),
                cardDetails.getExpiryMonth() + "\\" + cardDetails.getExpiryYear());
        checkoutPage.clickAndFillCardFields(checkoutPage.getCvv().get(0), cardDetails.getCvv());

    }

    @Then("the following validation error messages are not displayed on 'Payment' form:")
    public void validationErrorMessagesAreNotDisplayed() {
        assertThat(checkoutPage.isElementVisible("buynow-error-msg")).isFalse();
    }

    private List<String> getListOfValue(ElementsCollection list) {
        return list.texts();
    }

    @Given("^I open the '(.*)' page$")
    public void openPage(String url) {
        open(url);
    }

    @Then("^I verify header is '(.*)'$")
    public void verifyHeader(String expectedHeaderName) {
        assertThat(expectedHeaderName).as("Title change").isEqualTo(Selenide.title());
        System.out.println(Selenide.title());
    }

}