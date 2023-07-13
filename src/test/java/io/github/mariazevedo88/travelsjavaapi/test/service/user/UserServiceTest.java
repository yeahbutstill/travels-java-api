package io.github.mariazevedo88.travelsjavaapi.test.service.user;

import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.repository.user.UserRepository;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class that implements tests of the UserService features.
 * 
 * @author Mariana Azevedo
 * @since 08/12/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class UserServiceTest {
	
	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repository;
	
	static final String EMAIL = "email@test.com";
	
	/**
	 * Method to test the creation of an User.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(1)
	void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(User.class)))
			.willReturn(getMockUser());
		User response = service.save(new User());
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the service that search for an User by the email.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(2)
	void testFindByEmail(){
		
		BDDMockito.given(repository.findByEmail(Mockito.anyString()))
			.willReturn(Optional.of(new User()));

		Optional<User> response = service.findByEmail(EMAIL);
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method that fill a mock of a User to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>User</code> object
	 */
	private User getMockUser() {
		return new User(1L, "Test User", "123", EMAIL, RoleEnum.ROLE_ADMIN);
	}
	
	/**
	 * Method to remove all User test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/10/2020
	 */
	@AfterAll
	void tearDown() {
		repository.deleteAll();
	}

}
