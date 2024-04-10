package com.amazon.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	// public static ExtentSparkReporter htmlReporter;
	static ExtentReports report;

	public static ExtentReports getReportInstance() {
		if (report == null) {
			String reportName = DateUtils.getTimeStamp() + ".html";
			report = new ExtentReports();
			ExtentSparkReporter htmlReporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + "/test-output/Extent-Reports/" + reportName);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setReportName("PetClinic UI Test Report");
			htmlReporter.config().setDocumentTitle("UI automation Results");

			report.attachReporter(htmlReporter);

			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Environment", "DEV/QA");
			report.setSystemInfo("Browser", "Chrome");

		}

		return report;
	}

}
