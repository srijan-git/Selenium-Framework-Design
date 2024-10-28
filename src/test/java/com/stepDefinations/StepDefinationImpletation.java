package com.stepDefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;

import com.SeleniumFrameworkDesign.TestComponent.BaseTest;
import com.pageobjects.CartPage;
import com.pageobjects.CheckOutPage;
import com.pageobjects.ConfirmationPage;
import com.pageobjects.LandingPage;
import com.pageobjects.ProductCatalogue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinationImpletation extends BaseTest {

	public LandingPage landingPage;

	public ProductCatalogue productCatalogue;

	public CartPage cartPage;

	public ConfirmationPage confirmationPage;

	public CheckOutPage checkOutPage;

	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}

	// (.+) is a regular expression to express that this statement takes arguments
	@Given("^Logged in with (.+) and (.+)$")
	public void Logged_in_with_usernamae_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}

	@When("^I Add Product (.+) to cart$")
	public void I_Add_Product_productname_to_cart(String productName) {
		List<WebElement> listOfProducts = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}

	@And("^Checkout (.+) and submit the order$")
	public void Checkout_productname_and_submit_the_order(String productName) {
		cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(productName);

		Assert.assertTrue(match);

		checkOutPage = cartPage.gotToCheckOut();

		checkOutPage.selectCountry("India");

		confirmationPage = checkOutPage.submitOrder();
	}

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_on_confirmation_page(String message) {
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();
	}

	@Then("^\"([^\"]*)\" message is displayed")
	public void something_message_is_displayed(String messsage) throws Throwable {

		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		driver.close();
	}
}
