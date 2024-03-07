package udemy.test;

import config.ConfigDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrowserTest {
  public static WebDriver driver;

  @BeforeTest
  public void setUp() throws Exception {
    driver = ConfigDriver.initWebConfig();
  }

  @Test
  public void test() {
    driver.get("https://www.james-willett.com/gatling-load-testing-complete-guide/");
    driver.getTitle();
  }

  @AfterTest
  public void tearDown() {
    driver.quit();
  }
}
