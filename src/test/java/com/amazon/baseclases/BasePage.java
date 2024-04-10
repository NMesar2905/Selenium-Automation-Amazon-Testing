package com.amazon.baseclases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.amazon.pageclases.HomePage;
import com.amazon.utilities.DateUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class BasePage extends BaseTest {

	public ExtentTest logger;

	public BasePage(WebDriver driver, ExtentTest logger) {
		this.logger = logger;
		this.driver = driver;
	}

	/****************** Open Application ***********************/
	public HomePage openApplication() {
		logger.log(Status.INFO, "Opening the Amazon Web Page");
		driver.get("https://www.amazon.com/");
		logger.log(Status.PASS, "Succesfully Opened the https://www.amazon.com/");
		HomePage landingPage = new HomePage(driver, logger);
		PageFactory.initElements(driver, landingPage);
		return landingPage;
	}

	/****************** Get Page Title ***********************/
	public void getTitle(String expectedTitle) {
		try {
			logger.log(Status.INFO, "Verifying the Title Page");
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			reportPass("Actual Title : '" + driver.getTitle() + "' - equals to Expected Title : '" + expectedTitle+"'");
		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}

	}


	/*************** Reporting Functions ***************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	/*************** Capture Screenshot ****************/
	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

		String fileName = DateUtils.getTimeStamp() + ".png";
		File destFile = new File(System.getProperty("user.dir") + "/ScreenShots/" + fileName);
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/ScreenShots/" + fileName);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*************** Set a Date ****************/
	public void clickDataPicker(WebElement webElement, String birthDate) {
		try {

			int currentYear = Year.now().getValue();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			Date expectedDate = dateFormat.parse(birthDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(expectedDate);

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);

			// Select Day
			for (int i = 1; i <= 31; i++) {
				webElement.sendKeys(Keys.ARROW_UP);
				if (i == day) {
					break;
				}
			}

			// Select Month
			webElement.sendKeys(Keys.ARROW_RIGHT);
			for (int i = 1; i <= 12; i++) {
				webElement.sendKeys(Keys.ARROW_UP);
				if (i == month) {
					break;
				}
			}

			// Select Year
			webElement.sendKeys(Keys.ARROW_RIGHT);
			webElement.sendKeys(Keys.ARROW_UP);
			int diffYears = currentYear - year;
			if (currentYear != year) {
				for (int i = 0; i < diffYears; i++) {
					webElement.sendKeys(Keys.ARROW_DOWN);
				}
			}

		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/*************** Set a Date with GUID ****************/
	public void clickDataPickerUI(WebElement webElement, String birthDate) {
		try {

			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date expectedDate = dateFormat.parse(birthDate);

			long diffMiliSeconds = currentDate.getTime() - expectedDate.getTime();
			long diffDays = TimeUnit.MILLISECONDS.toDays(diffMiliSeconds);

			webElement.clear();
			webElement.sendKeys(Keys.SPACE);
			int i = 0;
			do {
				if (diffDays > 0) {
					webElement.sendKeys(Keys.ARROW_LEFT);
				}
				if (diffDays < 0) {
					webElement.sendKeys(Keys.ARROW_RIGHT);
				}
				i++;
			}
			while (i != Math.abs(diffDays));

		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/*************** Select value from drop down ****************/
	public void selectDropDownValue(WebElement webElement, String value) {
		try {
			Select select = new Select(webElement);
			select.selectByVisibleText(value);
			logger.log(Status.PASS, "Selected the Value : " + value);
		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********** Check if Method parameter have values ************/
	public boolean haveValues(String parameter) {
		return parameter != null && !parameter.trim().isEmpty() && parameter.matches(".*[a-zA-Z0-9].*");
	}

}
