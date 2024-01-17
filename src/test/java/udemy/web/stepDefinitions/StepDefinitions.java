package udemy.web.StepDefinitions;

import config.BaseConfigProperties;
import config.WebDriverHelper;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

public class StepDefinitions extends WebDriverHelper {
  static WebDriver driver;
  private static Logger log = Logger.getLogger(StepDefinitions.class);
  private final BaseConfigProperties properties = new BaseConfigProperties();

  @Given("an example scenario")
  public void anExampleScenario() {
    getMainSite("google");
  }

  @When("all step definitions are implemented")
  public void allStepDefinitionsAreImplemented() {}

  @Then("the scenario passes")
  public void theScenarioPasses() {}
}
