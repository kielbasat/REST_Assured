package STBLessons;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class POST_Test {
    @Test
    public void createNewUser() {
        String jsonBody = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Bartek Graham\",\n" +
                "    \"username\": \"Bartek\",\n" +
                "    \"email\": \"Bartek@april.biz\",\n" +
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
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("Bartek Graham", json.get("name"));
        assertEquals("Bartek", json.get("username"));
        assertEquals("Bartek@april.biz", json.get("email"));

    }
}
