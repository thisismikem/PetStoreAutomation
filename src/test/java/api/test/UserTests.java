package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("************* Creating user **************");
		
		Response response = UserEndPoint.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* User created **************");

	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("************* Reading user **************");

		Response response = UserEndPoint.readUser(userPayload.getUsername());
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
		
		Response response = UserEndPoint.updateUser(userPayload.getUsername(), userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseUpdated = UserEndPoint.readUser(userPayload.getUsername());
		
		Assert.assertEquals(responseUpdated.getStatusCode(), 200);
		Assert.assertEquals(responseUpdated.path("email"), userPayload.getEmail());
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		Response response = UserEndPoint.deleteUser(userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = UserEndPoint.readUser(userPayload.getUsername());
		
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
}
