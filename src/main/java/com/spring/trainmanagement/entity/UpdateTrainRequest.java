package com.spring.trainmanagement.entity;

import java.util.List;

public class UpdateTrainRequest {
	
	    private String trainNumber;
	    private String newName;
	    private List<String> newStations;
		public String getTrainNumber() {
			return trainNumber;
		}
		public void setTrainNumber(String trainNumber) {
			this.trainNumber = trainNumber;
		}
		public String getNewName() {
			return newName;
		}
		public void setNewName(String newName) {
			this.newName = newName;
		}
		public List<String> getNewStations() {
			return newStations;
		}
		public void setNewStations(List<String> newStations) {
			this.newStations = newStations;
		}
		@Override
		public String toString() {
			return "UpdateTrainRequest [trainNumber=" + trainNumber + ", newName=" + newName + ", newStations="
					+ newStations + "]";
		}
	    
	    

	   
	}


