package api.test;

import api.endpoints.EndPoint;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class UserTests extends BaseTest {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
		super.setupData();

		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("************* Creating user **************");
		
		Response response = EndPoint.performPost(userPayload, "USER_CREATE_URL");
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* User created **************");

	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("************* Reading user **************");

		Response response = EndPoint.performGetByParamName("USER_GET_URL", "username", userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.path("email"), userPayload.getEmail());

		logger.info("************* User is read **************");

	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		faker = new Faker();
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		Response response = EndPoint.performPut(userPayload, "USER_UPDATE_URL", Map.of("username", userPayload.getUsername()));
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseUpdated = EndPoint.performGetByParamName("USER_GET_URL", "username", userPayload.getUsername());
		
		Assert.assertEquals(responseUpdated.getStatusCode(), 200);
		Assert.assertEquals(responseUpdated.path("email"), userPayload.getEmail());
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		Response response = EndPoint.performDelete("USER_DELETE_URL", "username", userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = EndPoint.performGetByParamName("USER_GET_URL", "username", userPayload.getUsername());
		
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
}
