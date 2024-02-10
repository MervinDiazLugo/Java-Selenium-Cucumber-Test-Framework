package udemy.web.Page;

import config.WebDriverHelper;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.log4testng.Logger;
import udemy.web.PageObjects.KlimberAssurancePageObjects;

import java.util.Collections;
import java.util.List;

import static udemy.web.PageObjects.KlimberAssurancePageObjects.*;


public class KlimberAssurancePage extends WebDriverHelper {
    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(KlimberAssurancePage.class);
    KlimberAssurancePageObjects klimberAssurancePageObjects = new KlimberAssurancePageObjects();
    private static final int EXPLICIT_TIMEOUT = 20;

    public void waitForFirstStepElements(){
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Conocé el precio de tu plan"));
        Assert.assertTrue(isWebElementDisplayed(titleLoc), "title element was not visible on page");

        Assert.assertTrue(isWebElementDisplayed(SUB_TITLE_LOC), "Sub title element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(FULL_NAME_LOC), "Full name element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(PROVINCE_LOC), "Select Province element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(AREA_CODE_LOC), "Area code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(PHONE_NUMBER_LOC), "Phone number element was not visible on page");
    }

    public void waitForSecondStepElements(){
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Elegí el plan que más te conviene"));
        Assert.assertTrue(isWebElementDisplayed(titleLoc), "title element was not visible on page");

        Assert.assertTrue(isWebElementDisplayed(DIV_ADULTS_QUANTITY), "Adults quantity element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(DIV_CHILDREN_QUANTITY), "Children quantity element was not visible on page");
    }

    public void waitForLastStepElements(){
        By titleLoc = By.xpath(String.format(GENERIC_TEXT_LOC, "Completá tus datos"));
        Assert.assertTrue(isWebElementDisplayed(titleLoc), "title element was not visible on page");

        Assert.assertTrue(isWebElementDisplayed(NAME_LOC), "Name element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(SURNAME_LOC), "Surname element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(BIRTHDAY_LOC), "Birthday element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(ID_NUMBER_LOC), "DNI element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(BIOLOGIC_GENDER_LOC), "Biologic gender element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(SELF_GENDER_LOC), "Gender element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(EMAIL_LOC), "Email element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(PHONE_CODE_LOC), "Phone code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(PHONE_NUMBER_LAST_LOC), "Phone number element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(STREET_LOC), "Street element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(HOUSE_NUMBER_LOC), "House Number element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(FLOOR_LOC), "Floor element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(APARTMENT_LOC), "Apartment element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(ZIP_CODE_LOC), "Zip code element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(CITY_LOC), "City element was not visible on page");
        Assert.assertTrue(isWebElementDisplayed(REGISTER_BUTTON_LOC), "Register button element was not visible on page");

    }

    public void setFirstStepTextBoxes(List<List<String>> table){
        DataTable data = createDataTable(table);
        if (data != null) {
            //AtomicInteger i = new AtomicInteger(1);
            data.cells().forEach(value -> {
                String KEY = "";
                String VALUE = "";
                try {
                    List<String> rField = Collections.singletonList(value.get(0));
                    List<String> rValue = Collections.singletonList(value.get(1));
                    KEY = rField.get(0);
                    VALUE = rValue.get(0);

                } catch (NullPointerException e) {
                    log.info(String.format("Path specified on table doesn't exist: %s", KEY));
                    throw new SkipException(String.format("Path specified on table doesn't exist: %s", KEY));
                }

                if (StringUtils.containsIgnoreCase(KEY, "Nombre")) {
                    webSendKeys(FULL_NAME_LOC, VALUE);

                } else if (StringUtils.containsIgnoreCase(KEY, "Cód. área")) {
                    webSendKeys(AREA_CODE_LOC, VALUE);

                } else if (StringUtils.containsIgnoreCase(KEY, "Celular")) {
                    webSendKeys(PHONE_NUMBER_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Provincia")) {
                    selectOptionDropdownByText(PROVINCE_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Cantidad de adultos")) {
                    setInputValues(DIV_ADULTS_QUANTITY, KEY, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Menores de 12 años")) {
                    setInputValues(DIV_CHILDREN_QUANTITY, KEY, VALUE);
                }

            });

        }
    }

    public void clickOnButtons(String tag){
        if (StringUtils.containsIgnoreCase(tag, "Cotizá")) {
            webClick(FIRST_STEP_BUTTON_LOC);
            waitPageCompletelyLoaded();
            log.info("waiting second step loaded");
        }
        if (StringUtils.containsIgnoreCase(tag, "Siguiente")) {
            webClick(SECOND_STEP_BUTTON_LOC);
            waitPageCompletelyLoaded();
            log.info("waiting second step loaded");
        }
    }

    public void setInputValues(By locator, String key, String valueToBeSet){
        if(isWebElementDisplayed(locator)){
            WebElement divElements = driver.findElement(locator);
            WebElement removeElem = divElements.findElement(STEPPER_REMOVE_BUTTON);
            WebElement addElem = divElements.findElement(STEPPER_ADD_BUTTON);
            WebElement valueElem = null;

            if (StringUtils.containsIgnoreCase(key, "Cantidad de adultos")) {
                valueElem = divElements.findElement(STEPPER_ADULTS_VALUE_TEXT_BOX);
            }else if (StringUtils.containsIgnoreCase(key, "Menores de 12 años")) {
                valueElem = divElements.findElement(STEPPER_CHILDREN_VALUE_TEXT_BOX);
            }

            if(!removeElem.isDisplayed() && !addElem.isDisplayed() && !valueElem.isDisplayed()){
                Assert.fail(String.format("Select %s elements are not present", key));
            }

            String currentValue = getAttribute(valueElem, "value");

            if(StringUtils.isNotEmpty(currentValue) && Integer.parseInt(currentValue) > Integer.parseInt(valueToBeSet)){
                int clicksQuantity = Integer.parseInt(currentValue) - Integer.parseInt(valueToBeSet);
                for (int i = 0; i < clicksQuantity; ++i) {
                    sleep(1);
                    removeElem.click();
                }

            }else if(StringUtils.isNotEmpty(currentValue) && Integer.parseInt(currentValue) < Integer.parseInt(valueToBeSet)){
                int clicksQuantity = Integer.parseInt(valueToBeSet) - Integer.parseInt(currentValue);
                for (int i = 0; i < clicksQuantity; ++i) {
                    sleep(1);
                    addElem.click();
                }
            }else{
                log.info(key + ": already contain the input value");
            }

        }

    }

    public void setLastStepTextBoxes(List<List<String>> table){
        DataTable data = createDataTable(table);
        if (data != null) {
            //AtomicInteger i = new AtomicInteger(1);
            data.cells().forEach(value -> {
                String KEY = "";
                String VALUE = "";
                try {
                    List<String> rField = Collections.singletonList(value.get(0));
                    List<String> rValue = Collections.singletonList(value.get(1));
                    KEY = rField.get(0);
                    VALUE = rValue.get(0);

                } catch (NullPointerException e) {
                    log.info(String.format("Path specified on table doesn't exist: %s", KEY));
                    throw new SkipException(String.format("Path specified on table doesn't exist: %s", KEY));
                }

                if (StringUtils.containsIgnoreCase(KEY, "Nombre")) {
                    webSendKeys(NAME_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Apellido")) {
                    webSendKeys(SURNAME_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Fecha de nacimiento")) {
                    webSendKeys(BIRTHDAY_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "DNI")) {
                    webSendKeys(ID_NUMBER_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Sexo biológico")) {
                    selectOptionDropdownByText(BIOLOGIC_GENDER_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Género")) {
                    selectOptionDropdownByText(SELF_GENDER_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "E-mail")) {
                    webSendKeys(EMAIL_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Cód. área")) {
                    webSendKeys(PHONE_CODE_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Celular")) {
                    webSendKeys(PHONE_NUMBER_LAST_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Calle")) {
                    webSendKeys(STREET_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Número")) {
                    webSendKeys(HOUSE_NUMBER_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Piso")) {
                    webSendKeys(FLOOR_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Departamento")) {
                    webSendKeys(APARTMENT_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Código Postal")) {
                    webSendKeys(ZIP_CODE_LOC, VALUE);

                }else if (StringUtils.containsIgnoreCase(KEY, "Ciudad")) {
                    sleep(3);
                    selectOptionDropdownByText(CITY_LOC, VALUE);
                }

            });

        }
    }

}
