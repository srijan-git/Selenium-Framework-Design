package com.SeleniumFrameworkDesign.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.SeleniumFrameworkDesign.TestComponent.BaseTest;
import com.SeleniumFrameworkDesign.TestComponent.RetryTests;
import com.pageobjects.CartPage;
import com.pageobjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = RetryTests.class)
	public void loginErrorValidation() {

		landingPage.loginApplication("srijankhan@gmail.com", "Sel@1234#");

		landingPage.getErrorMessage();

		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}

	@Test
	public void productErrorValidation() {

		String productNameString = "ZARA COAT 3";

		ProductCatalogue productCatalogue = landingPage.loginApplication("srijankhan@gmail.com", "Sel@1234#");

		List<WebElement> listOfProducts = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productNameString);

		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(productNameString);

		Assert.assertFalse(match);
	}

}
