import Model.PositionCategorie;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Position_Categories extends Setup_Login{
    PositionCategorie pstc = new PositionCategorie();
    String positionCatId="";

    @Test
    public void createPositionCategories(){
        pstc.name=randomUreteci.ancient().hero();
        pstc.translateName=new Object[0];

        positionCatId=
                given()
                        .spec(reqSpec)
                        .body(pstc)

                        .when()
                        .post("school-service/api/position-category")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
    }

    @Test(dependsOnMethods = "createPositionCategories")
    public void createPositionCategoriesNegative(){

        given()
                .spec(reqSpec)
                .body(pstc)

                .when()
                .post("school-service/api/position-category")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"));

    }
    @Test(dependsOnMethods = "createPositionCategoriesNegative")
    public void updatePositionCategories(){
        pstc.id=positionCatId;
        pstc.name= randomUreteci.funnyName().name();

        given()
                .spec(reqSpec)
                .body(pstc)

                .when()
                .put("school-service/api/position-category")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(pstc.name))
        ;

    }

    @Test(dependsOnMethods = "updatePositionCategories")
    public void deletePositionCategories(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/position-category/"+positionCatId)

                .then()
                .log().body()
                .statusCode(204)
        ;


    }

    @Test(dependsOnMethods = "deletePositionCategories")
    public void deletePositionCategoriesNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/position-category/"+positionCatId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("PositionCategory not  found"))
        ;


    }

}
