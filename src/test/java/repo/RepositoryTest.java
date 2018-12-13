package repo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.garage.SpringBootGarageApplication;
import com.springboot.garage.model.SpringBootGarageModel;
import com.springboot.garage.repository.SpringBootGarageRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootGarageApplication.class})
@DataJpaTest
public class RepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SpringBootGarageRepository myRepo;
	
	@Test
	public void retrieveByIdTest() {
		SpringBootGarageModel model1 = new SpringBootGarageModel("Car", "Civic", "Honda", "Pink", "TC23 SUX");
		entityManager.persist(model1);
		entityManager.flush();
		assertTrue(myRepo.findById(model1.getId()).isPresent());
	}
}
