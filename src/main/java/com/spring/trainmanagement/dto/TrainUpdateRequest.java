package com.spring.trainmanagement.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainUpdateRequest {

	@JsonProperty("Number")
	private String number;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Stations")
	private List<String> stations;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getStations() {
		return stations;
	}

	public void setStations(List<String> stations) {
		this.stations = stations;
	}

	public TrainUpdateRequest(String number, String name, List<String> stations) {
		super();
		this.number = number;
		this.name = name;
		this.stations = stations;
	}

	public TrainUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TrainUpdateRequest [number=" + number + ", name=" + name + ", stations=" + stations + "]";
	}

}
