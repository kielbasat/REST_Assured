package STBLessons;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GET_Test {

private final String BaseUrl = "https://jsonplaceholder.typicode.com/";
private final String usersUrl = "users";


    @Test
    public void JSONPlaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get(BaseUrl + usersUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name");
        names.stream()
                .forEach(System.out::println);

        assertEquals(10, names.size());
    }

    @Test
    public void JSONPlaceholderReadOneUserPathVariable() {
        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BaseUrl + usersUrl + "/{userId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham"))
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("-37.3159", json.get("address.geo.lat"));

    }

    @Test
    public void JSONPlaceholderReadUserWithQueryParams() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BaseUrl + usersUrl)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(200, response.statusCode());
        assertEquals("Leanne Graham", json.getList("name").get(0));
        assertEquals("Bret", json.getList("username").get(0));
        assertEquals("Sincere@april.biz", json.getList("email").get(0));
        assertEquals("Kulas Light", json.getList("address.street").get(0));
    }

    @Test
    public void JSONPlaceholderReadOneUser() {
        Response response = given()
                .when()
                .get(BaseUrl + usersUrl + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(200, response.statusCode());
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street"));

    }
}
