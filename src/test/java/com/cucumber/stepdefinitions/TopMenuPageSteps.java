package com.cucumber.stepdefinitions;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.amazon.baseclases.BaseTest;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TopMenuPageSteps extends BaseTest{
	
	Map<String,List<String>> categoriesAndOptions;
	
	@When("User type {string} in search bar and click in suggestion {int}")
	public void user_type_in_search_bar(String productName, int suggestion) {
		topMenu = homePage.getTopMenu();
		searchResultPage = topMenu.searchProductBySuggestion(productName, suggestion);
	}
	
	
	@When("Click the hamburger menu and Get menu categories and subcategories")
	public void click_the_hamburger_menu_and_get_menu_categories_and_subcategories() {
		topMenu = homePage.getTopMenu();
		categoriesAndOptions = topMenu.getHamburguerOptions();
	}

	
	@Then("Compare the Category using {string}")
	public void compare_the_category_using(String categoryOption) {
		String buscarPorCategoriaJSONPath = prop.getProperty(categoryOption).replace("${user.dir}", System.getProperty("user.dir"));
		topMenu.verifyCategoryAndOptions(new File(buscarPorCategoriaJSONPath), categoriesAndOptions);
	}



}
