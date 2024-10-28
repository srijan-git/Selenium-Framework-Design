package com.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.AbstructComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	By productBy = By.cssSelector(".mb-3");

	By addToCart = By.cssSelector(".card-body button:last-of-type");

	By toastMessage = By.cssSelector("#toast-container");

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*
	 * Page factory is only applicable on webdriver but not on webElement
	 */

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinnerElement;

	public List<WebElement> getProductList() {

		waitForElementToAppear(productBy);
		return products;
	}

	public WebElement getProductByName(String productName) {

		WebElement productString = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		return productString;
	}

	public void addProductToCart(String productString) {

		WebElement prodElement = getProductByName(productString);

		prodElement.findElement(addToCart).click();

		waitForElementToAppear(toastMessage);

		waitForElementToDisappear(spinnerElement);

	}
}
