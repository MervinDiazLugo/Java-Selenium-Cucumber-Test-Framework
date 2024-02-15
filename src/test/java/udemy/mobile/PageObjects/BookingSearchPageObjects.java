package udemy.mobile.PageObjects;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class BookingSearchPageObjects {
  public static final By CLOSE_SIGN_IN_SCREEN = new MobileBy.ByAccessibilityId("Navigate up");
  public static final By OCCUPANCY_LOC =
      new By.ByXPath(
          "//*[contains(@resource-id, 'com.booking:id/facet_search_box_accommodation_occupancy')]");
  public static final By DESTINATION_LIST_LOC = new By.ByClassName("android.view.ViewGroup");
  public static final By DESTINATION_CITY_TITLE_LOC =
      new By.ByXPath(".//*[contains(@resource-id, 'view_disambiguation_destination_title')]");
  public static final By DESTINATION_CITY_SUB_LOC =
      new By.ByXPath(".//*[contains(@resource-id, 'view_disambiguation_destination_subtitle')]");

  public static final By STEPPER_REMOVE_BUTTON =
      new By.ByXPath(".//*[contains(@resource-id, 'bui_input_stepper_remove_button')]");
  public static final By STEPPER_VALUE_TEXT_BOX =
      new By.ByXPath(".//*[contains(@resource-id, 'bui_input_stepper_value')]");
  public static final By STEPPER_ADD_BUTTON =
      new By.ByXPath(".//*[contains(@resource-id, 'bui_input_stepper_add_button')]");

  public static final By OK_BUTTON = By.id("android:id/button1");
  public static final By APPLY_BUTTON = By.id("com.booking:id/group_config_apply_button");

  public static final By RESULT_LIST =
      By.xpath("//*[contains(@resource-id, 'com.booking:id/bui_review_score_view')]");
}
