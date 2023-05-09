package pages;


import com.codeborne.selenide.SelenideElement;

import page.AbstractPage;

import static com.codeborne.selenide.Selenide.$x;

public class InitialHomePage extends AbstractPage {
    private final SelenideElement searchField = $x("//input[@name = 'searchTerm']");

    public void enterSearchField(String bookName) {
        searchField.append(bookName).pressEnter();
    }
}