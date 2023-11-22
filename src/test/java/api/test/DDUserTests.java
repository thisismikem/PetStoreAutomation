package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.utilities.DataProviders;
import api.utilities.UserDataProvider;
import api.endpoints.UserEndPoint;
import api.payload.User;
import io.restassured.response.Response;


public class DDUserTests {
	
	public Logger logger;

	@BeforeClass
	public void setupData() {
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String userName, String firstName, String lastName, String email, String password, String phone) {
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		Response response = UserEndPoint.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String userName) {
		
		Response response = UserEndPoint.deleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = UserEndPoint.readUser(userName);
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
	
	@Test(priority = 5, dataProvider = "AllData", dataProviderClass = UserDataProvider.class)
	public void testPostUserNew(User user) {
		
		logger.info("************* Creating users from Excel **************");

		Response response = UserEndPoint.createUser(user);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* Users created from Excel **************");

	}
	
	@Test(priority = 7, dataProvider = "AllData", dataProviderClass = UserDataProvider.class)
	public void testDeleteUserByNameNew(User user) {
		
		Response response = UserEndPoint.deleteUser(user.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = UserEndPoint.readUser(user.getUsername());
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
	
}



