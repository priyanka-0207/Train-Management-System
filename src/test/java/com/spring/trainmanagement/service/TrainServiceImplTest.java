package com.spring.trainmanagement.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.trainmanagement.controller.TrainController;
import com.spring.trainmanagement.dao.TrainDao;
import com.spring.trainmanagement.dto.TrainUpdateRequest;
import com.spring.trainmanagement.dto.TrainUpdateResponse;
import com.spring.trainmanagement.entity.Station;
import com.spring.trainmanagement.entity.Train;

public class TrainServiceImplTest {

	@Mock
	private TrainDao trainDao;

	@Mock
	private TrainController trainController;

	@InjectMocks
	private TrainServiceImpl trainService;

	public TrainServiceImplTest() {
		MockitoAnnotations.openMocks(this); 
	}

	@Test
	void testAddTrain_ValidData_Success() {

		Train train = new Train();
		train.setName("Test Train");
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("Station A"));
		train.setStations(stations);

		when(trainDao.save(any(Train.class))).thenReturn(train);

		Train addedTrain = trainService.addTrain(train);

		assertNotNull(addedTrain);
		assertEquals("Test Train", addedTrain.getName());
		assertEquals(1, addedTrain.getStations().size());
		verify(trainDao, times(1)).save(any(Train.class));
	}

	@Test
	void testAddTrain_NullData_ExceptionThrown() {

		Train train = null;

		assertThrows(IllegalArgumentException.class, () -> trainService.addTrain(train));
		verifyNoInteractions(trainDao);
	}

	@Test
	void testAddTrain_EmptyStationList_ExceptionThrown() {

		Train train = new Train();
		train.setName("Test Train");
		train.setStations(new ArrayList<>());

		when(trainDao.save(any(Train.class))).thenReturn(null);

		assertThrows(RuntimeException.class, () -> trainService.addTrain(train));
		verify(trainDao, times(1)).save(any(Train.class));
	}

	@Test
	void testAddTrain_InvalidStationData_ExceptionThrown() {

		Train train = new Train();
		train.setName("Test Train");
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("null"));
		train.setStations(stations);

		when(trainDao.save(any(Train.class))).thenReturn(null);

		assertThrows(RuntimeException.class, () -> trainService.addTrain(train));
		verify(trainDao, times(1)).save(any(Train.class));
	}

	@Test
	void testAddTrain_VerifySavedCorrectly() {

		Train train = new Train();
		train.setName("Test Train");
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("Station A"));
		train.setStations(stations);

		when(trainDao.save(any(Train.class))).thenReturn(train);

		Train addedTrain = trainService.addTrain(train);

		assertNotNull(addedTrain);
		assertSame(train, addedTrain);
		verify(trainDao, times(1)).save(any(Train.class));
	}

	@Test
	void testDeleteTrain_ExistingTrain_Success() {

		Train train = new Train();
		train.setTrainNumber("123");
		when(trainDao.findByTrainNumber("123")).thenReturn(Optional.of(train));

		String resultMessage = trainService.deleteTrain("123");

		assertEquals("Train with number: 123 and all its associated stations have been successfully deleted.",
				resultMessage);
		verify(trainDao, times(1)).delete(train);
	}

	@Test
    void testDeleteTrain_NonExistingTrain_ExceptionThrown() {
        
        when(trainDao.findByTrainNumber(anyString())).thenReturn(Optional.empty());

        
        assertThrows(IllegalArgumentException.class, () -> trainService.deleteTrain("123"));
        verify(trainDao, never()).delete(any(Train.class));
    }

	@Test
	void testDeleteTrain_AssociatedStations_Success() {

		Train train = new Train();
		train.setTrainNumber("123");
		List<Station> stations = new ArrayList<>();
		stations.add(new Station("Station A", train));
		stations.add(new Station("Station B", train));
		train.setStations(stations);
		when(trainDao.findByTrainNumber("123")).thenReturn(Optional.of(train));

		String resultMessage = trainService.deleteTrain("123");

		assertEquals("Train with number: 123 and all its associated stations have been successfully deleted.",
				resultMessage);
		verify(trainDao, times(1)).delete(train);
	}

	@Test
	void testFindTrainByNumber_ExistingTrain_Success() {

		String trainNumber = "123";
		Train expectedTrain = new Train(1L, trainNumber, "Express", null);
		when(trainDao.findByTrainNumber(trainNumber)).thenReturn(Optional.of(expectedTrain));

		Train actualTrain = trainService.findTrainByNumberOrDefault(trainNumber, new Train());

		assertEquals(expectedTrain, actualTrain);
		verify(trainDao, times(1)).findByTrainNumber(trainNumber);
	}

	@Test
	void testFindTrainByNumber_NonExistingTrain_DefaultTrainReturned() {

		String trainNumber = "456";
		Train defaultTrain = new Train(); // Creating a default train
		when(trainDao.findByTrainNumber(trainNumber)).thenReturn(Optional.empty());

		Train actualTrain = trainService.findTrainByNumberOrDefault(trainNumber, defaultTrain);

		assertEquals(defaultTrain, actualTrain);
		verify(trainDao, times(1)).findByTrainNumber(trainNumber);
	}

	@Test
	void testFindTrainsBySourceAndDestination_ValidSourceNonExistingDestination_NoTrainsFound() {

		String source = "SourceA";
		String destination = "NonExistingDestination";

		when(trainDao.findTrainsBySourceAndDestination(source, destination)).thenReturn(Collections.emptyList());

		List<Train> actualTrains = trainService.findTrainsBySourceAndDestination(source, destination);

		assertTrue(actualTrains.isEmpty());
	}

	@Test
	void testFindTrainsBySourceAndDestination_NonExistingSourceValidDestination_NoTrainsFound() {

		String source = "NonExistingSource";
		String destination = "DestinationB";

		when(trainDao.findTrainsBySourceAndDestination(source, destination)).thenReturn(Collections.emptyList());

		List<Train> actualTrains = trainService.findTrainsBySourceAndDestination(source, destination);

		assertTrue(actualTrains.isEmpty());
	}

	@Test
	void testFindTrainsBySourceAndDestination_NonExistingSourceAndDestination_NoTrainsFound() {

		String source = "NonExistingSource";
		String destination = "NonExistingDestination";

		when(trainDao.findTrainsBySourceAndDestination(source, destination)).thenReturn(Collections.emptyList());

		List<Train> actualTrains = trainService.findTrainsBySourceAndDestination(source, destination);

		assertTrue(actualTrains.isEmpty());
	}

	@Test
	void testUpdateTrainDetails_NonExistingTrain_ExceptionThrown() {
	   
	    String trainNumber = "123";
	    TrainUpdateRequest updateRequest = new TrainUpdateRequest("NewName", trainNumber, Collections.singletonList("Station1"));

	    when(trainDao.findByTrainNumber(trainNumber)).thenReturn(Optional.empty()); 

	    TrainUpdateResponse response = trainService.updateTrainDetails(trainNumber, updateRequest);

	    assertNull(null); 
	}
	@Test
	void testUpdateTrainDetails_NullData_ExceptionThrown() {
	
	    String trainNumber = "123";
	    TrainUpdateRequest updateRequest = null;

	    assertDoesNotThrow(() -> {
	        trainService.updateTrainDetails(trainNumber, updateRequest);
	    }, "Expected no exception to be thrown when updateTrainDetails is called with null data");
	}
	
	@Test
	void testUpdateTrainDetails_EmptyStationList_ExceptionThrown() {

	    String trainNumber = "123";
	    TrainUpdateRequest updateRequest = new TrainUpdateRequest();
	    updateRequest.setName("NewName");
	    updateRequest.setStations(Collections.emptyList());

	    Exception exception = null;
	    try {
	        trainService.updateTrainDetails(trainNumber, updateRequest);
	    } catch (IllegalArgumentException e) {
	        exception = e;
	    }

	}
	@Test
	void testUpdateTrainDetails_InvalidStationData_ExceptionThrown() {
	
	    String trainNumber = "123";
	    TrainUpdateRequest updateRequest = new TrainUpdateRequest();
	    updateRequest.setName("NewName");
	    updateRequest.setStations(Collections.singletonList(null));
	    Train existingTrain = new Train(1L, trainNumber, "OldName", null);
	    when(trainDao.findByTrainNumber(trainNumber)).thenReturn(Optional.of(existingTrain));

	 
	    IllegalArgumentException thrownException = null;
	    try {
	        trainService.updateTrainDetails(trainNumber, updateRequest);
	    } catch (IllegalArgumentException e) {
	        thrownException = e;
	    }

	}

}
