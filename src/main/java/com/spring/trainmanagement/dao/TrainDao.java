package com.spring.trainmanagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.trainmanagement.entity.Train;

public interface TrainDao extends JpaRepository<Train, Long> {


	@Query("SELECT t FROM Train t JOIN t.stations s WHERE s.stationName = :source OR s.stationName = :destination GROUP BY t HAVING COUNT(t) >= 2")
    List<Train> findTrainsBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);


	@Query("SELECT t.name AS trainName, s.stationName AS stationName " + "FROM Train t JOIN t.stations s "
	+ "WHERE t.trainNumber = :trainNumber")
	public Train findTrainNumber(@Param("trainNumber") String trainNumber);
	
	Optional<Train> findByTrainNumber(String trainNumber);

}
