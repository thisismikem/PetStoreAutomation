package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//For CRUD - create, read, update, delete

public class UserEndPoint {
	
	static ResourceBundle endPointURLs = ResourceBundle.getBundle("routes");
	
	public static Response createUser(User payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(endPointURLs.getString("USER_CREATE_URL"));
		
		return response;
	}
	
	public static Response readUser(String userName) {
		Response response = given()
			.pathParam("username", userName)
		.when()
			.get(endPointURLs.getString("USER_GET_URL"));
		
		return response;
	}
	
	public static Response updateUser(String userName, User payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(payload)
		.when()
			.put(endPointURLs.getString("USER_UPDATE_URL"));
		
		return response;
	}
	
	public static Response deleteUser(String userName) {
		Response response = given()
			.pathParam("username", userName)
		.when()
			.delete(endPointURLs.getString("USER_DELETE_URL"));
		
		return response;
	}
}
