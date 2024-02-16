package api.test;

import io.restassured.RestAssured;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyTests {
	
	@Test
	public void testAsync() throws ExecutionException, InterruptedException {

		//sync
		Future<Response> responseFuture = Dsl.asyncHttpClient().prepareGet("https://reqres.in/api/users?delay=3").execute(); //wait for 3 seconds
		Response response = responseFuture.get();

		System.out.println(response);
		Assert.assertEquals(response.getStatusCode(), 200);

		System.out.println(".......................................finished.......................................");

		//async with listener
		//https://www.baeldung.com/async-http-client
	}

	@Test
	public void testProxy() {

		RestAssured.proxy("127.0.0.1", 8888);
		RestAssured.given().when().get();
	}

}
