@Regression
Feature: Desktop Checkout for Guest User
  As a customer
  I want to be able proceed to checkout
  So that I can specify my delivery and payment details and place the order

  Scenario: Proceed to checkout, final review and place order as guest user
    Given I am an anonymous customer with clear cookies
    When I open the 'Initial home page'
    And I search for 'Thinking in Java'
    And I am redirected to a "Search page"
    And Search results contain the following products
      | Thinking in Java       |
      | Thinking Java Part I   |
      | Core Java Professional |
    And I apply the following search filters
      | Price range  | 30 € +        |
      | Availability | In Stock (5)  |
      | Language     | English (17)  |
      | Format       | Paperback (22)|
    Then Search results contain only the following products
      | Thinking in Java                                                   |
      | Think Java                                                         |
      | Thinking Recursively - A 20th Anniversary Edition with Java (WSE)  |
      | Think Data Structures                                              |
    When I click 'Add to basket' button for product with name "Thinking in Java"
    And I select 'Basket Checkout' in basket pop-up
    Then I am redirected to the "Basket page"
    And Basket order summary is as following:
      | Delivery cost | Total   |
      | FREE          | 85,62 € |
    When I click 'Checkout' button on 'Basket' page
    Then I am redirected to the "Payment page"
    When I click 'Buy now' button
    Then the following validation error messages are displayed on 'Delivery Address' form:
      | Form field name | validaton error message                                  |
      | Email addres    | Please enter your Email address (for order confirmation) |
      | Full name       | Please enter your Full name                              |
      | Address line 1  | Please enter your Address line 1                         |
      | Town/City       | Please enter your Town/City                              |
      | Postcode/ZIP    | Please enter your postcode/ZIP or write 'No Postcode'    |
    Then the following validation error messages are displayed on 'Payment' form:
      | Please enter your card number, Please enter your card's expiration date, Please enter your CVV |
    And Checkout order summary is as following:
      | Sub-total | Delivery | VAT    | Total   |
      | 85,62 €   | FREE     | 0,00 € | 85,62 € |
    And I checkout as a new customer with email "test@user.com"
    When I fill delivery address information manually:
      | Full name   | Delivery country | Address line 1   | Address line 2   | Town/City | County/State | Postcode |
      | John  John  | Andorra          | Random address 1 | Random address 2 | Kyiv      | Random State | 12345    |
    Then the following validation error messages are not displayed on 'Payment' form:
    When I enter my card details
      | cardNumber   | 4111111111111111 |
      | Expiry Year  | 2022             |
      | Expiry Month | 03               |
      | Cvv          | 123              |
  Scenario: User opens Facebook Home Page
    Given I open the 'https://facebook.com' page
    When I verify header is 'Facebook - log in or sign up'