import Model.BankAccount;
import Model.Login;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Bank_Accounts extends Setup_Login {

    BankAccount bankAccount = new BankAccount();
    String bankAccountId="";
    Faker faker = new Faker();
    @Test
    public void createBankAccount() {
        bankAccount.name = faker.name().name();
        bankAccount.iban = faker.idNumber().invalid();
        bankAccount.currency = "EUR";
        bankAccount.active = true;
        bankAccount.integrationCode = faker.code().imei();
        bankAccount.schoolId = "646cbb07acf2ee0d37c6d984";

        bankAccountId =
        given()
                .spec(reqSpec)
                .body(bankAccount)

                .when()
                .post("school-service/api/bank-accounts")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;
    }

    @Test(dependsOnMethods = "createBankAccount")
    public void createBankAccountNegative() {

                given()
                        .spec(reqSpec)
                        .body(bankAccount)

                        .when()
                        .post("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(400)
        ;
    }


    @Test(dependsOnMethods = "createBankAccountNegative")
    public void updateBankAccount() {
        bankAccount.id = bankAccountId;
        bankAccount.name = bankAccount.name + " updated";

                given()
                        .spec(reqSpec)
                        .body(bankAccount)

                        .when()
                        .put("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("id")

        ;
    }

    @Test(dependsOnMethods = "updateBankAccount")
    public void deleteBankAccount() {

        given()
                .spec(reqSpec)
                .body(bankAccount)

                .when()
                .delete("school-service/api/bank-accounts/" + bankAccountId)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteBankAccount")
    public void deleteBankAccountNegative() {

        given()
                .spec(reqSpec)
                .body(bankAccount)

                .when()
                .delete("school-service/api/bank-accounts/" + bankAccountId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", containsString("bank account must be exist"))
        ;
    }



}
