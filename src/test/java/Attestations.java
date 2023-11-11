import Model.Attestation;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class Attestations extends Setup_Login {

    Attestation ast=new Attestation();

    String attestationsId="";

    @Test
    public void createAttestation(){
        ast.name=randomUreteci.ancient().hero();
        ast.translateName=new Object[0];

        attestationsId=
                given()
                        .spec(reqSpec)
                        .body(ast)

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
    }

    @Test(dependsOnMethods = "createAttestation")
    public void createAttestationNegative(){

        given()
                .spec(reqSpec)
                .body(ast)

                .when()
                .post("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"));

    }

    @Test(dependsOnMethods = "createAttestationNegative")
    public void updateAttestation(){
        ast.id=attestationsId;
        ast.name= randomUreteci.funnyName().name();

        given()
                .spec(reqSpec)
                .body(ast)

                .when()
                .put("/school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(ast.name))
        ;

    }

    @Test(dependsOnMethods = "updateAttestation")
    public void deleteAttestation(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/attestation/"+attestationsId)

                .then()
                .log().body()
                .statusCode(204)
        ;


    }

    @Test(dependsOnMethods = "deleteAttestation")
    public void deleteAttestationNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/attestation/"+attestationsId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("attestation not found"))
        ;


    }
}
