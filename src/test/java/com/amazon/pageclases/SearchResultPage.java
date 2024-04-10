package com.amazon.pageclases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.baseclases.BasePage;
import com.amazon.baseclases.TopMenuClass;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class SearchResultPage extends BasePage{

	TopMenuClass topMenu;
	
	public SearchResultPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topMenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topMenu);
	}
	
	@FindBy(xpath = "//*[@data-component-type='s-search-result']")
	public List<WebElement> searchProductResults;
	
	public String getProductPrice(int productSelection) {
		String productPrice = null;
		String productName = getProductName(productSelection);
		try {
			WebElement product = searchProductResults.get(productSelection - 1);
			List<WebElement> productPriceElement = product.findElements(By.xpath("//span[@class='a-offscreen']"));
			logger.log(Status.INFO, "Verifying price of Product: " + productSelection + "'");
//			productPrice = productPriceElement.get(0).getText();
			productPrice = productPriceElement.get(0).getAttribute("innerText");
			logger.log(Status.PASS, "The price of the product: '" + productName + "' is: '" + productPrice + "'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return productPrice;
	}
	
	public String getProductName(int productSelection) {
		String productName = null;
		try {
			WebElement product = searchProductResults.get(productSelection - 1);
			WebElement productNameElement = product
					.findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal'][1]"));
			logger.log(Status.INFO, "Verifying name of Product: '" + productSelection + "'");
			productName = productNameElement.getText();
			logger.log(Status.PASS, "The product name is: '" + productName + "'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return productName;
	}
	
	

}
