
import Model.SchoolLocations;

import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class School_Locations extends Setup_Login {

    String locationsId = "";

    SchoolLocations schoolLct = new SchoolLocations();

    @Test (priority = 0)
    public void getLocations() {

        given()
                .spec(reqSpec)

                .when()
                .get("school-service/api/school/646cbb07acf2ee0d37c6d984/location")

                .then()
                .log().body()
                .statusCode(200)

        ;

    }

    @Test (priority = 1)
    public void createLocation() {

        schoolLct.name = "testname";
        schoolLct.shortName = "testshort";
        schoolLct.active = true;
        schoolLct.capacity = 10;
        schoolLct.type = "CLASS";
        schoolLct.school = "646cbb07acf2ee0d37c6d984";

        locationsId=
                given()
                        .spec(reqSpec)
                        .body(schoolLct)

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()

                        .statusCode(201)
                        .extract().path("id")
        ;


    }

    @Test(priority = 2)
    public void createLocationNegative() {


        schoolLct.name = "testname";
        schoolLct.shortName = "testshort";
        schoolLct.active = true;
        schoolLct.capacity = 10;
        schoolLct.type = "CLASS";
        schoolLct.school = "646cbb07acf2ee0d37c6d984";


        given()
                .spec(reqSpec)
                .body(schoolLct)

                .when()
                .post("school-service/api/location")

                .then()
                .log().body()

                .statusCode(400)
                .body("message", containsString("already"))
        ;


    }


    @Test(priority = 3)
    public void updateLocation() {


        schoolLct.id= locationsId;
        schoolLct.name = "fn111";
        schoolLct.shortName = "ddff";
        schoolLct.active = randomUreteci.bool().bool();
        schoolLct.capacity = randomUreteci.hashCode();
        schoolLct.type = "CLASS";
        schoolLct.school = "646cbb07acf2ee0d37c6d984";


        given()
                .spec(reqSpec)
                .body(schoolLct)

                .when()
                .put("school-service/api/location")

                .then()
                .log().body()

                .statusCode(200)
                .body("name", equalTo(schoolLct.name))

        ;


    }

    @Test (priority = 4)
    public void deleteLocation(){

        given()
                .spec(reqSpec)
                .when()

                .delete("school-service/api/location/"+locationsId)

                .then()
                .log().body()

                .statusCode(200)


                ;

    }


    @Test (priority = 5)
    public void deleteLocationNegative(){

        given()

                .spec(reqSpec)
                .when()

                .delete("school-service/api/location/"+locationsId)

                .then()
                .log().body()

                .statusCode(400)
                .body("message",equalTo("School Location not found"))

;
    }
}
