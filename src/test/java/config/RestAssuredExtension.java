package config;

import static config.RestAssuredHelper.getBodyFromResource;
import static config.RestAssuredHelper.insertParams;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;

@Log
public class RestAssuredExtension extends RestAssuredConfigProperties {
  public static RequestSpecBuilder apiBuilder = new RequestSpecBuilder();
  public static RestAssuredConfigProperties apiProperties = new RestAssuredConfigProperties();

  public static String apiVersion;
  public static String apiUri;
  public static String authEndpoint = getAuthenticationEndpoint();
  static ResponseOptions<Response> authToken;

  public static boolean isAlreadyAuthenticated;

  public static ResponseOptions<Response> response;
  JsonPath jsonPathResponse;
  ResponseBody responseBody;

  public RestAssuredExtension() {
    apiVersion = getApiVersion();
    apiUri = getBaseUri().concat(apiVersion);
    authentication();
    try {
      apiBuilder.setBaseUri(apiUri);
      apiBuilder.setContentType(ContentType.JSON);
      apiBuilder.setAccept("*/*");

    } catch (IllegalArgumentException e) {
      log.info("Base URI cannot be null, check configProperties");
    }
  }

  /**
   * get response from GraphQL Api
   *
   * @return Api responses
   */
  public ResponseOptions<Response> authentication() {
    RequestSpecBuilder authBuilder = new RequestSpecBuilder();
    authBuilder.setBaseUri(apiUri);
    authEndpoint = getAuthenticationEndpoint();
    setDefaultHeaders();

    if (!isAlreadyAuthenticated) {
      if (StringUtils.contains(apiUri, "petstore.swagger.io")) {
        apiBuilder.addHeader("api_key", "special-key");
        isAlreadyAuthenticated = true;
      } else {
        postAuth(authBuilder);
      }
    }
    return authToken;
  }

  private void postAuth(RequestSpecBuilder authBuilder) {
    try {
      RequestSpecification requestToken = RestAssured.given().spec(authBuilder.build());
      authToken = requestToken.post(authEndpoint);
      if (authToken.getStatusCode() != 200) {
        throw new SkipException("Authentication failed " + authToken.getStatusCode());
      } else {
        isAlreadyAuthenticated = true;
      }
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new SkipException("Authentication failed " + e.getMessage());
    }
  }

  /** put default header to request */
  private static void setDefaultHeaders() {
    apiBuilder.addHeader("Content-Type", "application/json");
  }

  public static String getBearerToken() {
    return "Bearer "; // + authToken.getBody().jsonPath().get("token");
  }

  public ResponseOptions<Response> apiGet(String path) {
    path = insertParams(path);
    try {
      apiBuilder.setBaseUri(apiUri);
      RequestSpecification requestToken = RestAssured.given().spec(apiBuilder.build());
      response = requestToken.get(new URI(path));
      responseBody = response.getBody();
      jsonPathResponse = response.body().jsonPath();
      // validateAuth();

    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Api Get failed " + e);
    }
    return response;
  }

  public ResponseOptions<Response> apiDelete(String path) {
    path = insertParams(path);
    try {
      apiBuilder.setBaseUri(apiUri);
      RequestSpecification requestToken = RestAssured.given().spec(apiBuilder.build());
      response = requestToken.delete(new URI(path));
      responseBody = response.getBody();
      jsonPathResponse = response.body().jsonPath();

    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Api delete failed " + e);
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
      responseBody = response.getBody();
      jsonPathResponse = response.body().jsonPath();
      // validateAuth();
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
      responseBody = response.getBody();
      jsonPathResponse = response.body().jsonPath();
      // validateAuth();

    } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
      throw new SkipException("Authentication failed " + e);
    }
    return response;
  }

  public void addParams(String key, String value) {
    apiBuilder.addQueryParam(key, value);
  }
}
