package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import lombok.extern.java.Log;
@Log
public class WebDriverFactory extends WebDriverProperties {

  private static final String ZAP_PROXY_HOST = "localhost";
  private static final int ZAP_PROXY_PORT = 8098;

  // Create ZAP proxy by specifying proxy host and proxy port
  private static Proxy createZapProxyConfiguration() {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
    proxy.setSslProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
    return proxy;
  }

  private static Proxy zapProxy = createZapProxyConfiguration();

  private WebDriverFactory() {}

  public static WebDriver createNewDriver(String platform) throws Exception {
    WebDriver driver;

    if (!"CHROME_LOCAL".equalsIgnoreCase(platform)) {
      log.info("Creating session using boni garcia");
    }

    if ("FIREFOX".equalsIgnoreCase(platform)) {
      WebDriverManager.firefoxdriver().clearResolutionCache().forceDownload().setup();
      driver = new FirefoxDriver();
    } else if ("SELENIUM_GRIP".equalsIgnoreCase(platform)) {
      driver = seleniumGrid();
    } else if ("CHROME".equalsIgnoreCase(platform)) {
      WebDriverManager.chromedriver().clearResolutionCache().forceDownload().setup();
      Map<String, Object> prefs = new HashMap<String, Object>();
      ChromeOptions options = new ChromeOptions();
      prefs.put(
          "download.default_directory", getCurrentPath() + "\\src\\test\\resources\\downloads");
      prefs.put("download.prompt_for_download", false);
      options.setExperimentalOption("prefs", prefs);
      driver = new ChromeDriver(options);

    } else if ("CHROME_LOCAL".equalsIgnoreCase(platform)) {
      System.setProperty(
          "webdriver.chrome.driver",
          getCurrentPath() + "\\src\\test\\resources\\bin\\chromedriver\\win64\\chromedriver.exe");
      Map<String, Object> prefs = new HashMap<String, Object>();
      ChromeOptions options = new ChromeOptions();
      prefs.put(
          "download.default_directory", getCurrentPath() + "\\src\\test\\resources\\downloads");
      prefs.put("download.prompt_for_download", false);
      options.setExperimentalOption("prefs", prefs);
      options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
      driver = new ChromeDriver(options);
    } else if (StringUtils.equalsIgnoreCase(platform, "CHROME_LOCAL_BINARIES")) {
      driver = createLocalBinaries();
    } else if ("ZAP".equalsIgnoreCase(platform)) {
      DesiredCapabilities capabilities = DesiredCapabilities.chrome();
      capabilities.setCapability("proxy", zapProxy);
      capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
      System.setProperty(
          "webdriver.chrome.driver",
          getCurrentPath() + "\\src\\test\\resources\\bin\\chromedriver\\win64\\chromedriver.exe");
      Map<String, Object> prefs = new HashMap<String, Object>();
      ChromeOptions options = new ChromeOptions();
      prefs.put(
          "download.default_directory", getCurrentPath() + "\\src\\test\\resources\\downloads");
      prefs.put("download.prompt_for_download", false);
      options.setExperimentalOption("prefs", prefs);
      options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
      options.addArguments("ignore-certificate-errors");
      options.merge(capabilities);
      try {
        driver = new ChromeDriver(options);
      } catch (SessionNotCreatedException e) {
        log.info(e.getMessage());
        log.info("Creating session using boni garcia");
        WebDriverManager.chromedriver().clearResolutionCache().forceDownload().setup();
        driver = new ChromeDriver(options);
      }

    } else if ("ANDROID".equalsIgnoreCase(platform)) {
      driver = getDriverType();

    } else {
      log.info("The Driver is not selected properly, invalid name: " + platform);
      return null;
    }

    return driver;
  }

  public static WebDriver createLocalBinaries() {
    log.info("Creating chrome binaries local session...");
    ChromeOptions options = new ChromeOptions();
    options.setBinary(getCurrentPath() + "\\src\\test\\resources\\bin\\chrome-win64");
    System.setProperty(
        "webdriver.chrome.driver",
        getCurrentPath() + "\\src\\test\\resources\\bin\\chromedriver\\win64\\chromedriver.exe");
    return new ChromeDriver();
  }

  public static WebDriver seleniumGrid() throws MalformedURLException {
    log.info("Creating chrome from selenium grid...");
    ChromeOptions options = new ChromeOptions();
    options.setCapability("platformName", "Windows");
    options.setCapability("browserName", "chrome");
    return new RemoteWebDriver(new URL("http://localhost:4444/"), options);
  }

  public static AppiumDriver<MobileElement> getDriverType() throws Exception {
    AbstractDriverInitializer initializer = new AndroidDriverInitializer();
    return initializer.initialize();
  }
}
