package udemy.runners.web;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    tags = "@Orange and not @Ignore",
    features = "src/test/resources/udemy/WebTest/Orange",
    glue = "udemy.StepDefinitions",
    plugin = {
      "pretty",
      "html:test-output",
      "json:target/cucumber/cucumber.json",
      "html:target/cucumber-html-report.html"
    })
public class WebTestOrangeHRMRunner extends AbstractTestNGCucumberTests {
  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}
