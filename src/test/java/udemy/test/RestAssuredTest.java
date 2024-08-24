package udemy.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testng.Assert;
import org.testng.SkipException;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.*;


public class RestAssuredTest {



    @Test
    public void getPing() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/ping");
        ResponseBody body = response.getBody();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(body.asString(), "Created");
    }

    @Test
    public void getPing2() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.registerParser("text/plain", Parser.TEXT);
        RequestSpecification request = RestAssured.given();
        request.when().
                get("/ping").
                then().
                statusCode(201).
                body(equalTo("Created"));
    }

    @Test
    public void getPingUsingBuilder() {
        RequestSpecBuilder authBuilder = new RequestSpecBuilder();
        ResponseOptions<Response> response = null;

        try {
            authBuilder.setBaseUri("https://restful-booker.herokuapp.com");
            RequestSpecification requestToken = RestAssured.given().spec(authBuilder.build());
            response = requestToken.get(new URI("/ping"));


        } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
            throw new SkipException("Failed " + e.getMessage());
        }

        System.out.println(response.getStatusCode());
        response.getBody().prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.getBody().asString(), "Created");

    }


    @Test
    public void getIds() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/booking");
        ResponseBody body = response.getBody();
        body.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(body.jsonPath().get("[0].bookingid").toString()!=null);
    }


    @Test
    public void getIds2() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        request.when().
                get("/booking").then().statusCode(200).assertThat()
                .body(notNullValue());
    }

    @Test
    public void getIdsUsingBuilder() {
        RequestSpecBuilder authBuilder = new RequestSpecBuilder();
        ResponseOptions<Response> response = null;

        try {
            authBuilder.setBaseUri("https://restful-booker.herokuapp.com");
            RequestSpecification requestToken = RestAssured.given().spec(authBuilder.build());
            response = requestToken.get(new URI("/booking"));


        } catch (IllegalArgumentException | NullPointerException | URISyntaxException e) {
            throw new SkipException("Failed " + e.getMessage());
        }

        System.out.println(response.getStatusCode());
        response.getBody().prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(StringUtils.isNotEmpty(response.getBody().path("[0].bookingid").toString()));

    }

}
