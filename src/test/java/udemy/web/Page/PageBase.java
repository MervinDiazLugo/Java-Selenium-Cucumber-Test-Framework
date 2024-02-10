package udemy.web.Page;

import java.util.List;

import config.WebDriverHelper;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.log4testng.Logger;

public class PageBase extends WebDriverHelper {
  /** ****** Log Attribute ******* */
  private static Logger log = Logger.getLogger(PageBase.class);

  private static final int EXPLICIT_TIMEOUT = 20;

}
