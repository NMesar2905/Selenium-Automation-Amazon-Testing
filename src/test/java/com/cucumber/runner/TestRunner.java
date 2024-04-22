package com.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/cucumber/features",
				plugin = "json:target/jsonReports/cucumber-report.json",
				glue = "com.cucumber.stepdefinitions",
				tags = "@Exercise2")
public class TestRunner {

}
