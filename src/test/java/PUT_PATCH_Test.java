import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PUT_PATCH_Test {
    @Test
    public void updateUserPUT() {

        JSONObject user = new JSONObject();
        user.put("name","Bartek PUT");
        user.put("username","Bartek PUT");
        user.put("email","Bartek@april.put");
        user.put("phone","1-770-736-8031 x56442");
        user.put("website","hildegard.org");

        JSONObject geo = new JSONObject();
        geo.put("lat","-37.3159");
        geo.put("lng","81.1496");

        JSONObject address = new JSONObject();
        address.put("street","Kulas Light");
        address.put("suite","Apt. 556");
        address.put("city","Gwenborough");
        address.put("zipcode","92998-3874");
        address.put("geo", geo);

        user.put("address",address);

        JSONObject company = new JSONObject();
        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");

        user.put("company",company);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
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
//
//    @Test
//    public void userEmailUpdatePatch() {
//
//        String jsonBody = "{\"email\": \"Bartek@april.patch\"}";
//        Response response = given()
//                .contentType("application/json")
//                .body(jsonBody)
//                .when()
//                .patch("https://jsonplaceholder.typicode.com/users/1")
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        JsonPath json = response.jsonPath();
//
//        Assertions.assertEquals("Bartek@april.patch", json.get("email"));
//    }
}

