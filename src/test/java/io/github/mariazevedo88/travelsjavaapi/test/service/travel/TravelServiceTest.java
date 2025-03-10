package io.github.mariazevedo88.travelsjavaapi.test.service.travel;

import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import io.github.mariazevedo88.travelsjavaapi.repository.travel.TravelRepository;
import io.github.mariazevedo88.travelsjavaapi.service.travel.TravelService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that implements tests of the TravelService features.
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class TravelServiceTest {
	
	@Autowired
	private TravelService service;

	@MockBean
	private TravelRepository repository;
	
	private static final LocalDateTime DATE = LocalDateTime.now();
	
	/**
	 * Method to setup a Travel to use in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@Test
	@Order(1)
	void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Travel.class)))
			.willReturn(getMockTravel());
		Travel response = service.save(new Travel());
		
		assertNotNull(response);
		assertEquals("123456", response.getOrderNumber());
	}
	
	/**
	 * Method that test the service that search for an object Travel by the order number.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	@Test
	@Order(2)
	void testFindByOrderNumber() {
		
		BDDMockito.given(repository.findByOrderNumber(Mockito.anyString()))
			.willReturn(Optional.of(new Travel()));
		
		Optional<Travel> response = service.findByOrderNumber("123456");
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method that test the service that search for travels in a period of time.
	 * 
	 * @author Mariana Azevedo
	 * @since 21/08/2020
	 */
	@Test
	@Order(3)
	void testFindBetweenDates() {
		
		List<Travel> transactions = new ArrayList<>();
		transactions.add(getMockTravel());
		Page<Travel> page = new PageImpl<>(transactions);
		
		BDDMockito.given(repository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqual(Mockito.any(LocalDateTime.class), 
				Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).willReturn(page);
		
		Page<Travel> response = service.findBetweenDates(DATE, DATE, PageRequest.of(1, 10, Sort.by("id").ascending()));
		assertNotNull(response);
	}
	
	/**
	 * Method that fill a mock of a Travel to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 24/03/2020
	 * 
	 * @return <code>Travel</code> object
	 */
	private Travel getMockTravel() {

		return new Travel(1L, "123456", DATE,
				null, BigDecimal.valueOf(288), TravelTypeEnum.ONE_WAY,
				new Account(1L, "635241", AccountTypeEnum.BASIC));
	}
	
	/**
	 * Method to remove all Travel test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 */
	@AfterAll
	void tearDown() {
		repository.deleteAll();
	}

}
