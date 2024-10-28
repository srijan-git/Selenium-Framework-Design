package com.SeleniumFrameworkDesign.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.SeleniumFrameworkDesign.TestComponent.BaseTest;
import com.SeleniumFrameworkDesign.TestComponent.RetryTests;
import com.pageobjects.CartPage;
import com.pageobjects.CheckOutPage;
import com.pageobjects.ConfirmationPage;
import com.pageobjects.OrderPage;
import com.pageobjects.ProductCatalogue;
import com.seleniumFrameworkDesign.Data.DataReader;

public class SubmitOrderTest extends BaseTest {

	@Test(dataProvider = "getData", groups = { "Purchase" }, retryAnalyzer = RetryTests.class)
	public void submitOrder(HashMap<String, String> data) throws IOException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(data.get("email"), data.get("password"));

		List<WebElement> listOfProducts = productCatalogue.getProductList();

		productCatalogue.addProductToCart(data.get("product"));

		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(data.get("product"));

		Assert.assertTrue(match);

		CheckOutPage checkOutPage = cartPage.gotToCheckOut();

		checkOutPage.selectCountry("India");

		ConfirmationPage confirmationPage = checkOutPage.submitOrder();

		String confirmationMessage = confirmationPage.getConfirmationMessage();

		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	// To verify a specific product is displaying in orders page (Dependency test)
	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("srijankhan@gmail.com", "Sel@1234#");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay("ZARA COAT 3"));
	}

	// Json data
	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = DataReader.getJsonDataToHashMap(
				System.getProperty("user.dir") + "/src/test/java/com/seleniumFrameworkDesign/Data/PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

//	@DataProvider
//	public Object[][] getData() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "srijankhan@gmail.com");
//		map.put("password", "Sel@1234#");
//		map.put("product", "ZARA COAT 3");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "anshika@gmail.com");
//		map1.put("password", "Iamking@000");
//		map1.put("product", "ADIDAS ORIGINAL");
//
//		return new Object[][] { { map }, { map1 } };
//	}

//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] { { "srijankhan@gmail.com", "Sel@1234#","ZARA COAT 3"}, { "anshika@gmail.com", "Iamking@000","ADIDAS ORIGINAL"} };
//	}

}
