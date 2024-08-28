package udemy.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.SkipException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.*;

@Log
public class RestAssuredPostTest {
    JSONObject auth = new JSONObject();
    JSONObject createBooking = new JSONObject();
    JSONObject bookingdates = new JSONObject();

    String TOKEN = "";

    JSONParser parser = new JSONParser();



    RestAssuredPostTest(){
        auth.put("username", "admin");
        auth.put("password", "password123");

        createBooking.put("firstname", "Mervin Alberto");
        createBooking.put("lastname", "Mervin Alberto");
        createBooking.put("totalprice", 499);
        createBooking.put("depositpaid", true);
        bookingdates.put( "checkin", "2024-08-20");
        bookingdates.put("checkout", "2024-08-30");
        createBooking.put("bookingdates", bookingdates);
        createBooking.put("additionalneeds", "No requierements");
    }



    @Test
    public void postAuthToken() throws ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        //SERIALIZACION
        //request.body(auth.toJSONString());
        request.body("{\"username\": \"admin\", \"password\" : \"password123\"}");
        Response response = request.post("/auth");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);

        //DESERIALIZACION
        JsonPath rawResponse= response.getBody().jsonPath();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());

        TOKEN = rawResponse.get("token").toString();
        log.info(TOKEN);
        Assert.assertTrue(jsonResponse.containsKey("token"));
        Assert.assertTrue(StringUtils.isNotEmpty(TOKEN));

    }



    @Test
    public void postAuthToken2() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.registerParser("text/plain", Parser.TEXT);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body("{\"username\": \"admin\", \"password\" : \"password123\"}");
        request.when().
                post("/auth").
                then().
                statusCode(200).
                body("token", not(empty()));
    }

    @Test
    public void postAuthTokenUsingBuilder() throws ParseException {
        RequestSpecBuilder authBuilder = new RequestSpecBuilder();
        Response response = null;

        try {
            authBuilder.setBaseUri("https://restful-booker.herokuapp.com");
            authBuilder.addHeader("Content-Type", "application/json");
            authBuilder.setBody("{\"username\": \"admin\", \"password\" : \"password123\"}");

            RequestSpecification requestToken = RestAssured.given().spec(authBuilder.build());
            response = requestToken.post(new URI("/auth"));


        } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
            throw new SkipException("Failed " + e.getMessage());
        }

        System.out.println(response.getStatusCode());
        response.getBody().prettyPrint();

        //DESERIALIZACION
        JsonPath rawResponse= response.getBody().jsonPath();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());


        TOKEN = rawResponse.get("token").toString();
        log.info(TOKEN);
        Assert.assertTrue(jsonResponse.containsKey("token"));
        Assert.assertTrue(StringUtils.isNotEmpty(TOKEN));

    }

    @Test
    public void postCreateBooking() throws ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        //request.header("Cookie", "token=".concat(TOKEN));

        //SERIALIZACION
        request.body(createBooking.toJSONString());
        Response response = request.post("/booking");
        response.prettyPrint();

        //DESERIALIZACION
        JsonPath rawResponse= response.getBody().jsonPath();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        String bookingId = rawResponse.get("bookingid").toString();
        log.info(bookingId);
        Assert.assertTrue(jsonResponse.containsKey("bookingid"));
        Assert.assertTrue(StringUtils.isNotEmpty(bookingId));

    }

    @Test
    public void postCreateBookingUsingFile() throws ParseException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        //request.header("Cookie", "token=".concat(TOKEN));

        //SERIALIZACION
        request.body(getFileBody("/src/test/resources/data/jsonData/createBooking.json"));
        Response response = request.post("/booking");
        response.prettyPrint();

        //DESERIALIZACION
        JsonPath rawResponse= response.getBody().jsonPath();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        String bookingId = rawResponse.get("bookingid").toString();
        log.info(bookingId);
        Assert.assertTrue(jsonResponse.containsKey("bookingid"));
        Assert.assertTrue(StringUtils.isNotEmpty(bookingId));

    }

    public String getFileBody(String path){
        String bodyPath;
        try {
            bodyPath = new String(Files.readAllBytes(Paths.get(getCurrentPath()+path)));
        } catch (Exception e) {
            throw new SkipException("check configProperties or path variable " + e.getMessage());
        }

        return bodyPath;
    }

    public static String getCurrentPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

}
