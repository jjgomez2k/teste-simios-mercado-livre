package com.jjgomez2k.testesimios.controller;

import com.jjgomez2k.testesimios.api.TesteSimiosDnaController;
import com.mongodb.MongoWriteException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.jjgomez2k.testesimios.exception.CustomExceptionHandler;
import com.jjgomez2k.testesimios.model.StatsResponse;
import com.jjgomez2k.testesimios.service.implementation.DnaServiceImplementation;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TesteSimiosDnaControllerTest {
	@Mock
	private DnaServiceImplementation dnaService;

	@InjectMocks
	private TesteSimiosDnaController testeSimiosDnaController;
	@InjectMocks
	private CustomExceptionHandler customExceptionHandler;

	protected MockMvc mockMvc;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(customExceptionHandler, testeSimiosDnaController)
				.build();
	}

	@Test
	public void shouldValidateSimiosDnaWithDnaSimiosSuccessfully() throws Exception {
		Mockito.when(dnaService.isSimian(Mockito.any())).thenReturn(true);
		mockMvc.perform(post("/api/simian")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(getMockSimiosRequest()))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldValidateSimiosDnaWithDnaHumanSuccessfully() throws Exception {
		Mockito.when(dnaService.isSimian(Mockito.any())).thenReturn(false);
		mockMvc.perform(post("/api/simian")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(getMockSimiosRequest()))
				.andExpect(status().isForbidden());
	}


	@Test
	public void shouldReturnStatsSuccessfully() throws Exception {
		Mockito.when(dnaService.findDNAStats()).thenReturn(getMockStatsResponse());
		mockMvc.perform(get("/api/stats")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(getMockSimiosRequest()))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldThrowMongoWriteExceptionHandlerOnTryAlreadySavedDnaSequenceSuccessfully() throws Exception {
		Mockito.when(dnaService.isSimian(Mockito.any())).thenThrow(MongoWriteException.class);
		mockMvc.perform(post("/api/simian")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(getMockSimiosRequest()))
				.andExpect(status().isBadRequest());
	}

	private StatsResponse getMockStatsResponse(){
		return StatsResponse.builder()
				.count_human_dna(BigDecimal.valueOf(10))
				.count_mutant_dna(BigDecimal.valueOf(4))
				.ratio(BigDecimal.valueOf(0.4).setScale(2, RoundingMode.UP))
				.build();
	}

	private String getMockSimiosRequest() {
		return "{ \"dna\": [\"gaaa\", \"tgcc\", \"tgga\", \"ttag\"]}";
	}

}