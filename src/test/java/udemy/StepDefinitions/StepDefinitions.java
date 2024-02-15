package udemy.StepDefinitions;

import config.WebDriverHelper;
import io.cucumber.java.en.*;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;
import udemy.mobile.Page.BookingSearchPage;
import udemy.web.Page.KlimberAssurancePage;
import udemy.web.Page.PageBase;

public class StepDefinitions extends WebDriverHelper {
  static WebDriver driver;
  private static Logger log = Logger.getLogger(StepDefinitions.class);
  private static PageBase basePage = new PageBase();
  private static KlimberAssurancePage klimberAssurance = new KlimberAssurancePage();
  private static BookingSearchPage bookingSearchPage = new BookingSearchPage();

  @Given("an example scenario")
  public void anExampleScenario() {
    getMainSite();
  }

  @When("all step definitions are implemented")
  public void allStepDefinitionsAreImplemented() {}

  @Then("the scenario passes")
  public void theScenarioPasses() {}

  public StepDefinitions() {
    driver = Hooks.driver;
  }

  @Given("^I am in app main site$")
  public void iAmInAppMainSite() {
    driver.manage().window().maximize();
    URL_BASE = getUrlBase();
    log.info("Navigating to: " + URL_BASE);
    driver.get(URL_BASE);
    mainWindowsHandle.put("main", driver.getWindowHandle());
    waitPageCompletelyLoaded();
  }

  @And("^I wait for first step elements are loaded$")
  public void iWaitForTitleVisible() {
    klimberAssurance.waitForFirstStepElements();
  }

  @Then("^I fill following text boxes:$")
  public void iSetFollowingTextBoxes(List<List<String>> table) {
    klimberAssurance.setFirstStepTextBoxes(table);
  }

  @And("^click on (.*?) button$")
  public void clickOnButton(String tag) {
    klimberAssurance.clickOnButtons(tag);
  }

  @And("^I wait for second step elements are loaded$")
  public void iWaitForSecondStepElementsAreLoaded() {
    klimberAssurance.waitForSecondStepElements();
  }

  @And("^I wait for last step elements are loaded$")
  public void iWaitForLastStepElementsAreLoaded() {
    klimberAssurance.waitForLastStepElements();
  }

  @Then("I fill registration form with values:")
  public void iFillRegistrationFormWithValues(List<List<String>> table) {
    klimberAssurance.setLastStepTextBoxes(table);
  }
}
