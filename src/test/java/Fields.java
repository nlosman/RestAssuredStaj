import Model.Field;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Fields extends Setup_Login{
    Field field = new Field();

    String fieldId ="";
    @Test
    public void createField(){
        field.name=randomUreteci.ancient().hero();
        field.code=randomUreteci.ancient().titan();
        field.translateName=new Object[0];
        field.children=new Object[0];

        fieldId=
                given()
                        .spec(reqSpec)
                        .body(field)

                        .when()
                        .post("school-service/api/entity-field")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }
    @Test(dependsOnMethods = "createField")
    public void createFieldNegative(){

        given()
                .spec(reqSpec)
                .body(field)

                .when()
                .post("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"));

    }

    @Test(dependsOnMethods = "createFieldNegative")
    public void updateField(){
        field.id=fieldId;
        field.name=randomUreteci.color().name();

        given()
                .spec(reqSpec)
                .body(field)

                .when()
                .put("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(field.name))
        ;

    }
    @Test(dependsOnMethods ="updateField")
    public void deleteField(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/entity-field/"+fieldId)

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteField")
    public void deleteFieldNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/entity-field/"+fieldId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("EntityField not found"))
        ;


    }


}
