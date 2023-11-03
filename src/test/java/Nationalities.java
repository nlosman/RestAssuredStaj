import Model.Nationalitie;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Nationalities extends Setup_Login {
    Nationalitie ntlt=new Nationalitie();

    String nationalitiesId="";

    @Test
    public void createNationalities(){
        ntlt.name=randomUreteci.ancient().hero();
        ntlt.translateName=new Object[0];

        nationalitiesId=
                given()
                        .spec(reqSpec)
                        .body(ntlt)

                        .when()
                        .post("school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
    }

    @Test(dependsOnMethods = "createNationalities")
    public void createNationalitiesnegative(){

        given()
                .spec(reqSpec)
                .body(ntlt)

                .when()
                .post("school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"));

    }

    @Test(dependsOnMethods = "createNationalitiesnegative")
    public void updateNationalities(){
        ntlt.id=nationalitiesId;
        ntlt.name= randomUreteci.funnyName().name();

        given()
                .spec(reqSpec)
                .body(ntlt)

                .when()
                .put("/school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(ntlt.name))
        ;

    }

    @Test(dependsOnMethods = "updateNationalities")
    public void deleteNationalities(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/nationality/"+nationalitiesId)

                .then()
                .log().body()
                .statusCode(200)
        ;


    }

    @Test(dependsOnMethods = "deleteNationalities")
    public void deleteNationalitiesNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/nationality/"+nationalitiesId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Nationality not  found"))
        ;


    }


}
