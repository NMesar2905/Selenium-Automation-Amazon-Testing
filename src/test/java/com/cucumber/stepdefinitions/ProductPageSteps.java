package com.cucumber.stepdefinitions;

import org.testng.Assert;

import com.amazon.baseclases.BaseTest;

import io.cucumber.java.en.Then;

public class ProductPageSteps extends BaseTest{

	@Then("Verify that price is correct in Product Page")
	public void verify_that_price_is_correct_in_product_page() {
		String productPrice = productPage.getProductPrice();
	    Assert.assertTrue(!productPrice.isEmpty());
	    Assert.assertTrue(!productPrice.equals(null));
	    
	    double productNumericPrice = Double.parseDouble(productPrice.split("\\$")[1]);
	    Assert.assertTrue(productNumericPrice>0);
	}

}
