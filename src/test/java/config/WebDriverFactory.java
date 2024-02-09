package config;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

public class WebDriverFactory extends BaseConfigProperties {
    /**
     * ***** Log Attribute *******
     */
    private static Logger log = Logger.getLogger(WebDriverFactory.class);

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

    private WebDriverFactory() {
    }

    public static WebDriver createNewWebDriver(String platform) throws Exception {
        WebDriver driver;

        if (!"CHROME_LOCAL".equalsIgnoreCase(platform)) {
            log.info("Creating session using boni garcia");
        }

        if ("FIREFOX".equalsIgnoreCase(platform)) {
            WebDriverManager.firefoxdriver().clearResolutionCache().forceDownload().setup();
            driver = new FirefoxDriver();

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
                    getCurrentPath() + "\\src\\test\\resources\\bin\\windows32\\chromedriver.exe");
            Map<String, Object> prefs = new HashMap<String, Object>();
            ChromeOptions options = new ChromeOptions();
            prefs.put(
                    "download.default_directory", getCurrentPath() + "\\src\\test\\resources\\downloads");
            prefs.put("download.prompt_for_download", false);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else if ("ZAP".equalsIgnoreCase(platform)) {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("proxy", zapProxy);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            System.setProperty(
                    "webdriver.chrome.driver",
                    getCurrentPath() + "\\src\\test\\resources\\binaries\\windows32\\chromedriver.exe");
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

        } else {
            log.debug("The Driver is not selected properly, invalid name: " + platform);
            return null;
        }

        return driver;
    }
}
