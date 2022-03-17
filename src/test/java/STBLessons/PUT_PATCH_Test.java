package STBLessons;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PUT_PATCH_Test {

    private static Faker faker;
    private String fakeEmail;
    private String fakeName;
    private String fakeUsername;
    private String fakePhone;
    private String fakeWebsite;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }
    @BeforeEach
    public void beforeEach(){
        fakeEmail = faker.internet().emailAddress();
        fakeName = faker.name().name();
        fakeUsername = faker.name().username();
        fakePhone = faker.phoneNumber().phoneNumber();
        fakeWebsite = faker.internet().url();
    }

    @Test
    public void updateUserPUT() {

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUsername);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWebsite);

        JSONObject geo = new JSONObject();
        geo.put("lat", "-37.3159");
        geo.put("lng", "81.1496");

        JSONObject address = new JSONObject();
        address.put("street", "Kulas Light");
        address.put("suite", "Apt. 556");
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");
        address.put("geo", geo);

        user.put("address", address);

        JSONObject company = new JSONObject();
        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");

        user.put("company", company);

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

        Assertions.assertEquals(fakeName, json.get("name"));
        Assertions.assertEquals(fakeUsername, json.get("username"));
        Assertions.assertEquals(fakeEmail, json.get("email"));

    }

    @Test
    public void userEmailUpdatePatch() {
        Faker faker = new Faker();
        String fakeEmail = faker.internet().emailAddress();

        JSONObject userDetails = new JSONObject();
        userDetails.put("email", fakeEmail);

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(fakeEmail, json.get("email"));
    }
}

