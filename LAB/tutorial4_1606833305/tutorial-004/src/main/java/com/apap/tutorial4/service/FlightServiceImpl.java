package com.apap.tutorial4.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.repository.FlightDb;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{
	@Autowired
	private FlightDb flightDb;

	public void addFlight(FlightModel flight) {
		flightDb.save(flight);
		
	}

	public List<FlightModel> selectAll() {
		// TODO Auto-generated method stub
		return flightDb.findAll();
	}

	public void deleteFlight(long id) {
		flightDb.deleteById(id);
	}

	@Override
	public FlightModel getFlight(long id) {
		// TODO Auto-generated method stub
		return flightDb.findFlightById(id);
	}

	@Override
	public void updateFlight(FlightModel flight, long id) {
		FlightModel updatedFlight = flightDb.findFlightById(id);
		updatedFlight.setDestination(flight.getDestination());
		updatedFlight.setFlightNumber(flight.getFlightNumber());
		updatedFlight.setTime(flight.getTime());
		updatedFlight.setOrigin(flight.getOrigin());
		flightDb.save(updatedFlight);
		
	}

	@Override
	public FlightModel findFlightByFlightNumber(String flightNumber) {
		// TODO Auto-generated method stub
		return flightDb.findFlightByFlightNumber(flightNumber);
	}


	
}
