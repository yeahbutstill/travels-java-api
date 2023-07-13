package io.github.mariazevedo88.travelsjavaapi.test.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserAccountDTO;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.model.user.UserAccount;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserAccountService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.ParseException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class that implements tests of the UserAccountController features
 * 
 * @author Mariana Azevedo
 * @since 15/12/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class UserAccountControllerTest {

	static final String URL = "/api-travels/v1/users-accounts";
	static final Long ID = 1L;
	static final Long USER_ID = 1L;
	static final Long ACCOUNT_ID = 1L;
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserAccountService userAccountService;
	
	@BeforeAll
	void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an UserAccount in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 15/12/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	void testSave() throws Exception {
		
		BDDMockito.given(userAccountService.save(Mockito.any(UserAccount.class))).willReturn(getMockUserAccount());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.userId").value(USER_ID))
		.andExpect(jsonPath("$.data.accountId").value(ACCOUNT_ID));
		
	}
	
	/**
	 * Method that fill a mock UserAccount to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 15/12/2020
	 * 
	 * @return <code>UserAccount</code> object
	 * @throws ParseException 
	 */
	private UserAccount getMockUserAccount() throws ParseException {
		return new UserAccount(1L, new User(USER_ID), new Account(ACCOUNT_ID));
	}
	
	/**
	 * Method that fill a mock UserAccountDTO to use as return in the tests.
	 *
	 * @return <code>String</code> with the UserAccountDTO payload
	 * @throws JsonProcessingException
	 * @author Mariana Azevedo
	 * @since 15/12/2020
	 */
	private String getJsonPayload() throws JsonProcessingException {
		
		UserAccountDTO dto = new UserAccountDTO(UserAccountControllerTest.ID, UserAccountControllerTest.USER_ID, UserAccountControllerTest.ACCOUNT_ID);
		return new ObjectMapper().writeValueAsString(dto);
	}
}
