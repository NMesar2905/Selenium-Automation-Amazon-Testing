package com.amazon.baseclases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.pageclases.HomePage;
import com.amazon.pageclases.SearchResultPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


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
	
	private String suggestionText;
	
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
