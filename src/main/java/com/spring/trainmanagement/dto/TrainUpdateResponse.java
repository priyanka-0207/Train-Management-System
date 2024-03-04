package com.spring.trainmanagement.dto;

import com.spring.trainmanagement.entity.Train;

public class TrainUpdateResponse {

	private boolean success;
	private String message;
	private Train train;

	public TrainUpdateResponse(boolean success, String message, Train train) {
		super();
		this.success = success;
		this.message = message;
		this.train = train;
	}
	public TrainUpdateResponse(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
		this.train = train;
	}

	public TrainUpdateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	@Override
	public String toString() {
		return "TrainUpdateResponse [success=" + success + ", message=" + message + ", train=" + train + "]";
	}

}
