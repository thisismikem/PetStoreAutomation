package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//For CRUD - create, read, update, delete

public class EndPoint {
	
	static ResourceBundle endPointURLs = ResourceBundle.getBundle("routes");
	
	public static Response performPost(Object payload, String urlProperty) {
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(endPointURLs.getString(urlProperty));
		
		return response;
	}
	
	public static Response performGet(String urlProperty, String paraName, Object paraValue) {
		Response response = given()
			.pathParam(paraName, paraValue)
		.when()
			.get(endPointURLs.getString(urlProperty));
		
		return response;
	}
	
	public static Response performPut(Object payload, String urlProperty, Map<String, Object> paraMap) {
		
		RequestSpecification rs = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON);
		
		if (paraMap != null && paraMap.size() > 0)
			for (Entry<String, Object> entry : paraMap.entrySet())
				rs.pathParam(entry.getKey(), entry.getValue());
		
		Response response = rs
				.body(payload)
			.when()
				.put(endPointURLs.getString(urlProperty));
		
		return response;
	}
	
	public static Response performDelete(String urlProperty, String paraName, Object paraValue) {
		Response response = given()
			.pathParam(paraName, paraValue)
		.when()
			.delete(endPointURLs.getString(urlProperty));
		
		return response;
	}
}
