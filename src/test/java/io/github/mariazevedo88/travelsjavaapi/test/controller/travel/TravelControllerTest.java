package io.github.mariazevedo88.travelsjavaapi.test.controller.travel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.mariazevedo88.travelsjavaapi.dto.model.travel.TravelDTO;
import io.github.mariazevedo88.travelsjavaapi.enumeration.AccountTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.enumeration.TravelTypeEnum;
import io.github.mariazevedo88.travelsjavaapi.model.account.Account;
import io.github.mariazevedo88.travelsjavaapi.model.travel.Travel;
import io.github.mariazevedo88.travelsjavaapi.service.travel.TravelService;
import io.github.mariazevedo88.travelsjavaapi.util.TravelsApiUtil;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class that implements tests of the TransactionController features
 * 
 * @author Mariana Azevedo
 * @since 05/04/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
class TravelControllerTest {
	
	static final Long ID = 1L;
	static final Long ACCOUNT_ID = 1L;
	static final String ORDER_NUMBER = "654789";
	static final String START_DATE = "2020-08-15T18:32:04.150";
	static final String END_DATE = "2020-08-21T18:32:04.150";
	static final TravelTypeEnum TYPE = TravelTypeEnum.RETURN;
	static final BigDecimal VALUE = BigDecimal.valueOf(288);
	static final String URL = "/api-travels/v1/travels";
	
	HttpHeaders headers;

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TravelService travelService;
	
	@BeforeAll
	void setUp() {
		headers = new HttpHeaders();
        headers.set("X-api-key", "FX001-ZBSY6YSLP");
	}
	
	/**
	 * Method that tests to save an object Travel in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	void testSave() throws Exception {
		
		BDDMockito.given(travelService.save(Mockito.any(Travel.class))).willReturn(getMockTravel());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ORDER_NUMBER,
			TravelsApiUtil.getLocalDateTimeFromString(START_DATE.concat("Z")), 
			TravelsApiUtil.getLocalDateTimeFromString(END_DATE.concat("Z")),
								TYPE.getValue()))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
			.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.orderNumber").value(ORDER_NUMBER))
		.andExpect(jsonPath("$.data.startDate").value(START_DATE))
		.andExpect(jsonPath("$.data.endDate").value(END_DATE))
		.andExpect(jsonPath("$.data.amount").value(VALUE))
		.andExpect(jsonPath("$.data.type").value(TYPE.toString()));
	}
	
	/**
	 * Method that try to save an invalid object Travel in the API
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	void testSaveInvalidTravel() throws Exception {
		
		BDDMockito.given(travelService.save(Mockito.any(Travel.class))).willReturn(getMockTravel());
		
		mockMvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(null,
				TravelsApiUtil.getLocalDateTimeFromString(START_DATE.concat("Z")), TravelsApiUtil.
				 getLocalDateTimeFromString(END_DATE.concat("Z")), TYPE.getValue()
						))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.headers(headers))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.details").value("Order Number cannot be null"));
	}
	
	/**
	 * Method that fill a mock Travel object to use as return in the tests.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 * 
	 * @return <code>Travel</code> object
	 * @throws ParseException 
	 */
	private Travel getMockTravel() throws ParseException {

		return new Travel(ID, ORDER_NUMBER,
				TravelsApiUtil.getLocalDateTimeFromString(START_DATE.concat("Z")),
				TravelsApiUtil.getLocalDateTimeFromString(END_DATE.concat("Z")),
				VALUE, TYPE, new Account(1L, "123456", AccountTypeEnum.BASIC));
	}
	
	/**
	 * Method that fill a mock TravelDTO object to use as return in the tests.
	 *
	 * @param type
	 * @return <code>String</code> with the TravelDTO payload
	 * @throws JsonProcessingException
	 * @author Mariana Azevedo
	 * @since 05/04/2020
	 */
	private String getJsonPayload(String orderNumber, LocalDateTime startDate, LocalDateTime endDate,
								  String type) throws JsonProcessingException {
		
		TravelDTO dto = new TravelDTO(TravelControllerTest.ID, orderNumber, startDate, endDate,
				TravelControllerTest.VALUE, type, TravelControllerTest.ACCOUNT_ID);
	        
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper.writeValueAsString(dto);
	}
	
	@AfterAll
	void tearDown() {
		travelService.deleteById(1L);
	}

}
