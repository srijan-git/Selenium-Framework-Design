package com.SeleniumFrameworkDesign.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.pageobjects.LandingPage;

public class StandaloneTest {

	public static void main(String[] args) {

		String productNameString = "ZARA COAT 3";

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://rahulshettyacademy.com/client/");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		LandingPage landingPage=new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("srijankhan@gmail.com");

		driver.findElement(By.id("userPassword")).sendKeys("Sel@1234#");

		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector(".mb-3"))));

		List<WebElement> listOfProducts = driver.findElements(By.cssSelector(".mb-3"));

		WebElement productString = listOfProducts.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productNameString))
				.findFirst().orElse(null);

		productString.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("#toast-container"))));

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		List<WebElement> cartProductsElements = driver.findElements(By.cssSelector(".cartSection h3"));

		Boolean match = cartProductsElements.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productNameString));

		Assert.assertTrue(match);

		driver.findElement(By.cssSelector(".totalRow button")).click();

		Actions actions = new Actions(driver);

		actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build()
				.perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();

		driver.findElement(By.cssSelector(".action__submit")).click();

		String confrimMessageString = driver.findElement(By.cssSelector(".hero-primary")).getText();

		Assert.assertTrue(confrimMessageString.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		driver.close();
	}

}
