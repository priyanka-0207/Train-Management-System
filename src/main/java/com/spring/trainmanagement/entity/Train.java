package com.spring.trainmanagement.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Train {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long train_id;

	@Column(name = "train_number",nullable = false, unique = true)
	@JsonProperty("Number")
	private String trainNumber;

	@Column(nullable = true)
	@JsonProperty("Name")
	private String name;

	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
	@JsonProperty("Stations")
	private List<Station> stations;


	public Train(Long train_id, String trainNumber, String name, List<Station> stations) {
		super();
		this.train_id = train_id;
		this.trainNumber = trainNumber;
		this.name = name;
		this.stations = stations;
	}
	

	public Train(String trainNumber, String name) {
		super();
		this.trainNumber = trainNumber;
		this.name = name;
	}


	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return train_id;
	}

	public void setId(Long train_id) {
		this.train_id = train_id;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
		for (Station station : stations) {
	        station.setTrain(this);
	    }
	}
	public void updateStations(List<String> stationNames) {

        this.stations.clear();

        if (stationNames != null) {
            for (String name : stationNames) {
                Station station = new Station();
                station.setStationName(name);
                station.setTrain(this);
                this.stations.add(station);
            }
        }
	}

	@Override
	public String toString() {
		return "Train [id=" + train_id + ", trainNumber=" + trainNumber + ", name=" + name + ", stations=" + stations + "]";
	}

	public Train orElse(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStationsFromNames(List<String> stationNames) {
	    if (stationNames == null) {
	        return;
	    }

	    if (this.stations == null) {
	        this.stations = new ArrayList<>();
	    } else {
	        this.stations.clear();
	    }

	    for (String name : stationNames) {
	        this.stations.add(new Station(name, this));
	    }
	}


}
