package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

public class BaseConfigProperties {
  private static Properties prop = new Properties();
  private static final String GLOBAL_DATA_FILE_LOCATION = "/test.properties";

  public static String URL_BASE,
      PLATFORM_NAME,
      ENVIRONMENT,
      SYSTEM_ENVIRONMENT,
      PROPERTIES_ENVIRONMENT,
      TESTNG_ENVIRONMENT,
      SYSTEM_CLIENT,
      PROPERTIES_CLIENT,
      TESTNG_CLIENT,
      CLIENT;

  public BaseConfigProperties() {
    initConfig();
  }

  public void initConfig() {
    try {
      InputStream input;
      input = ConfigDriver.class.getResourceAsStream(GLOBAL_DATA_FILE_LOCATION);
      prop.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    PLATFORM_NAME = prop.getProperty("webdriver.platformName");

    // TODO: get environment/client by system, testng and properties
    // system var can be set as $env:webdriver_environment='dev' (windows)
    // system var can be set as $env:webdriver_client='client1' (windows)

    SYSTEM_ENVIRONMENT = System.getenv("webdriver_environment");
    PROPERTIES_ENVIRONMENT = prop.getProperty("webdriver.env");

    SYSTEM_CLIENT = System.getenv("webdriver_client");
    PROPERTIES_CLIENT = prop.getProperty("webdriver.client");
  }

  public String getPlatformName() {
    return PLATFORM_NAME;
  }

  public String getEnvironment() {
    SYSTEM_ENVIRONMENT = getSystemEnvironment();
    TESTNG_ENVIRONMENT = getTestNgEnvironment();
    PROPERTIES_ENVIRONMENT = getPropertiesEnvironment();
    ENVIRONMENT =
        StringUtils.isNotEmpty(SYSTEM_ENVIRONMENT)
            ? SYSTEM_ENVIRONMENT
            : StringUtils.isNotEmpty(TESTNG_ENVIRONMENT)
                ? TESTNG_ENVIRONMENT
                : StringUtils.isNotEmpty(PROPERTIES_ENVIRONMENT) ? PROPERTIES_ENVIRONMENT : null;

    return ENVIRONMENT;
  }

  public String getClient() {
    SYSTEM_CLIENT = getSystemClient();
    TESTNG_CLIENT = getTestNgClient();
    PROPERTIES_CLIENT = getPropertiesClient();
    CLIENT =
        StringUtils.isNotEmpty(SYSTEM_CLIENT)
            ? SYSTEM_CLIENT
            : StringUtils.isNotEmpty(TESTNG_CLIENT)
                ? TESTNG_CLIENT
                : StringUtils.isNotEmpty(PROPERTIES_CLIENT) ? PROPERTIES_CLIENT : null;

    return CLIENT;
  }

  public static String setTestNgEnvironment(String value) {
    TESTNG_ENVIRONMENT = value;
    return TESTNG_ENVIRONMENT;
  }

  public String getTestNgEnvironment() {
    return TESTNG_ENVIRONMENT;
  }

  public String getPropertiesEnvironment() {
    return PROPERTIES_ENVIRONMENT;
  }

  public String getSystemEnvironment() {
    return SYSTEM_ENVIRONMENT;
  }

  public static String setTestNgClient(String value) {
    TESTNG_CLIENT = value;
    return TESTNG_CLIENT;
  }

  public String getTestNgClient() {
    return TESTNG_CLIENT;
  }

  public String getPropertiesClient() {
    return PROPERTIES_CLIENT;
  }

  public String getSystemClient() {
    return SYSTEM_CLIENT;
  }

  public String getUrlBase() {
    String urlProperty = String.format("webdriver.%s.urlBase.%s", getEnvironment(), getClient());
    URL_BASE = prop.getProperty(urlProperty) != null ? prop.getProperty(urlProperty) : null;

    if (StringUtils.isEmpty(URL_BASE)) {
      urlProperty =
          String.format(
              "webdriver.%s.urlBase.%s", getPropertiesEnvironment(), getPropertiesClient());
      URL_BASE = prop.getProperty(urlProperty) != null ? prop.getProperty(urlProperty) : null;
    }
    Assert.assertTrue(StringUtils.isNotEmpty(URL_BASE), "url base malformation");

    return URL_BASE;
  }

  public static String getCurrentPath() {
    return Paths.get("").toAbsolutePath().toString();
  }
}
