package com.SeleniumFrameworkDesign.TestComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;

	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		Properties props = new Properties();

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/resources/GlobalData.properties");

		props.load(file);

		String browserNameString = System.getProperty("browser") != null ? System.getProperty("browser")
				: props.getProperty("browser");

		/* String browserNameString = props.getProperty("browser"); */

		if (browserNameString.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if (browserNameString.contains("headless")) {
				options.addArguments("headless");
			}

			this.driver = new ChromeDriver(options);
			this.driver.manage().window().setSize(new Dimension(1440, 900));
		} else if (browserNameString.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/src/main/java/com/resources/geckodriver");
			this.driver = new FirefoxDriver();
		}

		this.driver.manage().window().maximize();

		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {

		this.driver = initializeDriver();

		this.landingPage = new LandingPage(this.driver);

		landingPage.goTo();

		return landingPage;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		this.driver.close();
	}

	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		// File file =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		TakesScreenshot tScreenshot = (TakesScreenshot) driver;
		File sourceFile = tScreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceFile,
				new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png"));

		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

	}

}
