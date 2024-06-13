package udemy.StepDefinitions;

import config.WebDriverHelper;
import io.cucumber.java.en.*;
import java.util.List;

import lombok.extern.java.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import udemy.web.Page.KlimberAssurancePage;
import udemy.web.Page.OrangeHRMPage;
@Log
public class StepDefinitions extends WebDriverHelper {
  static WebDriver driver;
  private static KlimberAssurancePage klimberAssurance = new KlimberAssurancePage();
  private static OrangeHRMPage orangeHRMPage = new OrangeHRMPage();

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

  @Given("^The User fill username text box$")
  public void theUserFillUsernameTextBox() {
    orangeHRMPage.fillUsername("Admin");
  }

  @And("^The User fill password text box$")
  public void theUserFillPasswordTextBox() {
    orangeHRMPage.fillPassword("admin123");
  }

  @When("^The User clicks Login button$")
  public void theUserClicksLoginButton() {
    orangeHRMPage.clickLoginButton();
  }

  @When("^the user is Logged in$")
  public void theUserIsLoggedIn() {
    orangeHRMPage.login("Admin", "admin123");
  }

  @Then("^Verify the user is logged in$")
  public void verifyTheUserIsLoggedIn() {
    WebElement userBulletElm = orangeHRMPage.getUserBulletElem(driver);
    Assert.assertNotNull(userBulletElm, "El login fue incorrecto");
  }

  @Then("^The user go to System user list$")
  public void theUserGoToSystemUserList() {
    orangeHRMPage.goToSystemAdminUsers(driver);
  }

  @When("^Verify (.*?) user is present in the list$")
  public void verifyAdminUserIsPresentInTheList(String userName) {
    orangeHRMPage.getSystemUserList(userName);
  }

  @When("^the Admin user is Logged in$")
  public void theAdminUserIsLoggedIn() {
    orangeHRMPage.loginAdminUser();
  }
}
