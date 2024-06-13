package udemy.web.PageObjects;

import lombok.extern.java.Log;
import org.openqa.selenium.By;
@Log
public class KlimberAssurancePageObjects {

  public static final String GENERIC_TEXT_LOC = "//*[contains(text(), '%s')]";
  public static final By SUB_TITLE_LOC = By.cssSelector("div[class='title emergencias-subtitle']");
  public static final By FULL_NAME_LOC = By.cssSelector("input[id='txtNameStep1']");
  public static final By PROVINCE_LOC = By.cssSelector("select[id='ProvinceStep1']");
  public static final By AREA_CODE_LOC = By.cssSelector("input[id='PhoneCodeStep1']");
  public static final By PHONE_NUMBER_LOC = By.cssSelector("input[id='PhoneNumberStep1']");
  public static final By FIRST_STEP_BUTTON_LOC =
      By.cssSelector("button[id='btnSubmitHealthStep1']");

  // SECOND STEP
  public static final By DIV_ADULTS_QUANTITY = By.cssSelector("div[id='handleCounterPersons']");
  public static final By DIV_CHILDREN_QUANTITY = By.cssSelector("div[id='handleCounterChildren']");

  public static final By STEPPER_REMOVE_BUTTON =
      By.xpath(".//button[contains(@class, 'counter-minus')]");
  public static final By STEPPER_ADD_BUTTON =
      By.xpath(".//button[contains(@class, 'counter-plus')]");
  public static final By STEPPER_ADULTS_VALUE_TEXT_BOX = By.xpath(".//input[@id= 'txtPersons']");
  public static final By STEPPER_CHILDREN_VALUE_TEXT_BOX = By.xpath(".//input[@id= 'txtChildren']");
  public static final By SECOND_STEP_BUTTON_LOC =
      By.cssSelector("button[id='btnSubmitHealthStep2']");

  // LAST STEP
  public static final By NAME_LOC = By.cssSelector("input[id='Name']");
  public static final By SURNAME_LOC = By.cssSelector("input[id='Surname']");
  public static final By BIRTHDAY_LOC = By.cssSelector("input[id='Birthday']");
  public static final By ID_NUMBER_LOC = By.cssSelector("input[id='ID_Number']");
  public static final By BIOLOGIC_GENDER_LOC = By.cssSelector("select[id='Gender']");
  public static final By SELF_GENDER_LOC = By.cssSelector("select[id='IdentificationGenderType']");
  public static final By EMAIL_LOC = By.cssSelector("input[id='txtEmail']");
  public static final By PHONE_CODE_LOC = By.cssSelector("input[id='PhoneCode']");
  public static final By PHONE_NUMBER_LAST_LOC = By.cssSelector("input[id='PhoneNumber']");
  public static final By STREET_LOC = By.cssSelector("input[id='Street']");
  public static final By HOUSE_NUMBER_LOC = By.id("HouseNumber");
  public static final By FLOOR_LOC = By.cssSelector("input[id='Floor']");
  public static final By APARTMENT_LOC = By.cssSelector("input[id='Apartment']");
  public static final By ZIP_CODE_LOC = By.cssSelector("input[id='zipCode']");
  public static final By CITY_LOC = By.cssSelector("select[id='city']");
  public static final By REGISTER_BUTTON_LOC = By.cssSelector("button[id='btnRegister']");
}
