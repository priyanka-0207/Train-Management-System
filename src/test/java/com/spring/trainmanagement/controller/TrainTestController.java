package com.spring.trainmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.trainmanagement.entity.Station;
import com.spring.trainmanagement.entity.Train;
import com.spring.trainmanagement.service.TrainService;

@WebMvcTest(TrainController.class)
public class TrainTestController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TrainService trainService;

	@Test
	void testAddTrain() throws Exception {
		Train trainRequest = new Train();
		trainRequest.setTrainNumber("123");
		trainRequest.setName("Test Train");
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("Station 1"));
		trainRequest.setStations(stations);

		when(trainService.addTrain(any(Train.class))).thenReturn(trainRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/trains/add").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(trainRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	void testDeleteTrain() throws Exception {
		Train trainRequest = new Train();
		trainRequest.setTrainNumber("123");

		when(trainService.deleteTrain(trainRequest.getTrainNumber())).thenReturn("Train successfully deleted.");

		mockMvc.perform(MockMvcRequestBuilders.delete("/trains/delete").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(trainRequest)));

	}

//	@Test
//	void testUpdateTrain() throws Exception {
//
//		TrainUpdateRequest updateRequest = new TrainUpdateRequest();
//		updateRequest.setNumber("123");
//
//		TrainUpdateResponse updateResponse = new TrainUpdateResponse(true, "Train details updated successfully.");
//
//		when(trainService.updateTrainDetails(updateRequest.getNumber(), updateRequest)).thenReturn(updateResponse);
//
//		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/trains/updateTrain")
//				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateRequest)))
//				.andReturn();
//
//		MockHttpServletResponse response = mvcResult.getResponse();
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//		String content = response.getContentAsString();
//		assertNotNull(content);
//
//		TrainUpdateResponse responseObject = new ObjectMapper().readValue(content, TrainUpdateResponse.class);
//		assertTrue(responseObject.isSuccess());
//		assertEquals("Train details updated successfully.", responseObject.getMessage());
//	}

	@Test
	void testFindTrainsBySourceAndDestination() throws Exception {
		List<Train> trains = new ArrayList<>();
		Train train = new Train();
		train.setTrainNumber("20663");
		trains.add(train);

		when(trainService.findTrainsBySourceAndDestination("Source", "Destination")).thenReturn(trains);

		mockMvc.perform(MockMvcRequestBuilders.get("/trains/find").param("source", "Source").param("destination",
				"Destination")).andExpect(MockMvcResultMatchers.status().isOk());

	}
}
