package config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseConfigProperties {
  private static Properties prop = new Properties();
  private static final String GLOBAL_DATA_FILE_LOCATION = "/test.properties";

  private String platformName;

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
    platformName = prop.getProperty("webdriver.platformName");
  }

  public String getPlatformName() {
    return platformName;
  }

  public static String getCurrentPath() {
    return Paths.get("").toAbsolutePath().toString();
  }
}
