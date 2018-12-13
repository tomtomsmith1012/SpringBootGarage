package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.garage.SpringBootGarageApplication;
import com.springboot.garage.model.SpringBootGarageModel;
import com.springboot.garage.repository.SpringBootGarageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootGarageApplication.class })
@AutoConfigureMockMvc
public class IntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private SpringBootGarageRepository repository;

	@Before
	public void clearDB() {
		repository.deleteAll();
	}

	@Test
	public void findAndRetrieveTest() throws Exception {
		repository.save(new SpringBootGarageModel("Car", "a6", "Audi", "Green", "GH65 TSH"));
		mvc.perform(get("/api/vehicle").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].model", is("a6")));
	}

	public String convertVehicleToJson(SpringBootGarageModel vehicle) {
		ObjectMapper vehicleToJson = new ObjectMapper();
		try {
			 return vehicleToJson.writeValueAsString(vehicle);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Test
	public void addVehicleTest() throws Exception {
		SpringBootGarageModel testVehicle = new SpringBootGarageModel("Car", "TT", "Audi", "White", "SN12 YAF");
		mvc.perform(MockMvcRequestBuilders.post("/api/vehicle").contentType(MediaType.APPLICATION_JSON)
				.content(convertVehicleToJson(testVehicle))).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.model", is("TT")));
	}
	
	@Test
	public void deletePersonFromDatabaseTest() throws Exception {
		SpringBootGarageModel testModel = new SpringBootGarageModel("Test", "blah", "Test", "Blah", "Test");
		repository.save(testModel);
		mvc.perform(MockMvcRequestBuilders.delete("/api/vehicle/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/api/vehicle/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void modifyPersonFromDatabaseTest() throws Exception {
		SpringBootGarageModel testModel = new SpringBootGarageModel("Test", "Blah", "Test", "Blah", "Blah");
		repository.save(testModel);
		mvc.perform(MockMvcRequestBuilders.put("/api/vehicle/" + testModel.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(convertVehicleToJson(testModel)))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))	
			.andExpect(jsonPath("$.model", is("Blah")));
	}
}
