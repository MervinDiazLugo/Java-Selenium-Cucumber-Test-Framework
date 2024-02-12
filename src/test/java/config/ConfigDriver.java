package config;

import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

public class ConfigDriver {
  /** ***** Load Properties ******* */
  private static WebBaseConfigProperties baseConfigProperties = new WebBaseConfigProperties();

  private static AndroidDriverProperties androidConfigProperties = new AndroidDriverProperties();

  /** ***** Log Attribute ******* */
  private static Logger log = Logger.getLogger(ConfigDriver.class);

  public static WebDriver initWebConfig() throws Exception {
    WebDriver driver;
    String platform = baseConfigProperties.getPlatformName();

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
