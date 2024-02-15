package udemy.mobile.Page;

import static udemy.mobile.PageObjects.BookingSearchPageObjects.*;

import config.WebDriverHelper;
import io.cucumber.datatable.DataTable;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.log4testng.Logger;
import udemy.mobile.PageObjects.BookingSearchPageObjects;

public class BookingSearchPage extends WebDriverHelper {
  /** ****** Log Attribute ******* */
  private static Logger log = Logger.getLogger(BookingSearchPage.class);

  BookingSearchPageObjects bookingSearchPageObjects = new BookingSearchPageObjects();
  private static final int EXPLICIT_TIMEOUT = 20;

  public void setDestination(String destination) {
    click(genericByBuilder("Enter your destination"));
    fillField(genericByBuilder("Enter destination"), destination);
    List<WebElement> locationResultList =
        getElementList(bookingSearchPageObjects.DESTINATION_LIST_LOC);

    if (!locationResultList.isEmpty()) {
      for (WebElement elem : locationResultList) {
        String cityDestination =
            elem.findElement(bookingSearchPageObjects.DESTINATION_CITY_TITLE_LOC).getText();
        String areaDestination =
            elem.findElement(bookingSearchPageObjects.DESTINATION_CITY_SUB_LOC).getText();

        if (StringUtils.equalsIgnoreCase(cityDestination, destination)
            && StringUtils.equalsIgnoreCase(areaDestination, "City in Cusco, Peru")) {
          elem.click();
          break;
        }
      }

    } else {
      throw new SkipException("Results list didn't retrieve any result");
    }
  }

  public void skipSignInScreen() {
    By closeSignScreen = genericAccessibilityIdBuilder("Navigate up");

    if (waitVisibility(closeSignScreen, EXPLICIT_TIMEOUT)) {
      click(closeSignScreen);
    }
    boolean isVisible =
        waitVisibility(genericByBuilder("Enter your destination"), 5)
            || waitVisibility(genericByBuilder("Stays"), 5);

    Assert.assertTrue(isVisible, "Cannot reach Main app Screen");
  }

  public void userSetTravelDates(String from, String to) {
    By fromLoc = genericByBuilder(from);
    By toLoc = genericByBuilder(to);
    By selectDateBtnLoc = genericByBuilder("Select dates");

    // Select from date
    swipeTo(fromLoc);
    click(fromLoc);
    // Select to date
    swipeTo(toLoc);
    click(toLoc);

    boolean isVisible = waitVisibility(selectDateBtnLoc, 5);

    Assert.assertTrue(isVisible, "Select dates button is unable");
    click(selectDateBtnLoc);
  }

  public void userSetOccupancyValues(List<List<String>> table) {
    // click on Occupancy text box
    click(genericByBuilder("adults"));
    DataTable data = createDataTable(table);
    if (data != null) {
      // AtomicInteger i = new AtomicInteger(1);
      data.cells()
          .forEach(
              value -> {
                String KEY = "";
                String VALUES = "";
                try {
                  List<String> rField = Collections.singletonList(value.get(0));
                  List<String> rValue = Collections.singletonList(value.get(1));
                  KEY = rField.get(0);
                  VALUES = rValue.get(0);

                } catch (NullPointerException e) {
                  log.info(String.format("Path specified on table doesn't exist: %s", KEY));
                  throw new SkipException(
                      String.format("Path specified on table doesn't exist: %s", KEY));
                }
                // the logic begin here
                sleep(2);
                if (waitVisibility(genericByBuilder("Select rooms and guests"), 5)) {

                  List<WebElement> removeElems = null;
                  List<WebElement> addElems = null;
                  List<WebElement> valueElems = null;
                  WebElement removeElem = null;
                  WebElement addElem = null;
                  WebElement valueElem = null;

                  removeElems = driver.findElements(STEPPER_REMOVE_BUTTON);
                  addElems = driver.findElements(STEPPER_ADD_BUTTON);
                  valueElems = driver.findElements(STEPPER_VALUE_TEXT_BOX);

                  if (removeElems.isEmpty() && addElems.isEmpty() && valueElems.isEmpty()) {
                    Assert.fail("Select room elements are not present");
                  }
                  // SET VALUES
                  if (StringUtils.equalsIgnoreCase("room", KEY)) {
                    setInputValues(1, KEY, VALUES);
                  } else if (StringUtils.equalsIgnoreCase("adults", KEY)) {
                    setInputValues(2, KEY, VALUES);
                  } else if (StringUtils.equalsIgnoreCase("children", KEY)) {

                  }
                } else {
                  Assert.fail("Select rooms and guests");
                }
              });
    }
    click(APPLY_BUTTON);
  }

  private void setInputValues(int index, String key, String value) {
    List<WebElement> removeElems;
    List<WebElement> addElems;
    List<WebElement> valueElems;
    WebElement removeElem;
    WebElement addElem;
    WebElement valueElem;

    removeElems = driver.findElements(STEPPER_REMOVE_BUTTON);
    addElems = driver.findElements(STEPPER_ADD_BUTTON);
    valueElems = driver.findElements(STEPPER_VALUE_TEXT_BOX);

    if (removeElems.isEmpty() && addElems.isEmpty() && valueElems.isEmpty()) {
      Assert.fail(String.format("Select %s elements are not present", key));
    }

    // SET VALUES
    removeElem = removeElems.size() >= index ? removeElems.get(index - 1) : null;
    addElem = addElems.size() >= index ? addElems.get(index - 1) : null;
    valueElem = valueElems.size() >= index ? valueElems.get(index - 1) : null;

    // GET TEXT VALUE
    String ElemTextVal = "";
    ElemTextVal =
        StringUtils.isNotEmpty(valueElem.getAttribute("text"))
            ? valueElem.getAttribute("text")
            : "";

    boolean areElementsPresent = removeElem != null && addElem != null && valueElem != null;
    if (areElementsPresent) {
      // VALIDATE ALL ELEMENTS BEFORE CONTINUE
      if (StringUtils.isNotEmpty(ElemTextVal)
          && Integer.parseInt(ElemTextVal) > Integer.parseInt(value)) {
        int clicksQuantity = Integer.parseInt(ElemTextVal) - Integer.parseInt(value);
        for (int i = 0; i < clicksQuantity; ++i) {
          sleep(1);
          removeElem.click();
        }

      } else if (StringUtils.isNotEmpty(ElemTextVal)
          && Integer.parseInt(ElemTextVal) < Integer.parseInt(value)) {
        int clicksQuantity = Integer.parseInt(value) - Integer.parseInt(ElemTextVal);
        for (int i = 0; i < clicksQuantity; ++i) {
          sleep(1);
          addElem.click();
        }
      } else {
        log.info(key + ": already contain the input value");
      }
    } else {
      Assert.assertTrue(false, "Select room elements is not present");
    }
  }

  public void setChildrenValues(String age) {
    List<WebElement> removeElems;
    List<WebElement> addElems;
    List<WebElement> valueElems;
    WebElement removeElem;
    WebElement addElem;
    WebElement valueElem;

    removeElems = driver.findElements(STEPPER_REMOVE_BUTTON);
    addElems = driver.findElements(STEPPER_ADD_BUTTON);
    valueElems = driver.findElements(STEPPER_VALUE_TEXT_BOX);

    if (removeElems.isEmpty() && addElems.isEmpty() && valueElems.isEmpty()) {
      Assert.fail("Select children elements are not present");
    }

    // SET VALUES
    int index = 2;
    removeElem = removeElems.size() >= index ? removeElems.get(index) : null;
    addElem = addElems.size() >= index ? addElems.get(index) : null;
    valueElem = valueElems.size() >= index ? valueElems.get(index) : null;
    By selectedValueLoc = By.id("android:id/numberpicker_input");

    boolean areElementsPresent = removeElem != null && addElem != null && valueElem != null;
    if (areElementsPresent) {
      sleep(1);
      addElem.click();
      waitVisibility(selectedValueLoc, 5);
      int i = 0;
      boolean display = waitVisibility(genericByBuilder(age), 5);
      while (!display && i <= 20) {
        tapFixed(selectedValueLoc, 320);
        if (display) {
          click(genericByBuilder(age));
          break;
        }
        display = isElementDisplayed(genericByBuilder(age));
        i++;
      }
      click(OK_BUTTON);
      click(APPLY_BUTTON);
    } else {
      Assert.fail("Select Children elements are not present");
    }
  }

  public void userSetChildrenValues(List<List<String>> table) {
    DataTable data = createDataTable(table);
    if (data != null) {
      // AtomicInteger i = new AtomicInteger(1);
      data.cells()
          .forEach(
              value -> {
                String AGE = "";
                try {
                  List<String> rAge = Collections.singletonList(value.get(0));
                  AGE = rAge.get(0);

                } catch (NullPointerException e) {
                  log.info(String.format("Path specified on table doesn't exist: %s", AGE));
                  throw new SkipException(
                      String.format("Path specified on table doesn't exist: %s", AGE));
                }
                By childrenTxtLoc = genericByBuilder("children");
                click(childrenTxtLoc);
                setChildrenValues(AGE);
              });
    }
  }

  public void selectFromResultList(int index) {
    swipeTo(bookingSearchPageObjects.RESULT_LIST);
    List<WebElement> locationResultList = getElementList(bookingSearchPageObjects.RESULT_LIST);

    if (!locationResultList.isEmpty()) {
      WebElement item =
          locationResultList.size() >= index && locationResultList.get(index) != null
              ? locationResultList.get(index)
              : locationResultList.get(0);
      if (item != null) {
        item.click();
      } else {
        Assert.fail("Cannot click on the result list");
      }
    } else {
      throw new SkipException("Results list didn't retrieve any result");
    }
  }
}
