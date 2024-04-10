package com.amazon.pageclases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.amazon.baseclases.BasePage;
import com.amazon.baseclases.TopMenuClass;
import com.aventstack.extentreports.ExtentTest;

public class HomePage extends BasePage{

	TopMenuClass topMenu;
	
	public HomePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
		topMenu = new TopMenuClass(driver, logger);
		PageFactory.initElements(driver, topMenu);
	}
	
	public TopMenuClass getTopMenu() {
		return topMenu;
	}

}
