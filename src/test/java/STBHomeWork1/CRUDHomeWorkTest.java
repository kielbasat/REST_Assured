package STBHomeWork1;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CRUDHomeWorkTest {

    private String basePostsUrl;
    private String baseUsersUrl;
    private String fakePostTitle;
    private String fakePostBody;
    private JSONObject post;
    private Faker faker;

    @BeforeAll
    public static void beforeAll() {
    }

    @BeforeEach
    public void beforeEach() {
        basePostsUrl = "https://jsonplaceholder.typicode.com/posts/";
        baseUsersUrl = "https://jsonplaceholder.typicode.com/users/";
        faker = new Faker();
        fakePostTitle = faker.lorem().word();
        fakePostBody = faker.lorem().sentence();
        post = new JSONObject();
    }

    @Test
    public void readPosts() {

        Response response = given()
                .when()
                .get(basePostsUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> posts = json.getList("userId");
        assertEquals(100, posts.size());
    }

    @Test
    public void addOneUser() {

        post.put("userId", 1);
        post.put("title", fakePostTitle);
        post.put("body", fakePostBody);

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .post(basePostsUrl)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(101, (Integer) json.get("id"));
        assertEquals(fakePostTitle, json.get("title"));
        assertEquals(fakePostBody, json.get("body"));
    }

    @Test
    public void updateWholePost() {

        post.put("userId", 1);
        post.put("id", 1);
        post.put("title", fakePostTitle);
        post.put("body", fakePostBody);

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .put(basePostsUrl + "1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(1, (Integer) json.get("id"));
        assertEquals(fakePostTitle, json.get("title"));
        assertEquals(fakePostBody, json.get("body"));
    }

    @Test
    public void updatePostBody() {

        post.put("body", fakePostBody);

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .patch(basePostsUrl + "1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakePostBody, json.get("body"));
    }

    @Test
    public void deleteSelectedPost() {
        Response response = given()
                .when()
                .delete(basePostsUrl + "1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }

    @Test
    public void streamEmailDoesNotHavePlDomain() {
        Response response = given()
                .when()
                .get(baseUsersUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emailList = json.getList("email");
        boolean emailHasPolishDomain = emailList.stream()
                .anyMatch(email -> email.endsWith(".pl"));
        assertFalse(emailHasPolishDomain);
    }

    @Test
    public void forLoopEmailDoesNotHavePlDomain() {
        Response response = given()
                .when()
                .get(baseUsersUrl)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emailList = json.getList("email");


        for (int i = 0; i < emailList.size(); i++) {

            String selectedEmail = emailList.get(i);

            if (selectedEmail.endsWith("pl")) {
                assertTrue(selectedEmail.isEmpty());
            } else {
                assertFalse(selectedEmail.isEmpty());
            }
        }
    }
}