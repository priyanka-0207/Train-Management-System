package com.spring.trainmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.trainmanagement.dao.TrainDao;
import com.spring.trainmanagement.dto.TrainUpdateRequest;
import com.spring.trainmanagement.dto.TrainUpdateResponse;
import com.spring.trainmanagement.entity.Station;
import com.spring.trainmanagement.entity.Train;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {

	@Autowired
	private TrainDao trainDao;

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(TrainService.class);

	@Override
	public Train addTrain(Train train) {
		if (train == null) {
			throw new IllegalArgumentException("Train object cannot be null");
		}

		for (Station station : train.getStations()) {
			station.setTrain(train);
		}
		Train savedTrain = trainDao.save(train);
		if (savedTrain == null) {
			throw new RuntimeException("Failed to save train");
		}
		logger.info("Train successfully added with ID: {}", savedTrain.getId());
		return savedTrain;

	}

	@Transactional
	public String deleteTrain(String trainNumber) {

		Optional<Train> trainOptional = trainDao.findByTrainNumber(trainNumber);

		if (trainOptional.isPresent()) {
			Train train = trainOptional.get();
			trainDao.delete(train);
			return "Train with number: " + trainNumber
					+ " and all its associated stations have been successfully deleted.";
		} else {
			throw new IllegalArgumentException("Train with number: " + trainNumber + " not found.");
		}
	}

	public List<Train> findTrainsBySourceAndDestination(String source, String destination) {
		return trainDao.findAll().stream().filter(train -> train.getStations().stream()
				.anyMatch(station -> station.getStationName().equals(source))
				&& train.getStations().stream().anyMatch(station -> station.getStationName().equals(destination)))
				.collect(Collectors.toList());
	}

	public Train findTrainByNumberOrDefault(String trainNumber, Train defaultTrain) {
		return trainDao.findByTrainNumber(trainNumber).orElse(defaultTrain);
	}

	@Transactional
	@Override
	public TrainUpdateResponse updateTrainDetails(String trainNumber, TrainUpdateRequest updateRequest) {
		Optional<Train> optionalTrain = trainDao.findByTrainNumber(trainNumber);
		if (optionalTrain.isEmpty()) {
			return new TrainUpdateResponse(false, "Train not found with number: " + trainNumber, null);
		}
		Train train = optionalTrain.get();

		train.setName(updateRequest.getName());

		train.setStationsFromNames(updateRequest.getStations());

		trainDao.save(train);

		return new TrainUpdateResponse(true, "Train updated successfully", train);
	}

}
