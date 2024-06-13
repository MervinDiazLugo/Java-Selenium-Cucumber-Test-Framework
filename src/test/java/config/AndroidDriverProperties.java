package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
@Log
public class AndroidDriverProperties {
  private static Properties prop = new Properties();
  private static final String GLOBAL_DATA_FILE_LOCATION = "/test.properties";
  private String deviceName;
  private String appBase;
  private String deviceGroup;
  private boolean fullReset;
  private boolean noReset;
  private String uuid;
  private String groupId;
  private boolean disableWindowAnimation;
  private Long avdReadyTimeout;
  private boolean ignoreUnimportantViews;
  private boolean clearSystemFiles;
  private boolean unicodeKeyboard;
  private boolean resetKeyboard;
  private Long uiautomator2ServerLaunchTimeout;
  private Long adbExecTimeout;
  private String appPackage;
  private String appActivity;
  private static String ENVIRONMENT,
      SYSTEM_ENVIRONMENT,
      PROPERTIES_ENVIRONMENT,
      TESTNG_ENVIRONMENT,
      SYSTEM_CLIENT,
      PROPERTIES_CLIENT,
      TESTNG_CLIENT,
      CLIENT,
      DEVICE_URL,
      PLATFORM_NAME;
  private static Long IMPLICIT_WAIT;

  public AndroidDriverProperties() {
    initConfig();
  }

  public void initConfig() {
    try {
      InputStream input = ConfigDriver.class.getResourceAsStream(GLOBAL_DATA_FILE_LOCATION);
      prop.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }

    deviceName = prop.getProperty("mobile.driver.android.deviceName");
    deviceGroup = prop.getProperty("mobile.driver.android.deviceGroup");
    fullReset = Boolean.parseBoolean(prop.getProperty("mobile.driver.android.fullReset"));
    noReset = Boolean.parseBoolean(prop.getProperty("mobile.driver.android.noReset"));
    uuid = prop.getProperty("mobile.driver.android.uuid");
    groupId = prop.getProperty("mobile.driver.android.groupId");
    disableWindowAnimation =
        Boolean.parseBoolean(prop.getProperty("mobile.driver.android.disableWindowAnimation"));
    avdReadyTimeout = Long.valueOf(prop.getProperty("mobile.driver.android.avdReadyTimeout"));
    ignoreUnimportantViews =
        Boolean.parseBoolean(prop.getProperty("mobile.driver.android.ignoreUnimportantViews"));
    clearSystemFiles =
        Boolean.parseBoolean(prop.getProperty("mobile.driver.android.clearSystemFiles"));
    unicodeKeyboard =
        Boolean.parseBoolean(prop.getProperty("mobile.driver.android.unicodeKeyboard"));
    resetKeyboard = Boolean.parseBoolean(prop.getProperty("mobile.driver.android.resetKeyboard"));
    uiautomator2ServerLaunchTimeout =
        Long.valueOf(prop.getProperty("mobile.driver.android.uiautomator2ServerLaunchTimeout"));
    adbExecTimeout = Long.valueOf(prop.getProperty("mobile.driver.android.adbExecTimeout"));
    appPackage = prop.getProperty("mobile.driver.android.appPackage");
    appActivity = prop.getProperty("mobile.driver.android.appActivity");
    DEVICE_URL = prop.getProperty("mobile.driver.deviceURL");
    IMPLICIT_WAIT = Long.valueOf(prop.getProperty("mobile.driver.android.implicitlyWait"));
    PLATFORM_NAME = prop.getProperty("mobile.driver.platformName");
    appBase = prop.getProperty("mobile.driver.android.app");

    // system var can be set as $env:mobile_environment='dev' (windows)
    // system var can be set as $env:mobile_client='client1' (windows)

    SYSTEM_ENVIRONMENT = System.getenv("mobile_environment");
    PROPERTIES_ENVIRONMENT = prop.getProperty("mobile.driver.env");

    SYSTEM_CLIENT = System.getenv("mobile_client");
    PROPERTIES_CLIENT = prop.getProperty("mobile.driver.client");
  }

  public String getUuid() {
    return uuid;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public String getApp() {
    return appBase;
  }

  public String getDeviceGroup() {
    return this.deviceGroup;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public boolean getDisableWindowAnimation() {
    return this.disableWindowAnimation;
  }

  public Long getAvdReadyTimeout() {
    return this.avdReadyTimeout;
  }

  public boolean getFullReset() {
    return this.fullReset;
  }

  public boolean getNoReset() {
    return this.noReset;
  }

  public boolean getIgnoreUnimportantViews() {
    return this.ignoreUnimportantViews;
  }

  public boolean getClearSystemFiles() {
    return this.clearSystemFiles;
  }

  public boolean getUnicodeKeyboard() {
    return this.unicodeKeyboard;
  }

  public boolean getResetKeyboard() {
    return this.resetKeyboard;
  }

  public Long getUiautomator2ServerLaunchTimeout() {
    return this.uiautomator2ServerLaunchTimeout;
  }

  public Long getAdbExecTimeout() {
    return this.adbExecTimeout;
  }

  public String getAppPackage() {
    return appPackage;
  }

  public String getAppActivity() {
    return appActivity;
  }

  public static String getDeviceURL() {
    return DEVICE_URL;
  }

  public static Long getImplicitWait() {
    return IMPLICIT_WAIT;
  }

  public static String getPlatformName() {
    return PLATFORM_NAME;
  }

  public String getSystemEnvironment() {
    return SYSTEM_ENVIRONMENT;
  }

  public String getTestNgEnvironment() {
    return TESTNG_ENVIRONMENT;
  }

  public static String setTestNgEnvironment(String value) {
    TESTNG_ENVIRONMENT = value;
    return TESTNG_ENVIRONMENT;
  }

  public String getSystemClient() {
    return SYSTEM_CLIENT;
  }

  public String getTestNgClient() {
    return TESTNG_CLIENT;
  }

  public static String setTestNgClient(String value) {
    TESTNG_CLIENT = value;
    return TESTNG_CLIENT;
  }

  public String getPropertiesEnvironment() {
    return PROPERTIES_ENVIRONMENT;
  }

  public String getPropertiesClient() {
    return PROPERTIES_CLIENT;
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

  public String getAppBase() {
    String appProperty =
        String.format("mobile.driver.%s.android.app.%s", getEnvironment(), getClient());
    appBase = prop.getProperty(appProperty) != null ? prop.getProperty(appProperty) : null;

    if (StringUtils.isEmpty(appBase)) {
      appProperty =
          String.format(
              "mobile.driver.%s.android.app.%s", getPropertiesEnvironment(), getPropertiesClient());
      appBase = prop.getProperty(appProperty) != null ? prop.getProperty(appProperty) : null;
    }
    Assert.assertTrue(StringUtils.isNotEmpty(appBase), "url base malformation");

    return appBase;
  }
}
