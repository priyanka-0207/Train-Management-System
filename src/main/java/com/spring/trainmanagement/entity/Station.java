package com.spring.trainmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String stationName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_id")
	@JsonBackReference
	private Train train;

	public Station() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Station(Long id, String stationName, Train train) {
		super();
		this.id = id;
		this.stationName = stationName;
		this.train = train;
	}
	public Station(String stationName, Train train) {
        this.stationName = stationName;
        this.train = train;
    }

	public Station(Station stationName2) {
		super();
		// TODO Auto-generated constructor stub
	}

	public Station(String stationName) {
		this.stationName = stationName;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", stationName=" + stationName + ", train=" + train + "]";
	}

}
