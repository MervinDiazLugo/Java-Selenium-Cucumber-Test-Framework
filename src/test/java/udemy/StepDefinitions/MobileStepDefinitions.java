package udemy.StepDefinitions;

import config.WebDriverHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;
import udemy.mobile.Page.BookingSearchPage;
import udemy.web.Page.PageBase;

public class MobileStepDefinitions extends WebDriverHelper {
  static WebDriver driver;
  private static Logger log = Logger.getLogger(MobileStepDefinitions.class);
  private static PageBase basePage = new PageBase();
  private static BookingSearchPage bookingSearchPage = new BookingSearchPage();

  public MobileStepDefinitions() {
    driver = Hooks.driver;
  }

  @Given("^User is on Main app screen$")
  public void userIsOnMainAppScreen() {
    bookingSearchPage.skipSignInScreen();
  }

  @Then("^User sets Destination textbox as (.*?)$")
  public void userEntersDestinationValue(String destination) {
    bookingSearchPage.setDestination(destination);
  }

  @And("^User sets travel duration dates From: (.*?) to (.*?)$")
  public void userSetsTravelDurationDatesFromTo(String from, String to) {
    bookingSearchPage.userSetTravelDates(from, to);
  }

  @And("^User sets following Occupancy values$")
  public void userSetsFollowingOccupancyValues(List<List<String>> table) {
    bookingSearchPage.userSetOccupancyValues(table);
  }

  @And("^User sets following Children values$")
  public void userSetsFollowingChildrenValues(List<List<String>> table) {
    bookingSearchPage.userSetChildrenValues(table);
  }

  @Then("^User click on (.*?) button$")
  public void userClickOnSearchButton(String button) {
    click(genericByBuilder(button));
  }

  @Then("^User selects option number (.*?) of result list$")
  public void userSelectResultValue(int index) {
    bookingSearchPage.selectFromResultList(index);
  }
}
