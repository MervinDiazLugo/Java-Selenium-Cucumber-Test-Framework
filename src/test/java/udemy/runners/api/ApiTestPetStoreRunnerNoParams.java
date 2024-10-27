package udemy.runners.api.web;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

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
public class ApiTestPetStoreRunnerNoParams extends AbstractTestNGCucumberTests {}
