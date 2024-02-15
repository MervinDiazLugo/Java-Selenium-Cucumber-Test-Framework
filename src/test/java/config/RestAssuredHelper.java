package config;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import java.io.IOException;
import java.net.CacheRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.SkipException;
import org.testng.log4testng.Logger;

public class RestAssuredHelper extends RestAssuredExtension {
  public static Logger log = Logger.getLogger(RestAssuredHelper.class);

  public static JSONObject testData = setTestData();

  private static final String FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";

  public static JSONObject setTestData() {
    JSONObject data = new JSONObject();
    data.put("apiVersion", getApiVersion());
    data.put("uuid", UUID.randomUUID());
    data.put("uuid2", UUID.randomUUID());
    data.put("randomNumber", createRandomNumber());
    data.put("randomCUIL", createRandomCUIL());
    data.put("today", getTodayDate());
    data.put("lastYear", addDaysToDate("today", -362));
    data.put("timeIpsa", addDaysToDate("today", -340));
    data.put("timeCer", addDaysToDate("today", -156));
    data.put("tomorrow", addDaysToDate("today", -365));
    data.put("lastCancel", addDaysToDate("today", -365));
    //initGlobalTestData(data);

    return data;
  }

  public static String addDaysToDate(String startDate, int daysToAdd) {
    Date currentDatePlusDays = new Date();
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

    if (StringUtils.equalsIgnoreCase(startDate, "today")) {
      startDate = new SimpleDateFormat(FORMAT_DATE).format(Calendar.getInstance().getTime());
    } else {
      startDate =
          String.valueOf(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("uuuu-MM-dd")))
              .concat("T16:00:00");
    }

    Calendar c = Calendar.getInstance();
    try {
      c.setTime(format.parse(startDate));
      c.add(Calendar.DAY_OF_MONTH, daysToAdd);
      currentDatePlusDays = c.getTime();
    } catch (java.text.ParseException e) {
      log.info("Error converting dates");
    }
    return format.format(currentDatePlusDays);
  }

  private void initGlobalTestData(JSONObject data) {
    String bodyPath;
    JSONObject jsonData = null;
    try {
      bodyPath =
          new String(
              Files.readAllBytes(
                  Paths.get(getCurrentPath() + "/src/test/resources/data/data.json")));
    } catch (IOException | NullPointerException e) {
      throw new SkipException("check configProperties or path variable " + e.getMessage());
    }

    if (StringUtils.isNotEmpty(bodyPath)) {
      try {
        JSONParser parser = new JSONParser();
        jsonData = (JSONObject) parser.parse(bodyPath);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    } else {
      log.info("data.json is empty");
    }

    if (!jsonData.isEmpty()) {
      data.putAll(jsonData);
    }
  }

  public static String createRandomNumber() {
    long randomNumber = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
    return String.valueOf(randomNumber);
  }

  private static String createRandomCUIL() {

    int[] cuitStartNumbers = {20, 24, 27, 30, 34};
    int randomStartNumberIndex = (int) (Math.random() * 5);
    int randomStartNumber = cuitStartNumbers[randomStartNumberIndex];
    int randomNumber = (int) (Math.random() * 89999999 + 10000000);
    String cuit = Integer.toString(randomStartNumber) + Integer.toString(randomNumber);
    int suma = 0;
    for (int i = 0; i < cuit.length(); i++) {
      int digit = Character.getNumericValue(cuit.charAt(cuit.length() - i - 1));
      suma += digit * (2 + (i % 6));
    }
    int verificador = (11 - (suma % 11));
    verificador = (verificador == 11) ? 0 : verificador;
    String result = cuit + verificador;

    return result;
  }

  private String createRandomNumber(int size) {
    String number = "";
    while (size < 10000) {
      long num = (long) Math.floor(Math.random() * 90000000000L) + 10000000000L;
      if (Long.toString(num).length() == size) {
        number = String.valueOf(num);
        break;
      }
    }
    return number;
  }

  public static String getTodayDate() {
    Date currentDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

    String today = new SimpleDateFormat(FORMAT_DATE).format(Calendar.getInstance().getTime());
    Calendar c = Calendar.getInstance();

    try {
      c.setTime(format.parse(today));
      currentDate = c.getTime();
    } catch (Exception e) {
      log.info("Error converting dates");
    }
    return format.format(currentDate);
  }

  public static int getCurrentYear() {
    return Year.now().getValue();
  }

  public static Date parseDate(String rawDate) throws java.text.ParseException {
    Date parseDate = new Date();
    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

    String parseRawDate = String.valueOf(rawDate);
    Calendar c = Calendar.getInstance();

    try {
      c.setTime(format.parse(parseRawDate));
      parseDate = c.getTime();
    } catch (Exception e) {
      log.info("Error converting dates");
    }
    return parseDate;
  }

  public static String insertParams(String stringData) {
    StringBuffer stringbuffer = new StringBuffer();
    Pattern pattern = Pattern.compile("\\$(\\w+)");
    Matcher matcher;
    String replacement = null;
    if (StringUtils.isEmpty(stringData)) {
      throw new SkipException("String is empty");
    }
    if (testData.isEmpty()) {
      throw new SkipException("Test Data is empty");
    }
    matcher = pattern.matcher(stringData);
    while (matcher.find()) {
      String varName = matcher.group(1);
      boolean keyExist = testData.containsKey(varName);
      replacement = keyExist ? testData.get(varName).toString() : stringData;

      if (StringUtils.equalsIgnoreCase("uuid", varName)) {
        replacement = UUID.randomUUID().toString();
      }

      if (StringUtils.equalsIgnoreCase("birth_date", varName)) {
        setModifiedBirthDates(varName);
      }

      if (StringUtils.isNotEmpty(replacement)) {
        matcher.appendReplacement(
            stringbuffer,
            matcher.group(1).replaceFirst(Pattern.quote(matcher.group(1)), replacement));
      }
    }
    if (StringUtils.isNotEmpty(stringbuffer)) {
      matcher.appendTail(stringbuffer);
      stringData = stringbuffer.toString();
    }
    return stringData;
  }

  public static void setModifiedBirthDates(String varName) {
    String birth_date = testData.get(varName).toString();
    String year = StringUtils.substringBefore(birth_date, "-");
    int yearMinus = getCurrentYear() - 1;
    String split_birth_date = StringUtils.remove(birth_date, year);
    String registrationDate = String.valueOf(yearMinus).concat(split_birth_date);
    saveInTestData("birth_date_modified", addDaysToDate(registrationDate, -1));
    saveInTestData("birth_date_modified_15", addDaysToDate(registrationDate, +15));
    saveInTestData("birth_date_modified_2", addDaysToDate(registrationDate, -3));
  }

  public static String getBodyFromResource(String bodyPath) {
    try {
      bodyPath =
          new String(Files.readAllBytes(Paths.get(getCurrentPath() + getBodyData() + bodyPath)));
    } catch (IOException | NullPointerException e) {
      throw new SkipException("check configProperties or path variable " + e.getMessage());
    }
    return bodyPath;
  }

  public static void saveInTestData(String key, String value) {
    if (StringUtils.isNotEmpty(key)) {
      if (testData.containsKey(key)) {
        testData.replace(key, value);
      } else {
        testData.put(key, value);
      }
      log.info("Test Data updated: " + testData.toString());
    } else {
      log.info("testData data: value was empty");
    }
  }



  public static void getDataFromTable(List<List<String>> table) {
    DataTable data = createDataTable(table);
    if (data != null) {
      data.cells()
          .forEach(
              value -> {
                String key = "";
                String val = "";
                try {
                  List<String> rField = Collections.singletonList(value.get(0));
                  List<String> rValue = Collections.singletonList(value.get(1));
                  key = rField.get(0);
                  val = rValue.get(0);
                } catch (NullPointerException e) {
                  throw new SkipException(
                      String.format("key specified on table doesn't exist: %s", key));
                }
                if (StringUtils.isEmpty(val)) {
                  val = "";
                }
                saveInTestData(key, val);
              });
    }
  }

  public static void getDataFromResponse(List<List<String>> table) {
    DataTable data = createDataTable(table);
    if (data != null) {
      data.cells()
          .forEach(
              value -> {
                String key = "";
                String val = "";
                try {
                  List<String> rField = Collections.singletonList(value.get(0));
                  List<String> rValue = Collections.singletonList(value.get(1));
                  key = rField.get(0);
                  val = rValue.get(0);
                } catch (NullPointerException e) {
                  throw new SkipException(
                      String.format("key specified on table doesn't exist: %s", key));
                }
                saveInTestData(key, retrieveResponse(val));
              });
    }
  }

  /**
   * Create a table with parameters given on feature step.
   *
   * @param table is a list with parameters given on step.
   */
  public static DataTable createDataTable(List<List<String>> table) {
    DataTable data;
    data = DataTable.create(table);
    log.info(data.toString());
    return data;
  }
  public static String retrieveResponse(String key) {
    String value = "";
    ArrayList<String> arrayValue = new ArrayList<>();
    String classType = response.getBody().path(key).getClass().toString();
    if (response != null && response.getBody() != null) {
      if (response.getBody().path(key) != null) {
        if ("class java.util.ArrayList".equals(classType)) {
          arrayValue = response.getBody().path(key);
          value = StringUtils.equals(arrayValue.toString(), "[]") ? "" : arrayValue.toString();
        }else{
          value = response.getBody().path(key).toString();
        }
      } else {
        throw new SkipException("Selected path didn't exist ");
      }
    } else {
      throw new SkipException("Response is null or empty");
    }

    return value;
  }
}
