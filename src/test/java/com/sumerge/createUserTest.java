package com.sumerge;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class createUserTest {

    private singleUserTest s;
    private SoftAssert softly;
    private static String id;
    @BeforeClass
    public void setup()
    {
        s = new singleUserTest();
        softly = new SoftAssert();
    }

    public JsonPath create() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "morpheus");
        jsonBody.put("job", "leader");
        JsonPath response =
                given().
                        spec(s.setBaseUri()).
                        header("Content-Type","application/json").
                        body(jsonBody.toJSONString()).
                        when().
                        post().
                        then().
                        assertThat().statusCode(201).
                        extract().response().jsonPath();
        this.id=response.get("id");
        return (response);
    }

    public String returnID(){
        System.out.println(id);
        return (this.id);
    }

    @Test(groups = "creation")
    public void checkCreatedUserData(){
        JsonPath response = create();
        softly.assertEquals(response.get("name"), "morpheus");
        softly.assertEquals(response.get("job"), "leader");
        softly.assertAll();
    }
}
