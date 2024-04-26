package com.amazon.baseclases;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.amazon.pageclases.HomePage;
import com.amazon.pageclases.SearchResultPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TopMenuClass extends BasePage {

	public TopMenuClass(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	@FindBy(xpath = "//a[@aria-label='Amazon']")
	public WebElement amazonLogo;

	@FindBy(id = "searchDropdownBox")
	public WebElement categoryDropDown;

	@FindBy(id = "twotabsearchtextbox")
	public WebElement searchBar;
	
	@FindBy(id = "nav-search-submit-button")
	public WebElement searchButton;
	
	@FindBy(id = "nav-hamburger-menu")
	public WebElement hamburguerButton;
	
	@FindBy(xpath = "//*[@data-menu-id='1']")
	public WebElement hamburguerOptions;
	
	private String suggestionText;
	
	public Map<String, List<String>> getHamburguerOptions() {
		Map<String, List<String>> categoriesAndOptions = null;
		try {
		hamburguerButton.click();
		waitLoad(2);
		System.out.println("GETTEXT(): "+hamburguerOptions.getText());
		logger.log(Status.INFO, "Getting Categories and Sub Categories from Hamburger Menu");
		String[] hamburgerOptionsList = hamburguerOptions.getText().split("\n");
		String key=null;
		List<String> categoryOptions = new ArrayList<String>();
		
		categoriesAndOptions = new HashMap<String, List<String>>();
		for (int i = 0; i < hamburgerOptionsList.length-1; i++) {

			if(hamburgerOptionsList[i].equalsIgnoreCase("Tendencias")
					||hamburgerOptionsList[i].equalsIgnoreCase("Contenido y dispositivos digitales")
					||hamburgerOptionsList[i].equalsIgnoreCase("Buscar por categoría")
					||hamburgerOptionsList[i].equalsIgnoreCase("Programas y características")
					||hamburgerOptionsList[i].equalsIgnoreCase("Ayuda y configuración")) {
				categoriesAndOptions.put(key, categoryOptions);
				categoryOptions = new ArrayList<String>();
				key = hamburgerOptionsList[i];
			}else {
				categoryOptions.add(hamburgerOptionsList[i]);
			}
		}
			categoriesAndOptions.remove(null);
			logger.log(Status.PASS, "Created Map of Categories And Subcategories, the map is: " + categoriesAndOptions);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
		
		return categoriesAndOptions;
	}
	
	
	public void verifyCategoryAndOptions(File jsonFile, Map<String, List<String>> categoriesAndOptions) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, List<String>> expectedCategoryAndOptions = objectMapper.readValue(jsonFile, Map.class);
			logger.log(Status.INFO, "The expected Specific Category Map is: '" + expectedCategoryAndOptions + "'");
			
			String categoryToCompare =  List.copyOf(expectedCategoryAndOptions.keySet()).get(0);
			
			logger.log(Status.INFO, "Category to compare is: " + categoryToCompare);
			
			logger.log(Status.INFO, "Getting Actual Map of the specific Category: " + categoryToCompare);
			Map<String, List<String>> actualCategoryAndOptions = new HashMap<String, List<String>>();
			actualCategoryAndOptions.put(categoryToCompare, categoriesAndOptions.get(categoryToCompare));
			logger.log(Status.PASS, "Created Actual Map of specific Category: " + actualCategoryAndOptions);
			
			logger.log(Status.INFO, "Checking Actual Category Map and Expected Category Map");
			Assert.assertTrue(actualCategoryAndOptions.equals(expectedCategoryAndOptions));
			logger.log(Status.PASS, "The Actual Category Map is: '"+actualCategoryAndOptions+"'"+"\n"
					+"and the Expected Catergory Map is: '" + expectedCategoryAndOptions+ "'");
			
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	public HomePage clickHomeBtn() {
		logger.log(Status.INFO, "Clicking the Amazon Logo (Home)");
		amazonLogo.click();
		logger.log(Status.PASS, "Clicked the Amazon Logo (Home)");
		HomePage homePage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homePage);
		return homePage;
	}

	public SearchResultPage searchProductByButton(String productName) {
		try {
			writeProductName(productName);
			clickSearchButton();
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
		return returnSearchResultPage();
	}
	
	public SearchResultPage searchProductBySuggestion(String producName, int productSuggestionNumber) {
		try {
			writeProductName(producName);
			waitLoad(2);
			selectSuggestion(productSuggestionNumber);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
		
		return returnSearchResultPage();
	}
	
	public void verifyTitlePage() {
			String searchBarValue = searchBar.getAttribute("value");
			getTitle("Amazon.com : "+searchBarValue);
	}
	
	private void selectSuggestion(int i) {
		logger.log(Status.INFO, "Selecting the suggestion number: '"+i+"'");
		List<WebElement> searchSuggestions = driver.findElements(By.xpath("//div/div[@class='left-pane-results-container']/div"));
		WebElement suggestion = searchSuggestions.get(i-1);
		suggestionText = suggestion.getText();
		logger.log(Status.INFO, "Clicking the suggestion: '"+suggestionText+"'");
		suggestion.click();
		logger.log(Status.PASS, "Clicked the  suggestion: '"+suggestionText+"'");
	}

	private void writeProductName(String productName) {
		logger.log(Status.INFO, "Writing the product name: '"+productName+"' in search bar");
		searchBar.sendKeys(productName);
	}
	
	private void clickSearchButton() {
		logger.log(Status.INFO, "Clicking the Search Button");
		searchButton.click();
		logger.log(Status.PASS, "Clicked the Search Button");
	}
	
	private SearchResultPage returnSearchResultPage() {
		SearchResultPage searchResultPage = new SearchResultPage(driver, logger);
		PageFactory.initElements(driver, searchResultPage);
		return searchResultPage;
	}







}
