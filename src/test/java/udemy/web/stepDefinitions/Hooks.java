package udemy.web.StepDefinitions;

import config.BaseConfigProperties;
import config.ConfigDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.testng.log4testng.Logger;

public class Hooks {
  public static WebDriver driver;
  public static Map<String, String> mainWindowsHandle = new HashMap<>();

  private static BaseConfigProperties properties = new BaseConfigProperties();
  Logger log = Logger.getLogger(Hooks.class);
  Scenario scenario = null;


  public static JSONObject testData = new JSONObject();

  @Before("@WebTesting")
  public void initDriver(Scenario scenario) throws Exception {
    log.info(
        "***********************************************************************************************************");
    log.info("[ Configuration ] - Initializing driver configuration");
    log.info(
        "***********************************************************************************************************");
    driver = ConfigDriver.initConfig();
    mainWindowsHandle.put("main", driver.getWindowHandle());
    this.scenario = scenario;
    log.info(
        "***********************************************************************************************************");
    log.info("[ Scenario ] - " + scenario.getName());
    log.info(
        "***********************************************************************************************************");
  }


  @After("@WebTesting")
  /** Embed a screenshot in test report if test is marked as failed */
  public void embedScreenshot(Scenario scenario) throws IOException {

    if (scenario.isFailed()) {
      try {
        scenario.log("The scenario failed.");
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(
            screenshot, "JPG", properties.getCurrentPath() + "/src/test/resources/screenshots/");
        File destFile =
            new File(
                    properties.getCurrentPath()
                    + "/src/test/resources/screenshots/"
                    + scenario.getId()
                    + scenario.getName()
                    + ".jpg");
        FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destFile);
      } catch (WebDriverException somePlatformsDontSupportScreenshots) {
        System.err.println(somePlatformsDontSupportScreenshots.getMessage());
      }
    }

    log.info(
        "***********************************************************************************************************");
    log.info("[ Driver Status ] - Clean and close the instance of the driver");
    log.info(
        "***********************************************************************************************************");
    driver.quit();
  }

  @After("@WebTesting")
  private void killChromeDriverProcess() {
    log.info("[ Driver Status ] - Clean and close the instance of the driver");
    try {
      Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
      Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
