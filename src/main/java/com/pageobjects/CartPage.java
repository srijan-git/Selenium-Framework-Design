package com.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.AbstructComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProductsElements;

	@FindBy(css = ".totalRow button")
	WebElement checkOutElement;

	public Boolean verifyProductDisplay(String productName) {

		Boolean match = cartProductsElements.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));

		return match;
	}

	public CheckOutPage gotToCheckOut() {
		checkOutElement.click();
		return new CheckOutPage(driver);
	}
}
