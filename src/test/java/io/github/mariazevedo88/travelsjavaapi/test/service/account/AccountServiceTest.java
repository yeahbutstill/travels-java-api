package io.github.mariazevedo88.travelsjavaapi.test.service.account;

import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.repository.account.AccountRepository;
import io.github.mariazevedo88.travelsjavaapi.service.account.AccountService;
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
 * Class that implements tests of the AccountService features.
 * 
 * @author Mariana Azevedo
 * @since 08/12/2020
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class AccountServiceTest {
	
	@Autowired
	private AccountService service;

	@MockBean
	private AccountRepository repository;
	
	static final String ACCOUNT_NUMBER = "123456";
	
	/**
	 * Method to test the creation of an Account.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(1)
	void testSave() {
		
		BDDMockito.given(repository.save(Mockito.any(Account.class)))
			.willReturn(getMockAccount());
		Account response = service.save(new Account());
		
		assertNotNull(response);
	}
	
	/**
	 * Method that test the service that search for an Account by the account
	 * number.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 */
	@Test
	@Order(2)
	void testFindByAccountNumber(){
		
		BDDMockito.given(repository.findByAccountNumber(Mockito.anyString()))
			.willReturn(Optional.of(new Account()));

		Optional<Account> response = service.findByAccountNumber(ACCOUNT_NUMBER);
		assertFalse(response.isEmpty());
	}
	
	/**
	 * Method to remove all Account test data.
	 * 
	 * @author Mariana Azevedo
	 * @since 17/12/2020
	 */
	@AfterAll
	void tearDown() {
		repository.deleteAll();
	}
	
	/**
	 * Method that fill a mock of a Account to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/12/2020
	 * 
	 * @return <code>Account</code> object
	 */
	private Account getMockAccount() {
		return new Account(1L, ACCOUNT_NUMBER, AccountTypeEnum.BASIC);
	}

}
