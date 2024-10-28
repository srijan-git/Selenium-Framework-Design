@tag
Feature: Error Validation

  @ErrorValidation
  Scenario Outline: Title of your scenario outline
    Given I landed on Ecommerce Page
    When Logged in with <username> and <password>
    Then "Incorrect email or password." message displayed

    Examples: 
      | username             | password  |
      | srijankhan@gmail.com | Sel@1234# |
