package config;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;
import udemy.web.StepDefinitions.Hooks;

public class WebDriverHelper extends WebBaseConfigProperties {
  public static WebDriver driver;

  private static Logger log = Logger.getLogger(WebDriverHelper.class);
  public static JSONObject scenarioData = new JSONObject();
  public static Map<String, String> mainWindowsHandle = new HashMap<>();
  private static final int EXPLICIT_TIMEOUT = 20;
  private static final String FORMAT_DATE = "dd/MM/yyyy";

  private static final int RETRY = 20;

  public WebDriverHelper() {
    driver = Hooks.driver;
    mainWindowsHandle = Hooks.mainWindowsHandle;
    scenarioData = Hooks.testData;
  }

  /** ***** Scenario Attributes ******* */
  Scenario scenario = null;

  public void scenario(Scenario scenario) {
    this.scenario = scenario;
  }

  public void getMainSite() {
    URL_BASE = getUrlBase();
    driver.manage().window().setSize(new Dimension(1920, 1200));
    log.info("Navigate to: " + URL_BASE);
    driver.get(URL_BASE);
    waitPageCompletelyLoaded();
  }

  public void waitPageCompletelyLoaded() {
    String GetActual = driver.getCurrentUrl();
    System.out.println(String.format("Checking if %s page is loaded.", GetActual));
    log.info(String.format("Checking if %s page is loaded.", GetActual));
    new WebDriverWait(driver, EXPLICIT_TIMEOUT)
        .until(
            webDriver ->
                ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
                    .equals("complete"));
  }

  public String addDaysToDate(String startDate, int daysToAdd) {
    Date currentDatePlusDays = new Date();
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

    if (StringUtils.equalsIgnoreCase(startDate, "today")) {
      startDate = new SimpleDateFormat(FORMAT_DATE).format(Calendar.getInstance().getTime());
    }

    Calendar c = Calendar.getInstance();
    try {
      c.setTime(format.parse(startDate));
      c.add(Calendar.DAY_OF_MONTH, daysToAdd);
      currentDatePlusDays = c.getTime();
    } catch (ParseException e) {
      log.info("Error converting dates");
    }
    return format.format(currentDatePlusDays);
  }

  public String addDaysToDateWithFormat(String startDate, int daysToAdd, String formatDate) {
    Date currentDatePlusDays = new Date();
    SimpleDateFormat format = new SimpleDateFormat(formatDate);

    if (StringUtils.equalsIgnoreCase(startDate, "today")) {
      startDate = new SimpleDateFormat(formatDate).format(Calendar.getInstance().getTime());
    }

    Calendar c = Calendar.getInstance();
    try {
      c.setTime(format.parse(startDate));
      c.add(Calendar.DAY_OF_MONTH, daysToAdd);
      currentDatePlusDays = c.getTime();
    } catch (ParseException e) {
      log.info("Error converting dates");
    }
    return format.format(currentDatePlusDays);
  }

  public boolean isOnDateRange(String startDate, String portalDate, String endDate) {
    boolean isBeforeThan = false;
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

    if (StringUtils.isAnyEmpty(startDate, portalDate, endDate)) {
      log.info("One or more input dates are empty");
      return false;
    }

    Calendar rawStartDate = Calendar.getInstance();
    Calendar rawPortalDate = Calendar.getInstance();
    Calendar rawEndDate = Calendar.getInstance();
    try {
      rawStartDate.setTime(format.parse(startDate));
      rawPortalDate.setTime(format.parse(portalDate));
      rawEndDate.setTime(format.parse(endDate));

      isBeforeThan =
          rawStartDate.before(rawPortalDate)
              || rawStartDate.equals(rawPortalDate) && rawPortalDate.before(rawEndDate)
              || rawPortalDate.equals(rawEndDate);

    } catch (ParseException e) {
      log.info("Error converting dates");
    }
    return isBeforeThan;
  }

  public void waitForElementVisible(By element) {
    WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
    log.info("Waiting for the element: " + element + " to be visible");
    w.until(ExpectedConditions.visibilityOfElementLocated(element));
  }

  public boolean isWebElementDisplayed(By element) {
    boolean isDisplayed;
    try {
      log.info(String.format("Waiting Element: %s", element));
      WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
      isDisplayed =
          wait.until(ExpectedConditions.presenceOfElementLocated(element)).isDisplayed()
              && wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
    } catch (NoSuchElementException | TimeoutException e) {
      isDisplayed = false;
      log.info(String.valueOf(e));
    }
    log.info(String.format("%s visibility is: %s", element, isDisplayed));
    return isDisplayed;
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public void webClick(By locator) {
    explicitWait(locator);
    driver.findElement(locator).click();
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public void webSendKeys(By locator, String value) {
    explicitWait(locator);
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(value);
    log.info(locator + " receive the value " + value);
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public void webJsSendKeys(By locator, String value) {
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    WebElement elem = driver.findElement(locator) != null ? driver.findElement(locator) : null;
    // log.info("Scrolling to element: " + SeleniumElement.getText());
    if (elem != null) {
      jse.executeScript(String.format("arguments[0].value='%s';", value), elem);
    }

    explicitWait(locator);
    driver.findElement(locator).clear();
    driver.findElement(locator).sendKeys(value);
    log.info(locator + " receive the value " + value);
  }

  public void closeAlerts(boolean accept) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      if (accept) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      log.info("Alert closed");
    } catch (Exception e) {
      log.info("Couldn't handle alert");
    }
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public String getAttribute(By locator, String attribute) {
    explicitWait(locator);
    String value = driver.findElement(locator).getAttribute(attribute);
    log.info(locator + " return the value " + value);
    return value;
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public String getAttribute(WebElement locator, String attribute) {
    String value = locator.getAttribute(attribute);
    log.info(locator + " return the value " + value);
    return value;
  }

  public void selectOptionDropdownByIndex(By locator, int option) {
    log.info(String.format("Waiting Element: %s", locator));
    Select opt = new Select(driver.findElement(locator));
    log.info("Select option: " + option + " by index");
    opt.selectByIndex(option);
  }

  public void selectOptionDropdownByText(By locator, String option) {
    log.info(String.format("Waiting Element: %s", locator));
    sleep(3);
    explicitWait(locator);
    Select opt = new Select(driver.findElement(locator));
    log.info("Select option: " + option + " by text");
    opt.selectByVisibleText(option);
  }

  public void selectOptionDropdownByValue(By locator, String option) {
    log.info(String.format("Waiting Element: %s", locator));
    Select opt = new Select(driver.findElement(locator));
    log.info("Select option: " + option + " by value");
    opt.selectByValue(option);
  }

  // MOBILE DEVELOPMENT

  /**
   * build ByXPath from text
   *
   * @param text text used as reference
   */
  public By genericByBuilder(String text) {
    String GENERIC =
        String.format(
            "//*[contains(@text,'%s') or contains(@content-desc,'%s') or contains(@resource-id,'%s')]",
            text, text, text);
    return By.xpath(GENERIC);
  }

  /**
   * build ByAccessibilityId from text
   *
   * @param text text used as reference
   */
  public MobileBy.ByAccessibilityId genericAccessibilityIdBuilder(String text) {
    return new MobileBy.ByAccessibilityId(text);
  }

  public boolean waitVisibility(By by, Integer wt) {
    int time;
    if (wt == null) {
      time = EXPLICIT_TIMEOUT;
    } else {
      time = wt;
    }
    try {
      WebDriverWait wait = new WebDriverWait(driver, time);
      wait.until(ExpectedConditions.visibilityOfElementLocated(by));
      // wait.until(ExpectedConditions.elementToBeClickable(by));
      return true;
    } catch (NoSuchElementException | TimeoutException e) {
      log.info("Element is not present");
      return false;
    }
  }

  public boolean waitPresent(By by, Integer wt) {
    int time;
    if (wt == null) {
      time = EXPLICIT_TIMEOUT;
    } else {
      time = wt;
    }
    try {
      WebDriverWait wait = new WebDriverWait(driver, time);
      wait.until(ExpectedConditions.presenceOfElementLocated(by));
      return true;
    } catch (NoSuchElementException | TimeoutException e) {
      log.info("Element is not present");
      return false;
    }
  }

  public WebElement getElementFromGenericBuilders(String label) {
    WebElement elem = null;
    if (driver.findElement(genericByBuilder(label)) != null) {
      elem = driver.findElement(genericByBuilder(label));
    }

    if (elem == null) {
      label = label.split("(?<=[,.])|(?=[,.])|\n|®|:")[0].trim();
      if (driver.findElement(genericByBuilder(label)) != null) {
        elem = driver.findElement(genericByBuilder(label));
      }
    }

    return elem;
  }

  public WebElement getElementFromGenericBuilders(By loc) {
    WebElement elem = null;
    if (driver.findElement(loc) != null) {
      elem = driver.findElement(loc);
    }

    return elem;
  }

  public List<WebElement> getElementsFromGenericBuilders(String label) {
    List<WebElement> elem = new ArrayList<>();
    if (waitVisibility(genericByBuilder(label), EXPLICIT_TIMEOUT)
        && driver.findElements(genericByBuilder(label)) != null) {
      elem = driver.findElements(genericByBuilder(label));
    }

    if (elem.size() == 0) {
      label = label.split("(?<=[,.])|(?=[,.])|\n|®|:")[0].trim();
      if (waitVisibility(genericByBuilder(label), EXPLICIT_TIMEOUT)
          && driver.findElements(genericByBuilder(label)) != null) {
        elem = driver.findElements(genericByBuilder(label));
      }
    }

    if (elem.size() == 0) {
      swipeTo(genericByBuilder(label));
      for (int i = 0; i < 5; i++) {
        swipe();
      }
      elem = driver.findElements(genericByBuilder(label));
    }

    return elem;
  }

  public void scrollToElement(By SeleniumElement) {
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    log.info("Scrolling to element: " + SeleniumElement.toString());
    WebElement elem =
        waitVisibility(SeleniumElement, 20) ? driver.findElement(SeleniumElement) : null;
    if (elem != null) {
      jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(SeleniumElement));
    }
  }

  public void scrollToElement(WebElement SeleniumElement) {
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    WebElement elem = SeleniumElement != null ? SeleniumElement : null;
    log.info("Scrolling to element: " + SeleniumElement.getText());
    if (elem != null) {
      jse.executeScript("arguments[0].scrollIntoView();", SeleniumElement);
    }
  }

  /**
   * Will simulate scrolling down into a object.
   *
   * @param object web element that you do scroll action.
   */
  public void swipeTo(By object) {
    boolean display = waitVisibility(object, EXPLICIT_TIMEOUT / 2);
    int i = 0;
    while (!display && i <= 5) {
      swipe();
      display = isElementDisplayed(object);
      log.info("swipeTo element is " + display + " " + object.toString());
      i++;
    }
  }

  public void swipeHorizontalTo(By object) {
    boolean isDisplayed = isElementDisplayed(object);
    while (!isDisplayed) {
      swipeHorizontal();
      isDisplayed = isElementDisplayed(object);
    }
  }

  /** swipe horizontally */
  public void swipeHorizontal() {
    // Swipe length is a product of the screen's dimensions
    int swipeLength = (int) (driver.manage().window().getSize().getHeight() * 0.50);
    int anchorX =
        (int)
            (driver.manage().window().getSize().getHeight()
                * 0.90); // x coordinate will not change for this vertical swipe
    int endY =
        (int)
            (driver.manage().window().getSize().getHeight()
                * 0.3); // Higher on the screen (lower y coordinate)
    int startY = endY + swipeLength; // Lower on the screen (higher y coordinate)
    new TouchAction((PerformsTouchActions) driver)
        .longPress(new PointOption().withCoordinates(anchorX, startY))
        .moveTo(new PointOption().withCoordinates(anchorX, endY))
        .release()
        .perform();
  }

  /**
   * Performs a swipe using the appium driver. Swipes from the start coordinates to the end
   * coordinates.
   */
  public void swipe() {
    // Swipe length is a product of the screen's dimensions
    int swipeLength = (int) (driver.manage().window().getSize().getHeight() * 0.4);
    int anchorX =
        driver.manage().window().getSize().getWidth()
            / 2; // x coordinate will not change for this vertical swipe
    int endY =
        (int)
            (driver.manage().window().getSize().getHeight()
                * 0.3); // Higher on the screen (lower y coordinate)
    int startY = endY + swipeLength; // Lower on the screen (higher y coordinate)
    new TouchAction((PerformsTouchActions) driver)
        .longPress(new PointOption().withCoordinates(anchorX, startY))
        .moveTo(new PointOption().withCoordinates(anchorX, endY))
        .release()
        .perform();
  }

  /**
   * tap near from a element
   *
   * @param element element used as reference
   */
  public void tapFixed(By element, int fixY) {
    int x = driver.manage().window().getSize().getWidth() / 2;
    int y = driver.findElement(element).getLocation().getY() + fixY;
    log.info(String.format("retrieve element coordinates: %s, %s", x, y));
    tapByCoordinates(x, y);
  }

  /** Will simulate tapping by coordinates. */
  public void tapByCoordinates(int x, int y) {
    try {
      new TouchAction((PerformsTouchActions) driver)
          .longPress(PointOption.point(x, y))
          .moveTo(new PointOption().withCoordinates(x, y))
          .release()
          .perform();
    } catch (InvalidArgumentException e) {
      log.info("The tap were send beyond windows size");
    }
  }

  /**
   * wait an element appeared and return isDisplayed boolean value
   *
   * @param object web element that you are waiting for.
   */
  public boolean isElementDisplayed(By object) {
    boolean isDisplayed;
    try {
      WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
      isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(object)).isDisplayed();
    } catch (NoSuchElementException | TimeoutException e) {
      isDisplayed = false;
    }
    log.info(String.format("visibility is: %s", isDisplayed));
    return isDisplayed;
  }

  public void fillField(By locator, String text) {
    explicitWait(locator);
    MobileElement element = (MobileElement) driver.findElement(locator);
    element.click();
    implicitWait(1);
    element.sendKeys(text);
  }

  /**
   * click using generic xpath
   *
   * @param locator text used as reference
   */
  public void mobileClick(By locator) {
    explicitWait(locator);
    MobileElement element = (MobileElement) driver.findElement(locator);
    element.click();
  }

  public List<WebElement> getElementList(By locator) {
    List<WebElement> elem = new ArrayList<>();

    if (waitVisibility(locator, 10)) {
      elem = driver.findElements(locator);
    }

    if (elem.size() == 0) {
      swipeTo(locator);
      for (int i = 0; i < 5; i++) {
        swipe();
      }
      elem = driver.findElements(locator);
    }

    return elem;
  }

  /**
   * click using generic xpath
   *
   * @param text text used as reference
   */
  public void click(String text) {
    explicitWait(genericByBuilder(text));
    driver.findElement(genericByBuilder(text)).click();
  }

  /**
   * click using javascript
   *
   * @param element element used as reference
   */
  public void jsClick(WebElement element) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript("arguments[0].click();", element);
  }

  /**
   * click using javascript
   *
   * @param element element used as reference
   */
  public void jsClick(By element) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript("arguments[0].click();", driver.findElement(element));
  }

  /**
   * click using javascript
   *
   * @param element element used as reference
   */
  public void actionClick(By element) {
    if (waitVisibility(element, EXPLICIT_TIMEOUT)) {
      WebElement elm = driver.findElement(element);
      if (elm != null) {
        new Actions(driver).moveToElement(elm).click().perform();
      } else {
        log.debug("ActionClick WebElement is null");
      }
    } else {
      log.debug(element + " is not visible");
    }
  }

  /**
   * click using javascript
   *
   * @param element element used as reference
   */
  public void setAttribute(WebElement element, String key, String value) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    // executor.executeScript("arguments[0].setAttribute('class', 'multiselect-item dropdown-item
    // form-check active');", element);
    executor.executeScript(
        String.format("arguments[0].setAttribute('%s', '%s');", key, value), element);
  }

  /**
   * click using javascript
   *
   * @param element element used as reference
   */
  public void setAttribute(By element, String key, String value) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    if (waitVisibility(element, EXPLICIT_TIMEOUT)) {
      WebElement elm = driver.findElement(element);
      if (elm != null) {
        executor.executeScript(
            String.format("arguments[0].setAttribute('%s', '%s');", key, value), elm);
      } else {
        log.debug("setAttribute: WebElement is null");
      }
    } else {
      log.debug(element + " is not visible");
    }
  }

  /**
   * click using generic xpath
   *
   * @param loc locator used as reference
   */
  public void click(By loc) {
    explicitWait(loc);
    driver.findElement(loc).click();
  }

  public void explicitWait(By by) {
    WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public void implicitWait(int seconds) {
    try {
      driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    } catch (Exception e) {
      log.debug("Error in implicitlyWait  ", e);
    }
  }

  public void sleep(int seconds) {
    try {
      Thread.sleep(1000 * seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void explicitWait(By by, int timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public void setSecureTextValue(By element, String value) {
    String fields_text;
    boolean verify;
    int i = 0;
    do {
      sleep(1);
      explicitWait(element);
      fillField(element, value);
      sleep(2);
      String parts = driver.findElement(element).getText().trim();
      int end = parts.indexOf(",");
      if (end != -1) {
        fields_text = parts.substring(0, end);
      } else {
        fields_text = driver.findElement(element).getText().trim();
      }
      verify = fields_text.equals(value);
      if (verify) {
        return;
      }
      i++;
    } while (!verify && i <= RETRY);
  }

  /**
   * Create a table with parameters given on feature step.
   *
   * @param table is a list with parameters given on step.
   */
  public DataTable createDataTable(List<List<String>> table) {
    DataTable data;
    data = DataTable.create(table);
    log.info(data.toString());
    return data;
  }

  public void saveInScenarioData(String key, String value) {
    if (StringUtils.isNotEmpty(value)) {
      if (scenarioData.containsKey(key)) {
        scenarioData.replace(key, value);
      } else {
        scenarioData.put(key, value);
      }
      log.info("Scenario data updated: " + scenarioData.toString());
    } else {
      log.info("Scenario data: value was empty");
    }
  }

  public String getScenarioData(String key) {
    String value = null;
    if (scenarioData.containsKey(key)) {
      value = scenarioData.get(key).toString();
    } else {
      log.info("Scenario data: key didn't exits");
    }
    return value;
  }

  public void runBinary(String path) {
    String buildPath = getCurrentPath() + "/src/test/resources/data/bin/" + path;
    try {
      Runtime.getRuntime().exec(buildPath);
    } catch (IOException | RuntimeException e) {
      throw new RuntimeException(e);
    }
  }

  public void windowsHandle(String windowsName) {
    boolean alreadyExist;
    sleep(10);
    if (this.mainWindowsHandle.containsKey(windowsName)) {
      driver.switchTo().window(this.mainWindowsHandle.get(windowsName));
      log.info(
          String.format(
              "I go to Windows: %s with value: %s ",
              windowsName, this.mainWindowsHandle.get(windowsName)));
    } else {
      for (String winHandle : driver.getWindowHandles()) {
        for (String entry : this.mainWindowsHandle.keySet()) {
          String value = this.mainWindowsHandle.get(entry.trim());
          alreadyExist = StringUtils.equalsIgnoreCase(value, winHandle);
          if (!alreadyExist) {
            this.mainWindowsHandle.put(windowsName, winHandle);
            log.info(
                "The New window"
                    + windowsName
                    + "is saved in scenario with value"
                    + this.mainWindowsHandle.get(windowsName));
            driver.switchTo().window(winHandle);
            break;
          }
        }
      }
    }
  }

  public String createRandomNumber() {
    long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
    return String.valueOf(randomNumber);
  }

  public String decimalFormat(String number, String pattern) {
    String value;
    double amount = Double.parseDouble(number);
    DecimalFormat formatter = new DecimalFormat(pattern);
    value = formatter.format(amount);
    return value;
  }

  public double decimalDoubleFormat(String number) throws ParseException {
    NumberFormat nf = NumberFormat.getInstance();
    double value = nf.parse(number).doubleValue();
    return value;
  }

  public void takeScreenShot() throws IOException {
    log.info("Saving screen shot");
    File destFile =
        new File(
            getCurrentPath() + "/src/test/resources/screenshots/" + UUID.randomUUID() + ".png");
    FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), destFile);
  }

  private int getMod(int t) {
    int e = 0;
    int n = 1;

    while (t > 0) {
      n = (n + t % 10 * (9 - e++ % 6)) % 11;
      t = t / 10;
    }

    if (n > 0) {
      return n - 1;
    } else {
      return 10; // return 10 when n is 0
    }
  }

  public String generateRut() {
    int randomNumber = getRandomNumber();
    int mod = getMod(randomNumber);
    return randomNumber + String.valueOf(mod);
  }

  private int getRandomNumber() {
    int minValue = 1000000;
    int maxValue = 40000000;
    return new Random().nextInt(maxValue - minValue + 1) + minValue;
  }
}
