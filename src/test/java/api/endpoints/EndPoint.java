package api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

//For CRUD - create, read, update, delete

public class EndPoint {
	
	public static ResourceBundle endPointURLs = ResourceBundle.getBundle("routes");

	public static Response performPost(Object payload, String urlProperty) {
		basePath = endPointURLs.getString(urlProperty);

		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post();
		
		return response;
	}
	
	public static Response performGetByParamName(String urlProperty, String pathName, Object pathValue) {
		basePath = endPointURLs.getString(urlProperty);

		RequestSpecification rs = given();

		if (pathName != null)
			rs.pathParam(pathName, pathValue);

		Response response = rs
		.when()
			.get();
		
		return response;
	}

	public static Response performGetByQueryName(String urlProperty, String queryName, Object queryValue) {
		basePath = endPointURLs.getString(urlProperty);

		RequestSpecification rs = given();

		if (queryName != null)
			rs.queryParam(queryName, queryValue);

		Response response = rs
				.when()
				.get();

		return response;
	}

	public static Response performPut(Object payload, String urlProperty, Map<String, Object> paraMap) {
		basePath = endPointURLs.getString(urlProperty);

		RequestSpecification rs = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON);
		
		if (paraMap != null && paraMap.size() > 0)
			for (Entry<String, Object> entry : paraMap.entrySet())
				rs.pathParam(entry.getKey(), entry.getValue());
		
		Response response = rs
				.body(payload)
			.when()
				.put();
		
		return response;
	}
	
	public static Response performDelete(String urlProperty, String paraName, Object paraValue) {
		basePath = endPointURLs.getString(urlProperty);

		Response response = given()
			.pathParam(paraName, paraValue)
		.when()
			.delete();
		
		return response;
	}
}
