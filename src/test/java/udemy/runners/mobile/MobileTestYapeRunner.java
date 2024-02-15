package udemy.runners.mobile;

import config.AndroidDriverProperties;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
    tags = "@MobileYapeBooking and not @Ignore",
    features = "src/test/resources/udemy/MobileTest/Yape",
    glue = "udemy.StepDefinitions",
    plugin = {
      "pretty",
      "summary",
      "html:test-output",
      "json:target/cucumber/cucumber.json",
      "html:target/cucumber-html-report.html"
    })
public class MobileTestYapeRunner extends AbstractTestNGCucumberTests {
  @BeforeTest
  @Parameters({"mobile.driver.env", "mobile.driver.client"})
  public void beforeSuite(@Optional("null") String environment, @Optional("null") String client) {
    System.out.println("TestNG mobile.driver.env for this test set is " + environment);
    System.out.println("TestNG mobile.client for this test set is " + client);
    AndroidDriverProperties.setTestNgEnvironment(environment);
    AndroidDriverProperties.setTestNgClient(client);
  }
}
