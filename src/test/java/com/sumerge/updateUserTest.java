package com.sumerge;

import io.restassured.path.json.JsonPath;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
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
    }

    //update user data and get response to assert it
    public JsonPath updateUser(){
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "morpheus");
        jsonBody.put("job", "zion resident");
        jsonBody.put("id", createUser.returnID());

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

    //Doing assertions
    @Test (dependsOnGroups = "creation")
    public void checkUpdatedUserData(){
        JsonPath response = updateUser();
        softly.assertEquals(response.get("name"), "morpheus");
        softly.assertEquals(response.get("job"), "zion resident");
        softly.assertEquals(response.get("id"), createUser.returnID());
        softly.assertAll();
        System.out.println("ID recieved by update class: "+createUser.returnID());
    }
}

