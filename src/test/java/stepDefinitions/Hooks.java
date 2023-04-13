package stepDefinitions;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.aeonbits.owner.ConfigFactory;
import Environments.Environment;
import base.Base;
import base.BrowserActions;
import base.ElementActions;
import driver.TargetType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.qameta.allure.Allure;
import utils.ConfigReader;
import utils.JsonFileHandler;
import utils.MyLogger;

public class Hooks extends Base {
	public Environment cfg;
	public TargetType targettype;
	private JsonFileHandler jsonfilehandler;

	@Before
	public void initialization(Scenario scenario) throws IOException {
		MyLogger.info("Reading Data Json files");
		jsonfilehandler = new JsonFileHandler();
		countriesdata = jsonfilehandler.loadJson("CountriesData");
		
		// Update Environment parameters
		MyLogger.info("Update Environment parameters using owner library");
		cfg = ConfigFactory.create(Environment.class);

		// save the environment variable into threadlocal
		MyLogger.info("save the environment variable into threadlocal");
		setEnvironment(cfg);
		// make new class from targettype class and get environment and pass the
		// environment to it
		// initialize target class to choose to work locally or remotely
		MyLogger.info("initialize target class to choose to work locally or remotely");
		targettype = new TargetType(getEnvironment().gettarget(), ConfigReader.getBrowserType());

		// Set the driver
		MyLogger.info("Set the driver");
		setDriver(targettype.createWebDriverInstance());

		// initialize the driver actions and pass the driver webdriver to the class
		MyLogger.info("initialize the ElementActions and BrowserActions and pass the driver webdriver to the class");
		setElementActions(new ElementActions(getDriver()));
		setBrowserActions(new BrowserActions(getDriver()));

		// maximize the window
		MyLogger.info("maximize the window");
		getDriver().manage().window().maximize();

		// open the URL
		MyLogger.info("open the URL");
		
		MyLogger.startTestCase(scenario);
		
	}
	
	@Given("User is in STC Plan URL")
	public void User_In_STC_Plan_URL() {
		getBrowserActions().openURL(getEnvironment().geturlBase());
	}
	
	@After
	public void teardown(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			MyLogger.error("Test Failed");
			MyLogger.error("Take Screen shot");
			Allure.addAttachment(scenario.getName(),new ByteArrayInputStream(getElementActions().takeScreenShot(scenario.getName(), getDriver())));
		} else {
			MyLogger.info("Test Passed");
		}
		MyLogger.endTestCase(scenario);
		getBrowserActions().closeAllWindows();
	}
}
