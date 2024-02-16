package api.test;

import api.endpoints.EndPoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.baseURI;

public class BaseTest {

	public Logger logger;

	@BeforeClass
	public void setupData() {
		logger = LogManager.getLogger(this.getClass());
		baseURI = EndPoint.endPointURLs.getString("BASE_URL");
	}

}
