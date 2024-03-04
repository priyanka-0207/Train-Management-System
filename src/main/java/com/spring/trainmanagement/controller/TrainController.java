package com.spring.trainmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.trainmanagement.dto.TrainUpdateRequest;
import com.spring.trainmanagement.dto.TrainUpdateResponse;
import com.spring.trainmanagement.entity.Station;
import com.spring.trainmanagement.entity.Train;
import com.spring.trainmanagement.service.TrainService;

@RestController
@RequestMapping("/trains")
public class TrainController {

	@Autowired
	private TrainService trainService;

	@PostMapping("/add")
	public ResponseEntity<Train> addTrain(@RequestBody Train trainRequest) {
		Train train = new Train();
		train.setTrainNumber(trainRequest.getTrainNumber());
		train.setName(trainRequest.getName());

		List<Station> stationNames = trainRequest.getStations();
		List<Station> stations = new ArrayList<>();

		if (stationNames != null) {
			for (Station station : stationNames) {
				stations.add(new Station(station.getStationName()));
			}
		}
		System.out.println("Received train: " + train);
		train.setStations((List<Station>) stations);

		Train addedTrain = trainService.addTrain(train);
		return ResponseEntity.status(HttpStatus.CREATED).body(addedTrain);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteTrain(@RequestBody Train request) {
		String resultMessage = trainService.deleteTrain(request.getTrainNumber());

		if (resultMessage.contains("successfully")) {
			return ResponseEntity.ok().body(resultMessage);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMessage);
		}
	}

	
	@PostMapping("/updateTrain")
	public ResponseEntity<TrainUpdateResponse> updateTrain(@RequestBody TrainUpdateRequest updateRequest) {
	    String trainNumber = updateRequest.getNumber();
	    TrainUpdateResponse response = trainService.updateTrainDetails(trainNumber, updateRequest);
	    if (response != null && response.isSuccess()) {
	        System.out.println("Received train number: " + updateRequest.getNumber());
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}


	@GetMapping("/find")
	public ResponseEntity<List<Train>> findTrainsBySourceAndDestination(@RequestParam String source,
			@RequestParam String destination) {
		List<Train> trains = trainService.findTrainsBySourceAndDestination(source, destination);
		if (trains.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trains);
	}
}
