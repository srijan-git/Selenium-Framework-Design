@tag
Feature: Purchase the order from Ecommerce website

  #Similar to @BeforeMethod
  Background: 
    Given I landed on Ecommerce Page

  #A feature can have multiple scenarios
  @Regression
  Scenario Outline: Positive Test of Submititng the order
    Given Logged in with <username> and <password>
    When I Add Product <productname> to cart
    And Checkout <productname> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | username             | password  | productname |
      | srijankhan@gmail.com | Sel@1234# | ZARA COAT 3 |
