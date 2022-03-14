import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PUT_PATCH_Test {
    @Test
    public void updateUserPUT() {
        String jsonBody = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Bartek PUT\",\n" +
                "    \"username\": \"Bartek PUT\",\n" +
                "    \"email\": \"Bartek@april.put\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Kulas Light\",\n" +
                "        \"suite\": \"Apt. 556\",\n" +
                "        \"city\": \"Gwenborough\",\n" +
                "        \"zipcode\": \"92998-3874\",\n" +
                "        \"geo\": {\n" +
                "            \"lat\": \"-37.3159\",\n" +
                "            \"lng\": \"81.1496\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "        \"name\": \"Romaguera-Crona\",\n" +
                "        \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "        \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "}";

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek PUT", json.get("name"));
        Assertions.assertEquals("Bartek PUT", json.get("username"));
        Assertions.assertEquals("Bartek@april.put", json.get("email"));

    }

    @Test
    public void userEmailUpdatePatch() {

        String jsonBody = "{\"email\": \"Bartek@april.patch\"}";
        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals("Bartek@april.patch", json.get("email"));
    }
}

