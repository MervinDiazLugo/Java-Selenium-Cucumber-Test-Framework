package udemy.web.Page;

import config.WebDriverHelper;
import org.testng.log4testng.Logger;

public class PageBase extends WebDriverHelper {
  /** ****** Log Attribute ******* */
  private static Logger log = Logger.getLogger(PageBase.class);

  private static final int EXPLICIT_TIMEOUT = 20;
}
