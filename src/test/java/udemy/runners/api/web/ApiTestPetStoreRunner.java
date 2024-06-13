package udemy.runners.api.web;

import config.RestAssuredConfigProperties;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.java.Log;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Log
@CucumberOptions(
    tags = "@ApiTesting and not @Ignore",
    features = "src/test/resources/udemy/ApiTest/PetStore",
    glue = "udemy.StepDefinitions",
    plugin = {
      "pretty",
      "summary",
      "html:test-output",
      "json:target/cucumber/cucumber.json",
      "html:target/cucumber-html-report.html"
    })
public class ApiTestPetStoreRunner extends AbstractTestNGCucumberTests {
  @BeforeTest
  @Parameters({"webdriver.env", "webdriver.client"})
  public void beforeSuite(@Optional("null") String environment, @Optional("null") String client) {
    log.info("TestNG webdriver.env for this test set is " + environment);
    log.info("TestNG webdriver.client for this test set is " + client);
    RestAssuredConfigProperties.setTestNgEnvironment(environment);
    RestAssuredConfigProperties.setTestNgClient(client);
  }
}
