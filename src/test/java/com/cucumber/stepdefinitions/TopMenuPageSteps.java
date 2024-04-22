package com.cucumber.stepdefinitions;

import com.amazon.baseclases.BaseTest;

import io.cucumber.java.en.When;

public class TopMenuPageSteps extends BaseTest{
	
	@When("User type {string} in search bar and click in suggestion {int}")
	public void user_type_in_search_bar(String productName, int suggestion) {
		topMenu = homePage.getTopMenu();
		searchResultPage = topMenu.searchProductBySuggestion(productName, suggestion);
	}

}
