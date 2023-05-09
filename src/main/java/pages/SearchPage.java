package pages;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dto.Filters;
import page.AbstractPage;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPage extends AbstractPage {

    private ElementsCollection listOfBooks = $$("//div[@class='book-item']//h3[@class='title']//a");

    private List<SelenideElement> listOfButton = $$("//a[contains(text(), 'Add to basket')]");

    private SelenideElement filterPrice = $("//select[@id='filterPrice']");

    private SelenideElement filterAvailability = $("//select[@id='filterAvailability']");

    private SelenideElement filterLang = $("//select[@id='filterLang']");

    private SelenideElement filterFormat = $("//select[@id='filterFormat']");

    private SelenideElement buttonRefineResults = $("//button[contains(text(),'Refine results')]");

    private SelenideElement basketCheckoutButton = $("//a[contains(text(), 'Basket / Checkout')]");

    public SelenideElement getBasketCheckoutButton() {
        return basketCheckoutButton;
    }

    public void clickRefineResultsButton() {
        clickOnWebElement(buttonRefineResults);
    }

    public ElementsCollection getListOfBooks() {
        return listOfBooks;
    }

    public List<SelenideElement> getListOfButton() {
        return listOfButton;
    }

    public void clickOnButton(String button) {
        List<String> searchList = getListOfValue(getListOfBooks());
        clickOnWebElement(getListOfButton().get(searchList.indexOf(button)));
    }

    public SearchPage applyPriceFilter(Filters filters) {
        selectOptions(filterPrice, filters.getPriceRange());
        selectOptions(filterAvailability, filters.getAvailability());
        selectOptions(filterLang, filters.getLanguage());
        selectOptions(filterFormat, filters.getFormat());
        return this;
    }

    private List<String> getListOfValue(List<SelenideElement> list) {
        return list.stream().map(SelenideElement::getText).collect(Collectors.toList());
    }
}
