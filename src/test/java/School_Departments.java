import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class School_Departments extends Setup_Login {

    Faker random = new Faker();
    String rndmDepName = "";
    String rndmDepCode = "";

    String departmentID = "";



    @Test
    public void CreateDepartment() {
        rndmDepName = random.commerce().department() + random.address().countryCode();
        rndmDepCode = random.address().zipCode();

        Map<String, String> newDepartment = new HashMap<>();
        newDepartment.put("name", rndmDepName);
        newDepartment.put("code", rndmDepCode);
        newDepartment.put("school", "646cbb07acf2ee0d37c6d984");

        departmentID =
                given()
                        .spec(reqSpec).
                        body(newDepartment).

                        when()
                        .post("school-service/api/department")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods = "CreateDepartment")
    public void updateDepartment() {

        String newDepartmentName = "updated Department" + random.number().digits(5);
        String newDepartmentCode = random.number().digits(6);

        Map<String, String> updDepartment = new HashMap<>();
        updDepartment.put("id", departmentID);
        updDepartment.put("name", newDepartmentName);
        updDepartment.put("code", newDepartmentCode);
        updDepartment.put("school","646cbb07acf2ee0d37c6d984");

        given().
                spec(reqSpec).
                body(updDepartment).

                when()
                .put("school-service/api/department")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(newDepartmentName));
    }


    @Test(dependsOnMethods = "updateDepartment")
    public void deleteDepartment(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/department/"+departmentID)

                .then()
                .log().body()
                .statusCode(204);
    }
}
