package tests;

import static io.restassured.http.ContentType.JSON;;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTests extends TestBase {

    @DisplayName("Создание нового пользователя")
    @Test
    void createUserTest() {
        String postData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .body(postData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @DisplayName("Получение одного пользователя")
    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .get("/users/2")

                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @DisplayName("Пользователь не существует")
    @Test
    void singleUserNotFoundTest() {
        given()
                .log().uri()
                .get("/users/23")

                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @DisplayName("Изменение пользователя")
    @Test
    void updateUserTest() {
        String putData = "{\"name\": \"ivan\", \"job\": \"worker\"}";

        given()
                .body(putData)
                .contentType(JSON)
                .log().uri()

                .when()
                .put("/users/2")

                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("name", is("ivan"))
                .body("job", is("worker"));
    }

    @DisplayName("Удаление пользователя")
    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .delete("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }
}
