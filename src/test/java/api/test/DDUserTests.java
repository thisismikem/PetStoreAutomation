package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.utilities.DataProviders;
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
	
	@Test(priority = 5, dataProvider = "UserData", dataProviderClass = DataProviders.class)
	public void testPostUser(User user) {
		
		logger.info("************* Creating users from Excel **************");

		Response response = UserEndPoint.createUser(user);
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* Users created from Excel **************");
	}
	
	@Test(priority = 7, dataProvider = "UserData", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(User user) {
		
		Response response = UserEndPoint.deleteUser(user.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = UserEndPoint.readUser(user.getUsername());
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
	
}



