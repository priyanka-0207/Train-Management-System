package com.spring.trainmanagement.service;

import java.util.List;

import com.spring.trainmanagement.dto.TrainUpdateRequest;
import com.spring.trainmanagement.dto.TrainUpdateResponse;
import com.spring.trainmanagement.entity.Train;

public interface TrainService {

	public Train addTrain(Train train);

	public String deleteTrain(String string);

	List<Train> findTrainsBySourceAndDestination(String source, String destination);

	public TrainUpdateResponse updateTrainDetails(String number, TrainUpdateRequest updateRequest);

}
