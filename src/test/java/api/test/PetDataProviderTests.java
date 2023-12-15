package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.utilities.DataProviders;
import api.endpoints.EndPoint;
import api.payload.Pet;
import io.restassured.response.Response;


public class PetDataProviderTests {
	
	public Logger logger;

	@BeforeClass
	public void setupData() {
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 5, dataProvider = "PetData", dataProviderClass = DataProviders.class)
	public void testPostPet(Pet pet) {
		
		logger.info("************* Creating pets from Excel **************");

		Response response = EndPoint.performPost(pet, "PET_CREATE_URL");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//test response payload object
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = response.body().asString();

	    try {
			Pet petResponsePayload = mapper.readValue(jsonStr, Pet.class);
			Assert.assertEquals(pet, petResponsePayload);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    
		logger.info("************* Pets created from Excel **************");
	}
	
	@Test(priority = 7, dataProvider = "PetData", dataProviderClass = DataProviders.class)
	public void testDeletePetById(Pet pet) {
		
		Response response = EndPoint.performDelete("PET_DELETE_URL", "petId", pet.getId());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = EndPoint.performGet("PET_GET_URL", "petId", pet.getId());
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
	
	@Test(priority = 8, dataProvider = "PetDataJson", dataProviderClass = DataProviders.class)
	public void testPostPetJson(Pet pet) {
		
		logger.info("************* Creating pets from Json **************");

		Response response = EndPoint.performPost(pet, "PET_CREATE_URL");
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************* Pets created from Json **************");
	}
}



