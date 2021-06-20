package com.sumerge;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
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

    //Creating user and getting response to assert it
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

    //function to pass "ID" to updateUser class
    public String returnID(){
        return (this.id);
    }

    //comment
    //Doing assertions
    //Creating group so update user test can't be preformed unless user is created
    @Test(groups = "creation")
    public void checkCreatedUserData(){
        JsonPath response = create();
        softly.assertEquals(response.get("name"), "morpheus");
        softly.assertEquals(response.get("job"), "leader");
        softly.assertAll();
        System.out.println("ID passed by create class: "+ returnID());
    }
}
