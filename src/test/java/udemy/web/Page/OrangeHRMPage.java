package udemy.web.Page;

import config.WebDriverHelper;
import java.util.List;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import udemy.web.PageObjects.OrangeHRMPageObjects;

@Log
public class OrangeHRMPage extends WebDriverHelper {
  OrangeHRMPageObjects orangeHRMPageObjects = new OrangeHRMPageObjects();

  public void login(String user, String pass) {
    fillUsername(user);
    fillPassword(pass);
    clickLoginButton();
  }

  public void loginAdminUser() {
    String user = "Admin";
    String pass = "admin123";

    fillUsername(user);
    fillPassword(pass);
    clickLoginButton();
  }

  public void fillUsername(String user) {
    WebElement userNameElm = getElement(orangeHRMPageObjects.userNameLoc);

    if (StringUtils.isEmpty(user)) {
      throw new SkipException("Variable user is empty");
    }

    if (userNameElm != null) {
      userNameElm.sendKeys(user);
    } else {
      throw new SkipException("Username text box is not present");
    }
  }

  public void fillPassword(String password) {
    WebElement userPasswordElm = getElement(orangeHRMPageObjects.passwordLoc);

    if (StringUtils.isEmpty(password)) {
      throw new SkipException("Variable password is empty");
    }

    if (userPasswordElm != null) {
      userPasswordElm.sendKeys(password);
    } else {
      throw new SkipException("Password text box is not present");
    }
  }

  public void clickLoginButton() {
    WebElement loginButtonElm = getElement(orangeHRMPageObjects.loginBtnLoc);

    if (loginButtonElm != null) {
      loginButtonElm.click();
    } else {
      throw new SkipException("Login Button is not present");
    }
  }

  public WebElement getUserBulletElem(WebDriver driver) {
    return getElement(orangeHRMPageObjects.userBulletLoc);
  }

  public void goToSystemAdminUsers(WebDriver driver) {
    WebElement adminMenuElm = getElement(orangeHRMPageObjects.adminModuleLoc);

    if (adminMenuElm != null) {
      adminMenuElm.click();
    } else {
      throw new SkipException("Admin item at main menu is not present");
    }
  }

  public void getSystemUserList(String userName) {
    List<WebElement> systemUserElm = getElements(orangeHRMPageObjects.adminUsersTableListLoc);
    boolean isPresent = false;

    if (!systemUserElm.isEmpty()) {
      for (WebElement elem : systemUserElm) {
        String[] currentUsernameData = StringUtils.split(elem.getText(), "\n");
        String currentUsername = "";
        if (currentUsernameData.length > 0) {
          currentUsername = currentUsernameData[0];
        } else {
          throw new SkipException("currentUsernameData is empty");
        }
        if (StringUtils.isNotEmpty(currentUsername)
            && StringUtils.equalsIgnoreCase(currentUsername, userName)) {
          isPresent = true;
          break;
        }
      }
    } else {
      throw new SkipException("Admin item at main menu is not present");
    }

    Assert.assertTrue(isPresent, "The username was not present");
  }
}
