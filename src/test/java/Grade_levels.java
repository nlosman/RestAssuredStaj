import Model.GradeLevel;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Grade_levels extends Setup_Login{

    GradeLevel gradeLevel = new GradeLevel();
    String gradeLevelId="";
    Faker faker = new Faker();
    @Test
    public void createGradeLevel() {
        gradeLevel.name = faker.name().name();
        gradeLevel.shortName = faker.code().gtin8();
        gradeLevel.nextGradeLevel = null;
        gradeLevel.order = "2";
        gradeLevel.maxApplicationCount = faker.number().numberBetween(1, 6);
        gradeLevel.active = true;
        gradeLevel.schoolIds = new ArrayList<>();
        gradeLevel.schoolIds.add("646cbb07acf2ee0d37c6d984");

        gradeLevelId =
                given()
                        .spec(reqSpec)
                        .body(gradeLevel)

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;
    }


    @Test(dependsOnMethods = "createGradeLevel")
    public void createGradeLevelNegative() {
                given()
                        .spec(reqSpec)
                        .body(gradeLevel)

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(400)
        ;
    }


    @Test(dependsOnMethods = "createGradeLevelNegative")
    public void updateGradeLevel() {
        gradeLevel.id = gradeLevelId;
        gradeLevel.name = gradeLevel.name + " updated";

        given()
                .spec(reqSpec)
                .body(gradeLevel)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "updateGradeLevel")
    public void deleteGradeLevel() {

        given()
                .spec(reqSpec)
                .body(gradeLevel)

                .when()
                .delete("school-service/api/grade-levels/" + gradeLevelId)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteGradeLevel")
    public void deleteGradeLevelNegative() {

        given()
                .spec(reqSpec)
                .body(gradeLevel)

                .when()
                .delete("school-service/api/grade-levels/" + gradeLevelId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("Grade Level not found"))
        ;
    }



}
