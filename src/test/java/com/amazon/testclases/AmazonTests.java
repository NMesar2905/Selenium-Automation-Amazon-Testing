package com.amazon.testclases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.amazon.baseclases.BasePage;
import com.amazon.baseclases.BaseTest;
import com.amazon.baseclases.TopMenuClass;
import com.amazon.pageclases.HomePage;
import com.amazon.pageclases.SearchResultPage;




public class AmazonTests extends BaseTest {

	TopMenuClass topMenu;

	HomePage homePage;
	
	SearchResultPage searchResultPage;
	
	@Test
	public void searchProductBySuggestion() {
		logger = report.createTest("[TC-01] : Search Product by Suggestion");
		invokeBrowser("Chrome");
		BasePage pageBase = new BasePage(driver, logger);
		PageFactory.initElements(driver, pageBase);
		homePage = pageBase.openApplication();
		topMenu = homePage.getTopMenu();
		searchResultPage = topMenu.searchProductBySuggestion("Iphone 15 Pro", 1);
		topMenu.verifyTitlePage();
		String productPrice = searchResultPage.getProductPrice(1);
		System.out.println("The product price is: "+productPrice);
		takeScreenShot();
	}
	
	@Test
	public void searchProductByButton() {
		logger = report.createTest("[TC-02] : Search Product by Search Button");
		invokeBrowser("Chrome");
		BasePage pageBase = new BasePage(driver, logger);
		PageFactory.initElements(driver, pageBase);
		homePage = pageBase.openApplication();
		topMenu = homePage.getTopMenu();
		searchResultPage = topMenu.searchProductByButton("Iphone 15 Pro");
		topMenu.verifyTitlePage();
		String productPrice = searchResultPage.getProductPrice(1);
		System.out.println("The product price is: "+productPrice);
		takeScreenShot();
	}

	

}
