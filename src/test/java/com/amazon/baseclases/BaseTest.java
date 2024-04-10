package com.amazon.baseclases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

import com.amazon.utilities.DateUtils;
import com.amazon.utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class BaseTest {

	public WebDriver driver;

	public ExtentReports report = ExtentReportManager.getReportInstance();

	public ExtentTest logger;

	/****************** Invoke Browser ***********************/
	public void invokeBrowser(String browserName) {

		try {
			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "//src//test//resources//drivers//chromedriver.exe");
				driver = new ChromeDriver();
			}
			else if (browserName.equalsIgnoreCase("Mozilla")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "//src//test//resources//drivers//geckodriver.exe");
				driver = new FirefoxDriver();
			}
			else {
				System.setProperty("webdriver.msedge.driver",
						System.getProperty("user.dir") + "//src//test//resources//drivers//msedgedriver.exe");
				driver = new EdgeDriver();
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(180));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
	}

	@AfterMethod
	public void flushReports() {
		report.flush();
		driver.close();
	}

	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			}
			else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			}
			else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void takeScreenShot() {
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

}
