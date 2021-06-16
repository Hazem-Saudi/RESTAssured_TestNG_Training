package com.sumerge;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class deleteUserTest {

    private singleUserTest s;
    private SoftAssert softly;

    @BeforeClass
    public void setup() {
        s = new singleUserTest();
        softly = new SoftAssert();
    }
    @Test(dependsOnGroups = "update")
    public void deletedUserTest(){
        given().
                spec(s.setBaseUri()).
                when().delete("/2").
                then().
                assertThat().statusCode(204);
    }

}
