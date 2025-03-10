package io.github.mariazevedo88.travelsjavaapi.test.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mariazevedo88.travelsjavaapi.dto.model.user.UserDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.RoleEnum;
import io.github.mariazevedo88.travelsjavaapi.model.user.User;
import io.github.mariazevedo88.travelsjavaapi.service.user.UserService;
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
 * Class that implements tests of the UserController features
 * 
 * @author Mariana Azevedo
 * @since 13/12/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class UserControllerTest {

	static final String URL = "/api-travels/v1/users";
	static final Long ID = 1L;
	static final String NAME = "Test User";
	static final String PASSWORD = "123";
	static final String EMAIL = "email@test.com";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@BeforeAll
	void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an Account in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 13/12/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	void testSave() throws Exception {
		
		BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload())
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.role").value(RoleEnum.ROLE_ADMIN.getValue()));
		
	}
	
	/**
	 * Method that fill a mock User object to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/12/2020
	 * 
	 * @return <code>User</code> object
	 * @throws ParseException 
	 */
	private User getMockUser() throws ParseException {
		return new User(1L, NAME, PASSWORD, EMAIL, RoleEnum.ROLE_ADMIN);
	}
	
	/**
	 * Method that fill a mock UserDTO object to use as return in the tests.
	 *
	 * @return <code>String</code> with the UserDTO payload
	 * @throws JsonProcessingException
	 * @author Mariana Azevedo
	 * @since 13/12/2020
	 */
	private String getJsonPayload() throws JsonProcessingException {
		
		UserDTO dto = new UserDTO(UserControllerTest.ID, UserControllerTest.NAME, UserControllerTest.PASSWORD, UserControllerTest.EMAIL, RoleEnum.ROLE_ADMIN.getValue());
		return new ObjectMapper().writeValueAsString(dto);
	}
}
