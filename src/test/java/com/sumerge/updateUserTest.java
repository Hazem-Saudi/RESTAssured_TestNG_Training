package com.sumerge;

import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class updateUserTest {

    private singleUserTest s;
    private SoftAssert softly;
    private createUserTest createUser;

    @BeforeClass
    public void setup()
    {
        s = new singleUserTest();
        softly = new SoftAssert();
        createUser = new createUserTest();
        System.out.println(createUser.returnID());
    }

    public JsonPath updateUser(){
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "morpheus");
        jsonBody.put("job", "zion resident");
        JsonPath response =
                given().
                        spec(s.setBaseUri()).
                        header("Content-Type","application/json").
                        body(jsonBody.toJSONString()).
                        when().
                        put().
                        then().
                        assertThat().statusCode(200).
                        extract().response().jsonPath();
        return (response);
    }
    @Test (groups = "update",dependsOnGroups = "creation")
    public void checkUpdatedUserData(){
        JsonPath response = updateUser();
        softly.assertEquals(response.get("name"), "morpheus");
        softly.assertEquals(response.get("job"), "zion resident");
        softly.assertAll();
    }
}

