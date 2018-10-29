package com.apap.tutorial4.service;

import java.util.List;


import com.apap.tutorial4.model.FlightModel;

public interface FlightService {
	void addFlight(FlightModel flight);
	List <FlightModel> selectAll ();
	void deleteFlight (long id);
	FlightModel getFlight (long id);
	void updateFlight (FlightModel flight, long id);
	FlightModel findFlightByFlightNumber (String flightNumber);
}
