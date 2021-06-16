package com.sumerge;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.*;


public class singleUserTest {

    private SoftAssert softly;

    @BeforeClass
    public void setup() {
        softly = new SoftAssert();
    }

    @BeforeClass
    public RequestSpecification setBaseUri() {
        RequestSpecification requestspec = new RequestSpecBuilder().
                setBaseUri("https://reqres.in/api/users").
                build();
        return (requestspec);
    }

    @Test
    public void checkSingleUserData() {
        JsonPath body = given().
                spec(setBaseUri()).
                when().
                get("/2").
                then().assertThat().statusCode(200).
                extract().response().jsonPath();
        softly.assertEquals(body.get("data.id"), 2);
        softly.assertEquals(body.get("data.email"), "janet.weaver@reqres.in");
        softly.assertEquals(body.get("data.first_name"), "Janet");
        softly.assertEquals(body.get("data.last_name"), "Weaver");
        softly.assertEquals(body.get("data.avatar"),  "https://reqres.in/img/faces/2-image.jpg");
        softly.assertAll();
    }

}
