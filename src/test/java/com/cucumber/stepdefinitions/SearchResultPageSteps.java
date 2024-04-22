package com.cucumber.stepdefinitions;

import org.testng.Assert;

import com.amazon.baseclases.BaseTest;

import io.cucumber.java.en.Then;

public class SearchResultPageSteps extends BaseTest{
	
	@Then("Verify Page Title")
	public void verify_page_title() {
		topMenu.verifyTitlePage();
	}
	@Then("Verify that price is correct of product {int} in Search Product Page")
	public void verify_that_price_is_correct_of_product_in_search_product_page(Integer productNumber) {
	    String productPrice = searchResultPage.getProductPrice(productNumber);
	    Assert.assertTrue(!productPrice.isEmpty());
	    Assert.assertTrue(!productPrice.equals(null));
	    
	    double productNumericPrice = Double.parseDouble(productPrice.split("\\$")[1]);
	    Assert.assertTrue(productNumericPrice>0);
	}
	
	@Then("Click the price of product {int}")
	public void click_the_price_of_product(Integer productNumber) {
		productPage = searchResultPage.clickProductPrice(productNumber);
	}

}
