package udemy.runners.web;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    tags = "@UdemyWebTest",
    features = "src/test/resources/udemy",
    glue = "udemy.StepDefinitions",
    plugin = {
      "pretty",
      "html:test-output",
      "json:target/cucumber/cucumber.json",
      "html:target/cucumber-html-report.html"
    })
public class WebUdemyCucumberTestRunner extends AbstractTestNGCucumberTests {}
