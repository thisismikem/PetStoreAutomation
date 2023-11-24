package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//For CRUD - create, read, update, delete

public class PetEndPoint {
	
	static ResourceBundle endPointURLs = ResourceBundle.getBundle("routes");
	
	public static Response createPet(Pet payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(endPointURLs.getString("PET_CREATE_URL"));
		
		return response;
	}
	
	public static Response readPet(int petId) {
		Response response = given()
			.pathParam("petId", petId)
		.when()
			.get(endPointURLs.getString("PET_GET_URL"));
		
		return response;
	}
	
	public static Response updatePet(Pet payload) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.put(endPointURLs.getString("PET_UPDATE_URL"));
		
		return response;
	}
	
	public static Response deletePet(int petId) {
		Response response = given()
			.pathParam("petId", petId)
		.when()
			.delete(endPointURLs.getString("PET_DELETE_URL"));
		
		return response;
	}
}
