package com.cucumber.stepdefinitions;

import org.openqa.selenium.support.PageFactory;

import com.amazon.baseclases.BasePage;
import com.amazon.baseclases.BaseTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class HomePageSteps extends BaseTest{
	
	@Before
	public void openBrowser() {
		invokeBrowser(prop.getProperty("browser"));
	}

	@After
	public void tearDown() {
		takeScreenShot();
		driver.close();
		report.flush();
	}
	
	@Given("The name of the test {string}")
	public void the_name_of_the_test(String testName) {
		logger = report.createTest(testName);
	}
	
	@Given("User is on Amazon Landing Page")
	public void user_is_on_amazon_landing_page() {
		BasePage pageBase = new BasePage(driver, logger);
		PageFactory.initElements(driver, pageBase);
	    homePage = pageBase.openApplication();
	}

}
