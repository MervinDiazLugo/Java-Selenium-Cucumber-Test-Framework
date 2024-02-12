package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AbstractDriverInitializer {
  public abstract AppiumDriver<MobileElement> initialize() throws Exception;
}
