package stepDefinitions;

import dto.*;
import dto.Error;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import pages.BasketPage;
import pages.CheckoutPage;
import pages.SearchPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import pages.InitialHomePage;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static constant.Constants.IMPLICITLY_WAIT_TIMEOUT;
import static constant.Constants.TIME_TO_WAIT_ELEMENT;
import static helper.WebDriverWaiter.waitForPageReadyState;
import static helper.WebDriverWaiter.waitForVisibilityOfElement;
import static org.junit.Assert.*;
import static singltoneDriver.SingletonDriver.getDriver;

public class MyStepdefs {
    public MyStepdefs() {
    }

    InitialHomePage initialHomePage = new InitialHomePage();
    SearchPage searchPage = new SearchPage();
    BasketPage basketPage = new BasketPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    @After
    public void tearDown() {
        getDriver().quit();
    }

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

    @Before
    public void testSetUp() {
        getDriver();
    }

    @Given("I am an anonymous customer with clear cookies")
    public void clearCookiesAsAnon() {
        getDriver().manage().deleteAllCookies();
    }

    @When("I open the {string}")
    public void openInitialHomePage(String pageName) {

        initialHomePage.openPage(pageName);
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
    }

    @And("I search for {string}")
    public void searchForThinkingInJava(String bookName) {
        initialHomePage.enterSearchField(bookName);
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
    }

    @Then("I am redirected to the {string}")
    @And("I am redirected to a {string}")
    public void redirectToPage(String pageName) {
        assertTrue(getDriver().getCurrentUrl().contains(pageName.substring(0, 6).toLowerCase()));
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
    }

    @And("Search results contain the following products")
    public void checkResultsContainsFollowingBook(List<String> books) {
        List<String> searchList = getListOfValue(searchPage.getListOfBooks());
        assertTrue(new HashSet<>(searchList).containsAll(books));
    }

    @And("I apply the following search filters")
    public void applyFollowingSearchFilters(Map<String, String> map) {
        Filters filters = convertFilters(map);
        searchPage.selectOptions(searchPage.getFilterPrice(), filters.getPriceRange());
        searchPage.selectOptions(searchPage.getFilterAvailability(), filters.getAvailability());
        searchPage.selectOptions(searchPage.getFilterLang(), filters.getLanguage());
        searchPage.selectOptions(searchPage.getFilterFormat(), filters.getFormat());
        searchPage.clickRefineResultsButton();
    }

    @Then("Search results contain only the following products")
    public void searchResultsContainOnlyTheFollowingProducts(List<String> listOfBooks) {
        List<String> searchList = getListOfValue(searchPage.getListOfBooks());
        assertEquals(searchList, listOfBooks);
    }

    @When("I click 'Add to basket' button for product with name {string}")
    public void productAddToBasket(String bookName) {
        List<String> searchList = getListOfValue(searchPage.getListOfBooks());
        searchPage.getListOfButton().get(searchList.indexOf(bookName)).click();
    }

    @And("I select 'Basket Checkout' in basket pop-up")
    public void clickOnCheckout() {
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
        waitForVisibilityOfElement(TIME_TO_WAIT_ELEMENT, searchPage.getBasketCheckoutButton());
        searchPage.clickButtonUsingJS(searchPage.getBasketCheckoutButton());
    }

    @And("Basket order summary is as following:")
    public void checkBasketOrderSummary(@Transpose Map<String, String> entry) {
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
        Basket basket = convertBasket(entry);
        assertEquals(basketPage.getDeliveryCost().getText(), (basket.getDeliveryCost()));
        assertEquals(basketPage.getTotal().getText(), (basket.getTotal()));
    }

    @When("I click 'Checkout' button on 'Basket' page")
    public void clickOnCheckoutBasketPage() {
        basketPage.clickButtonUsingJS(basketPage.getCheckoutButton());
    }

    @When("I click 'Buy now' button")
    public void clickOnBuyNowButton() {
        checkoutPage.clickOnBuyNowButton();
        waitForPageReadyState(IMPLICITLY_WAIT_TIMEOUT);
    }

    @Then("the following validation error messages are displayed on 'Delivery Address' form:")
    public void validationErrorMessages(List<Map<String, String>> maps) {
        List<String> list = maps.stream().map(this::getListOfError)
                .map(Error::getValidationErrorMessage).collect(Collectors.toList());
        assertEquals(list, checkoutPage.getListFromWebElements());
    }

    @Then("the following validation error messages are displayed on 'Payment' form:")
    public void checkValidationErrorsArePresent(String error) {
        checkoutPage.getPaymentErrorMessages().isDisplayed();
        String expected = checkoutPage.getPaymentErrorMessages().getText().replaceAll("\n", ", ");
        assertTrue(expected.equals(error));
    }

    @And("Checkout order summary is as following:")
    public void checkCheckoutOrderSummary(List<Map<String, String>> maps) {
        Optional<CheckoutOrderSummary> checkoutOrderSummary = maps.stream().map(this::getCheckoutOrderSummary).findAny();
        assertEquals(checkoutPage.getSubTotal().getText(), checkoutOrderSummary.get().getSubTotal());
        assertEquals(checkoutPage.getDelivery().getText(), checkoutOrderSummary.get().getDelivery());
        assertEquals(checkoutPage.getVat().getText(), checkoutOrderSummary.get().getVat());
        assertEquals(checkoutPage.getTotal().getText(), checkoutOrderSummary.get().getTotal());
    }

    @And("I checkout as a new customer with email {string}")
    public void checkoutAsANewCustomer(String email) {
        getDriver().navigate().refresh();
        checkoutPage.getEmail().sendKeys(email);
    }

    @When("I fill delivery address information manually:")
    public void fillDeliveryAddress(List<Map<String, String>> maps) {
        Optional<DeliveryAddress> deliveryAddress = maps.stream().map(this::getDeliveryAddress).findAny();
        checkoutPage.clickAndHold(checkoutPage.getDeliveryCountry());
        waitForVisibilityOfElement(TIME_TO_WAIT_ELEMENT, checkoutPage.getDeliveryCountryAndorra(deliveryAddress.get().getDeliveryCountry()));
        checkoutPage.chooseOption(checkoutPage.getDeliveryCountryAndorra(deliveryAddress.get().getDeliveryCountry()));
        waitForVisibilityOfElement(TIME_TO_WAIT_ELEMENT, checkoutPage.getDeliveryAddressLine1());
        checkoutPage.getDeliveryFullNameField().sendKeys(deliveryAddress.get().getFullName());
        checkoutPage.getDeliveryAddressLine1().sendKeys(deliveryAddress.get().getAddressLine1());
        checkoutPage.getDeliveryAddressLine2().sendKeys(deliveryAddress.get().getAddressLine2());
        checkoutPage.getDeliveryCity().sendKeys(deliveryAddress.get().getTownCity());
        checkoutPage.getDeliveryCounty().sendKeys(deliveryAddress.get().getCountyState());
        checkoutPage.getDeliveryPostCode().sendKeys(deliveryAddress.get().getPostCode());
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
        assertFalse(checkoutPage.isElementPresent("buynow-error-msg"));

    }

    private List<String> getListOfValue(List<WebElement> list) {
        return list.stream().map(WebElement::getText).collect(Collectors.toList());
    }

}