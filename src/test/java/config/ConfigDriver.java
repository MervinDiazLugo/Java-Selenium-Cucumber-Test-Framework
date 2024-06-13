package config;

import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

@Log
public class ConfigDriver {
  /** ***** Load Properties ******* */
  private static WebDriverProperties baseConfigProperties = new WebDriverProperties();

  private static AndroidDriverProperties androidConfigProperties = new AndroidDriverProperties();

  public static WebDriver initWebConfig() throws Exception {
    WebDriver driver;
    String platform = baseConfigProperties.getPlatformName();

    log.info("*******************************************************************************************************");
    log.info("[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
    log.info("[ POM Configuration ] - Browser: " + platform);
    log.info("******************************************************************************************************");

    driver = WebDriverFactory.createNewDriver(platform);

    return driver;
  }

  public static WebDriver initMobileConfig() throws Exception {
    WebDriver driver;
    String platform = androidConfigProperties.getPlatformName();

    log.info(
        "***********************************************************************************************************");
    log.info(
        "[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
    /** ****** POM Information ******* */
    log.info("[ POM Configuration ] - Browser: " + platform);
    log.info(
        "***********************************************************************************************************");

    /** **** Load the driver ****** */
    driver = WebDriverFactory.createNewDriver(platform);

    return driver;
  }
}
