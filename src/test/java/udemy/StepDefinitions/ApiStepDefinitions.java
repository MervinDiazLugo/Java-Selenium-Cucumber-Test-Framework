package udemy.StepDefinitions;

import static config.RestAssuredHelper.*;

import config.RestAssuredExtension;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
@Log
public class ApiStepDefinitions extends RestAssuredExtension {

  public ApiStepDefinitions() {}

  @Given("^I do a POST in (.*?) using body (.*?)$")
  public void iDoAPost(String endPoint, String bodyPath) {
    apiPost(endPoint, bodyPath);
  }

  @Given("^I do a POST in (.*?) using body (.*?) with variables bellow$")
  public void iDoAPost(String endPoint, String bodyPath, List<List<String>> table) {
    getDataFromTable(table);
    apiPost(endPoint, bodyPath);
  }

  @Given("^I print the api Response$")
  public void iDoAPrint() {
    response.getBody().prettyPrint();
    long responseTime1 = response.getTime();
    log.info("Response time in milliseconds:" + responseTime1);
    long responseTimeInSeconds = response.getTimeIn(TimeUnit.SECONDS);
    log.info("Response time in seconds:" + responseTimeInSeconds);
  }

  @And("^I save the response key (.*?) on test data$")
  public void iSaveTheResponseKeyOnTestData(String key) {
    saveInTestData(key, retrieveResponse(key));
  }

  @Then("^I do a GET in (.*?)$")
  public void iDoAGet(String bodyPath) {
    apiGet(bodyPath);
  }

  @Then("^I do a DELETE in (.*?)$")
  public void iDoDelete(String bodyPath) {
    apiDelete(bodyPath);
  }

  @And("^I validate status code is (.*?)$")
  public void iValidateStatusCodeIs(String code) {
    Assert.assertTrue(
        StringUtils.equals(String.valueOf(response.statusCode()), code),
        String.format(
            "Status code in response is %s but expected is %s",
            String.valueOf(response.statusCode()), code));
  }

  @Given("^I Save in scenario data following (.*?) and (.*?)$")
  public void iSaveInScenarioDataFollowingKeyAndValue(String key, String value) {
    saveInTestData(key, value);
  }

  @And("^I save the response key (.*?) as (.*?)$")
  public void iSaveTheResponseKeyFilesIdAsFileId(String value, String key) {
    saveInTestData(key, retrieveResponse(value));
  }

  @And("^I validate response error message: (.*?)$")
  public void validateMessageResponse(String message) {
    Assert.assertTrue(
        StringUtils.containsIgnoreCase(response.getBody().asPrettyString(), message),
        String.format(
            "Error message response is %s but expected is %s",
            response.getBody().asPrettyString(), message));
  }

  @Then("^I Save the response with following values and keys$")
  public void iSaveTheResponseWithFollowingValuesAndKeys(List<List<String>> table) {
    getDataFromResponse(table);
  }

  @And("^I assert following (.*?) is empty$")
  public void iAssertFollowingFileIsEmpty(String key) {
    Assert.assertTrue(StringUtils.isEmpty(retrieveResponse(key)), "Files is not empty");
  }

  @And("^I assert entity (.*?) is (.*?)$")
  public void iAssertEntityValue(String key, String value) {
    String val = insertParams(value);
    String responseValue = retrieveResponse(key);
    Assert.assertTrue(
        StringUtils.equals(retrieveResponse(key), val),
        String.format(
            "Values does not match, response is %s and expected is %s", val, responseValue));
  }

  @Given("^I do a PUT in (.*?) using body (.*?)$")
  public void iDoAPUTClaim(String endPoint, String bodyPath, List<List<String>> table) {
    getDataFromTable(table);
    apiPut(endPoint, bodyPath);
  }

  @And("^I wait before send a new request$")
  public void iWaitBeforeSendANewRequest() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Then("^I validate that (.*?) is equal to date$")
  public void iValidateThatFileDateIsEqualToDate(String value) throws ParseException {
    Date currentDate = parseDate(getTodayDate());
    String rawDate = retrieveResponse(value);
    Date responseDate = parseDate(rawDate);
    Assert.assertTrue(
        responseDate.after(currentDate) && currentDate.before(responseDate),
        "The modification date is not equal to the current date");
  }
}
