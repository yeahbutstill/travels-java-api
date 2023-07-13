package io.github.mariazevedo88.travelsjavaapi.test.repository.statistic;

import io.github.mariazevedo88.travelsjavaapi.model.statistic.Statistic;
import io.github.mariazevedo88.travelsjavaapi.repository.statistic.StatisticRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class that implements tests of the StatisticRepository features
 * 
 * @author Mariana Azevedo
 * @since 24/03/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
class StatisticRepositoryTest {
	
	@Autowired
	StatisticRepository repository;
	
	Statistic statistic;
	
	/**
	 * Method that test the repository that save a Statistic in the API.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@Test
	void testSave() {
		
		statistic = new Statistic(null, new BigDecimal("200"), new BigDecimal("100"),
				new BigDecimal("100"), new BigDecimal("100"), 2);
		
		Statistic response = repository.save(statistic);
		
		assertNotNull(response);
		
		assertEquals(statistic.getSum(), response.getSum());
		assertEquals(statistic.getMin(), response.getMin());
		assertEquals(statistic.getMax(), response.getMax());
		assertEquals(statistic.getAvg(), response.getAvg());
		assertEquals(statistic.getCount(), response.getCount());
	}
	
	/**
	 * Method to remove all Statistic test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 */
	@AfterAll
	void tearDown() {
		repository.delete(statistic);
	}

}
