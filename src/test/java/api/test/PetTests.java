package api.test;

import api.endpoints.EndPoint;
import api.payload.Pet;
import api.payload.Pet.Category;
import api.payload.Pet.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.gson.reflect.TypeToken;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetTests extends BaseTest {
	
	Faker faker;
	Pet petPayload;
	
	@BeforeClass
	public void setupData() {
		super.setupData();

		faker = new Faker();
		petPayload = new Pet();
		
		petPayload.setId(1111);
		
		petPayload.setName("hello bird");
		petPayload.setCategory(new Category(222, "bird"));
		petPayload.setPhotoUrls(new ArrayList<String>(Arrays.asList("https://www.allaboutbirds.org/guide/assets/photo/303441381-1280px.jpg")));
		petPayload.setTags(new ArrayList<Tag>(Arrays.asList(new Tag(333, "robin"))));
		petPayload.setStatus("available");
	}
	
	@Test(priority = 1)
	public void testPostPet() {
		
		logger.info("************* Creating Pet **************");
		
		Response response = EndPoint.performPost(petPayload, "PET_CREATE_URL");
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//test response payload object
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = response.body().asString();

	    try {
			Pet petResponsePayload = mapper.readValue(jsonStr, Pet.class);
			Assert.assertEquals(petPayload, petResponsePayload);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("************* Pet created **************");

	}
	
	@Test(priority = 2)
	public void testGetPetByID() {
		logger.info("************* Reading Pet **************");

		Response response = EndPoint.performGetByParamName("PET_GET_URL", "petId", 9527);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		System.out.println(response.body());
		Pet pet = response.as(Pet.class);

		Assert.assertEquals(response.path("status"), "sold");
		Assert.assertEquals(pet.getName(), "fish");

		JsonPath jPath = response.jsonPath();
		Assert.assertEquals(jPath.getString("tags[1].name"), "pig burger");
		Assert.assertEquals(jPath.getInt("tags.size()"), 2);

		logger.info("************* Pet is read **************");
	}

	@Test(priority = 2)
	public void testGetPetByStatus() {
		logger.info("************* Reading All Pets **************");

		Response response = EndPoint.performGetByQueryName("PET_FIND_BY_STATUS", "status", "available");
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
//		System.out.println(response.body());

		Type type = new TypeToken<List<Pet>>() {}.getType();
		List<Pet> pets = response.as(type);
		System.out.println("pets.size() = " + pets.size());

		Assert.assertTrue(pets.size() > 1);

		logger.info("************* Pets are read **************");
	}
	
	@Test(priority = 3)
	public void testUpdatePetById() {
		faker = new Faker();
		petPayload.setName("jiji");
		petPayload.setStatus("coming");
		
		Response response = EndPoint.performPut(petPayload, "PET_UPDATE_URL", null);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseUpdated = EndPoint.performGetByParamName("PET_GET_URL", "petId", petPayload.getId());
		
		Assert.assertEquals(responseUpdated.getStatusCode(), 200);
		Assert.assertEquals(responseUpdated.path("status"), petPayload.getStatus());
	}
	
	@Test(priority = 4)
	public void testDeletePet() {
		Response response = EndPoint.performDelete("PET_DELETE_URL", "petId", petPayload.getId());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseDeleted = EndPoint.performGetByParamName("PET_GET_URL", "petId", petPayload.getId());
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
}
