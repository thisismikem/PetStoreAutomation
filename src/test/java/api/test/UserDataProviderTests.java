package api.test;

import api.endpoints.EndPoint;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserDataProviderTests extends BaseTest {
	
	@Test(priority = 5, dataProvider = "UserData", dataProviderClass = DataProviders.class)
	public void testPostUser(User user) {
		
		logger.info("************* Creating users from Excel **************");

		Response response = EndPoint.performPost(user, "USER_CREATE_URL");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* Users created from Excel **************");
	}
	
	@Test(priority = 7, dataProvider = "UserData", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(User user) {
		
		Response response = EndPoint.performDelete("USER_DELETE_URL", "username", user.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = EndPoint.performGetByParamName("USER_GET_URL", "username", user.getUsername());
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
	
}



