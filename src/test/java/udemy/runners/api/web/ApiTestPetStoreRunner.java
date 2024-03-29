package udemy.runners.api.web;

import config.RestAssuredConfigProperties;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

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
    System.out.println("TestNG webdriver.env for this test set is " + environment);
    System.out.println("TestNG webdriver.client for this test set is " + client);
    RestAssuredConfigProperties.setTestNgEnvironment(environment);
    RestAssuredConfigProperties.setTestNgClient(client);
  }
}
