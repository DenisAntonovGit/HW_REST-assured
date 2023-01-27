import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class HwReqresInTests {

    String baseUrl = "https://reqres.in/";
    String createData = "{ \"name\" : \"Denis\", \"job\": \"QAA\" }";
    String dataForUpdate = "{ \"name\" : \"Denis\", \"job\": \"QAA\" }";

    @Test
    void successfulCreating() {
        given()
                .log().uri()
                .contentType(JSON)
                .body(createData)
                .when()
                .post(baseUrl + "api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name",is("Denis"))
                .body("job", is("QAA"));
    }

    @Test
    void getListUsers() {
        given()
                .log().uri()
                .when()
                .get(baseUrl + "api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2))
                .body("total", is(12))
                .body("total_pages", is(2));
    }

    @Test
    void singleResourceNotFound() {
        given()
                .log().uri()
                .when()
                .get(baseUrl + "api/unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
    @Test
    void updateData() {
        given()
                .log().uri()
                .contentType(JSON)
                .body(dataForUpdate)
                .when()
                .put(baseUrl + "api/users/5")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void deleteUser() {
        given()
                .log().uri()
                .when()
                .delete(baseUrl + "api/users/5")
                .then()
                .log().status()
                .statusCode(204);
    }
}
