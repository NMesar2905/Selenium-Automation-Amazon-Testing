package com.amazon.pageclases;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.baseclases.BasePage;
import com.amazon.baseclases.TopMenuClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ProductPage extends BasePage{

	TopMenuClass topMenu;
	
	public ProductPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topMenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topMenu);
	}
	
	@FindBy(id = "productTitle")
	public List<WebElement> productNameElement;
	
	public String getProductPrice() {

		String productPrice = null;
		String productName = getProductName();
		try {
			WebElement productPriceElementsList = driver.findElement(By.xpath("//*[@class='a-section a-spacing-micro'][1]"));	
	        logger.log(Status.INFO, "Verifying price in Product Page");   
	        
	        productPrice = productPriceElementsList.getText().replace("\n", ".")
	        												 .replace(",", "");
	        
			logger.log(Status.PASS, "The price of the product: '" + productName + "' in product page is: '" + productPrice + "'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return productPrice;
	}
	
	public String getProductName() {
		String productName = null;
		try {
			logger.log(Status.INFO, "Verifying name in Product Page");
			productName = productNameElement.get(0).getText();
			logger.log(Status.PASS, "The product name in the page is: '" + productName + "'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return productName;
	}
	
	

}
