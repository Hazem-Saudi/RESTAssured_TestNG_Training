package com.sumerge;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.lang.reflect.Array;
import static io.restassured.RestAssured.given;

public class multiUserTest {

    private singleUserTest s;
    private SoftAssert softly;

    @BeforeClass public void setup()
    {
        s = new singleUserTest();
        softly = new SoftAssert();
    }

    //Creating test data
    public static Object[][] generateData() {
        return new Object[][]
                {
                        {7, "michael.lawson@reqres.in", "Michael", "Lawson",
                                "https://reqres.in/img/faces/7-image.jpg"},
                        {8, "lindsay.ferguson@reqres.in", "Lindsay", "Ferguson",
                                "https://reqres.in/img/faces/8-image.jpg"},
                        {9, "tobias.funke@reqres.in", "Tobias", "Funke",
                                "https://reqres.in/img/faces/9-image.jpg"},
                        {10, "byron.fields@reqres.in", "Byron", "Fields",
                                "https://reqres.in/img/faces/10-image.jpg"},
                        {11, "george.edwards@reqres.in", "George", "Edwards",
                                "https://reqres.in/img/faces/11-image.jpg"},
                        {12, "rachel.howell@reqres.in", "Rachel", "Howell",
                                "https://reqres.in/img/faces/12-image.jpg"},
                };
    }

    //Doing assertions
    @Test
    public void checkMultiUsersData() {
        Object[][] myData = generateData();
        JsonPath body = given().
                spec(s.setBaseUri()).
                when().
                get("?page=2").
                then().assertThat().statusCode(200).
                extract().response().jsonPath();

        for (int x = 0; x < Array.getLength(myData); x++) {
            int currentID = body.get("data[" + x + "].id");
            String currentEmail = body.get("data[" + x + "].email");
            String currentFirst = body.get("data[" + x + "].first_name");
            String currentLast = body.get("data[" + x + "].last_name");
            String currentAvatar = body.get("data[" + x + "].avatar");
            softly.assertEquals(currentID, myData[x][0]);
            softly.assertEquals(currentEmail, myData[x][1]);
            softly.assertEquals(currentFirst, myData[x][2]);
            softly.assertEquals(currentLast, myData[x][3]);
            softly.assertEquals(currentAvatar, myData[x][4]);
        }
        softly.assertAll();
    }


}
