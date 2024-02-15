package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.log4testng.Logger;

import static config.RestAssuredHelper.getBodyFromResource;
import static config.RestAssuredHelper.insertParams;

public class RestAssuredExtension extends RestAssuredConfigProperties{

  public static RequestSpecBuilder apiBuilder = new RequestSpecBuilder();
  public static RestAssuredConfigProperties apiProperties = new RestAssuredConfigProperties();
  public static Logger log = Logger.getLogger(RestAssuredExtension.class);

  public static String apiVersion;
  public static String apiUri;
  public static String authEndpoint;
  public static String apiPath;

  public static boolean isAlreadyAuthenticated;

  static ResponseOptions<Response> authToken;
  public static ResponseOptions<Response> response;

  public RestAssuredExtension() {
    apiVersion = getApiVersion();
    apiUri = getBaseUri().concat(apiVersion);
    // authEndpoint = String.format(apiProperties.getAuthEndPoint(), apiVersion);
    //testData = setTestData();
    try {
      apiBuilder.setBaseUri(apiUri);
      apiBuilder.setContentType(ContentType.JSON);
      apiBuilder.setAccept("*/*");
      if (!isAlreadyAuthenticated) {
        //authentication();
        //apiBuilder.addHeader("Authorization", getBearerToken());
      }

    } catch (IllegalArgumentException e) {
      log.info("Base URI cannot be null, check configProperties");
    }
  }

  /**
   * get response from GraphQL Api
   *
   * @return Api responses
   */
  public static ResponseOptions<Response> authentication() {
    RequestSpecBuilder authBuilder = new RequestSpecBuilder();
    setDefaultHeaders();
    try {
      authBuilder.setBaseUri(apiUri);
      // authBuilder.addQueryParam("username", prop.getUsername());
      // authBuilder.addQueryParam("password", prop.getPassword());
      RequestSpecification requestToken = RestAssured.given().spec(authBuilder.build());
      authToken = requestToken.post(new URI(authEndpoint));

      if (authToken.getStatusCode() != 200) {
        throw new SkipException("Authentication failed " + authToken.getStatusCode());
      }

    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Authentication failed " + e.getMessage());
    }
    isAlreadyAuthenticated = true;
    return authToken;
  }

  /** put default header to request */
  private static void setDefaultHeaders() {
    apiBuilder.addHeader("Content-Type", "application/json");
  }

  public static String getBearerToken() {
    return "Bearer " + authToken.getBody().jsonPath().get("token");
  }

  public ResponseOptions<Response> apiGet(String path) {
    path = insertParams(path);
    try {
      apiBuilder.setBaseUri(apiUri);
      // body
      // query params
      RequestSpecification requestToken = RestAssured.given().spec(apiBuilder.build());
      response = requestToken.get(new URI(path));

    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Api Get failed " + e);
    }
    return response;
  }

  public ResponseOptions<Response> apiPost(String path, String bodyPath) {
    path = insertParams(path);
    try {
      apiBuilder.setBaseUri(apiUri);

      String body = getBodyFromResource(bodyPath);

      if (StringUtils.isNotEmpty(body)) {
        body = insertParams(body);
      } else {
        throw new SkipException("Body is Empty");
      }
      apiBuilder.setBody(body);
      log.info(body);
      RequestSpecification requestToken = RestAssured.given().spec(apiBuilder.build());
      response = requestToken.post(new URI(path));

      /*if (authToken.getStatusCode() != 200) {
        throw new SkipException("Authentication failed");
      }*/
    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Authentication failed " + e);
    }
    return response;
  }

  public ResponseOptions<Response> apiPut(String path, String bodyPath) {
    path = insertParams(path);
    try {
      apiBuilder.setBaseUri(apiUri);

      String body = getBodyFromResource(bodyPath);

      if (StringUtils.isNotEmpty(body)) {
        body = insertParams(body);
      } else {
        throw new SkipException("Body is Empty");
      }
      apiBuilder.setBody(body);
      log.info(body);
      RequestSpecification requestToken = RestAssured.given().spec(apiBuilder.build());
      response = requestToken.put(new URI(path));

/*      if (authToken.getStatusCode() != 200) {
        throw new SkipException("Authentication failed");
      }*/
    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Authentication failed " + e);
    }
    return response;
  }

  public void addParams(String key, String value) {
    apiBuilder.addQueryParam(key, value);
  }


}
