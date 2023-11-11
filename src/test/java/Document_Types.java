import Model.DocumentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Document_Types extends Setup_Login{
    DocumentType dcty=new DocumentType();

    String documentTypeId="";
    @Test
    public void createDocument(){
        dcty.name= randomUreteci.backToTheFuture().character();
        dcty.description=randomUreteci.funnyName().name();
        dcty.attachmentStages=new Object[1];
        dcty.active=randomUreteci.bool().bool();
        dcty.required=randomUreteci.bool().bool();
        dcty.translateName=new Object[0];

        documentTypeId=
                given()
                        .spec(reqSpec)
                        .body(dcty)

                        .when()
                        .post("school-service/api/attachments/create")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
        ;

    }
    //    @Test(dependsOnMethods = "createDocument")
//    public void createDocumentNegative(){
//
//        given()
//                .spec(reqSpec)
//                .body(dcty)
//
//                .when()
//                .post("school-service/attachments/create")
//
//                .then()
//                .log().body()
//                .statusCode(400)
//                .body("message", containsString("already"))
//        ;
//
//    }
    @Test(dependsOnMethods = "createDocument")
    public void updateDocument(){
        dcty.id=documentTypeId;
        dcty.name=randomUreteci.color().name();

        given()
                .spec(reqSpec)
                .body(dcty)

                .when()
                .put("school-service/api/attachments")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(dcty.name))
        ;

    }

    @Test(dependsOnMethods ="updateDocument")
    public void deleteDocument(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/attachments/"+documentTypeId)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteDocument")
    public void deleteDocumentNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/attachments/"+documentTypeId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Attachment Type not found"))
        ;


    }
}
