import Model.Discount;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Set;

public class Discounts extends Setup_Login {
    Discount disc = new Discount();

    String discountId="";

    @Test
    public void createDiscount(){

        disc.description=randomUreteci.backToTheFuture().character();
        disc.code=randomUreteci.ancient().hero();
        disc.active=randomUreteci.bool().bool();
        disc.priority=randomUreteci.number().numberBetween(1,10);

        discountId=
                given()
                .spec(reqSpec)
                        .body(disc)

                        .when()
                        .post("school-service/api/discounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id")
                ;

    }

    @Test(dependsOnMethods = "createDiscount")
    public void createDiscountNegative(){

        given()
                .spec(reqSpec)
                .body(disc)

                .when()
                .post("school-service/api/discounts")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("already"))
                ;

    }
    @Test(dependsOnMethods = "createDiscountNegative")
    public void updateDiscount(){
        disc.id=discountId;
        disc.description=randomUreteci.color().name();

        given()
                .spec(reqSpec)
                .body(disc)

                .when()
                .put("school-service/api/discounts")

                .then()
                .log().body()
                .statusCode(200)
                .body("description", equalTo(disc.description))
                ;

    }

    @Test(dependsOnMethods ="updateDiscount")
    public void deleteDiscount(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/discounts/"+discountId)

                .then()
                .log().body()
                .statusCode(200)
                ;
    }

    @Test(dependsOnMethods = "deleteDiscount")
    public void deleteDiscountNegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("school-service/api/discounts/"+discountId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Discount not found"))
        ;


    }

}
