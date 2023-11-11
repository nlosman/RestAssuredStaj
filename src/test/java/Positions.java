import Model.Position;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Positions extends Setup_Login{
    Position pst = new Position();
    String positionId="";
    @Test
    public void createPositions(){
        pst.name=randomUreteci.ancient().hero();
        pst.shortName=randomUreteci.funnyName().name();
        pst.translateName=new Object[0];
        pst.active=randomUreteci.bool().bool();

        positionId=
                given()
                        .spec(reqSpec)
                        .body(pst)

                        .when()
                        .post("school-service/api/employee-position")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }
    @Test(dependsOnMethods = "createPositions")
    public void createPositionsNegative(){

        given()
                .spec(reqSpec)
                .body(pst)

                .when()
                .post("school-service/api/employee-position")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"));

    }
    @Test(dependsOnMethods = "createPositionsNegative")
    public void updatePositions(){
        pst.id=positionId;
        pst.name= randomUreteci.funnyName().name();

        given()
                .spec(reqSpec)
                .body(pst)

                .when()
                .put("school-service/api/employee-position")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(pst.name))
        ;

    }

    @Test(dependsOnMethods = "updatePositions")
    public void deletePositions(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/employee-position/"+positionId)

                .then()
                .log().body()
                .statusCode(204)
        ;


    }

//    @Test(dependsOnMethods = "deletePositions")
//    public void deletePositionsNegative(){
//        given()
//                .spec(reqSpec)
//                .when()
//                .delete("school-service/api/employee-position/"+positionId)
//
//                .then()
//                .log().body()
//                .statusCode(204)
//                .body("message", equalTo("Position not  found"))
//        ;
//
//
//    }


}
