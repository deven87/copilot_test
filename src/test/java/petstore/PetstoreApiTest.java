package petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetstoreApiTest {
    static long createdPetId;

    @Test
    @Order(1)
    void getAvailablePets() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get("/pet/findByStatus?status=available")
        .then()
            .statusCode(200)
            .body("", isA(java.util.List.class));
    }

    @Test
    @Order(2)
    void createPet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        String newPet = "{" +
                "\"id\":" + System.currentTimeMillis() + "," +
                "\"name\":\"TestPet\"," +
                "\"photoUrls\":[\"http://example.com/photo.jpg\"]," +
                "\"status\":\"available\"}";
        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(newPet)
        .when()
            .post("/pet");
        response.then().statusCode(anyOf(is(200), is(201)));
        createdPetId = response.jsonPath().getLong("id");
        Assertions.assertEquals("TestPet", response.jsonPath().getString("name"));
    }

    @Test
    @Order(3)
    void updatePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        String updatePet = "{" +
                "\"id\":" + createdPetId + "," +
                "\"name\":\"UpdatedPet\"," +
                "\"photoUrls\":[\"http://example.com/photo.jpg\"]," +
                "\"status\":\"sold\"}";
        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(updatePet)
        .when()
            .put("/pet");
        response.then().statusCode(anyOf(is(200), is(201)));
        Assertions.assertEquals("sold", response.jsonPath().getString("status"));
    }

    @Test
    @Order(4)
    void getPetById() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .get("/pet/" + createdPetId)
        .then()
            .statusCode(200)
            .body("name", equalTo("TestPet"));
    }

    @Test
    @Order(5)
    void deletePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        Response response = RestAssured.given()
            .accept(ContentType.JSON)
        .when()
            .delete("/pet/" + createdPetId);
        response.then().statusCode(anyOf(is(200), is(204)));
    }
}
