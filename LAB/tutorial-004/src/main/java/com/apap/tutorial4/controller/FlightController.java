package com.apap.tutorial4.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add (@PathVariable (value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping (value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit (@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping (value = "/flight/delete/{id}")
	private String deleteFlight (@PathVariable (value = "id") long id) {
		flightService.deleteFlight(id);
		return "deleteFlight";
	}
	
	@RequestMapping (value = "/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight (@PathVariable (value = "id") long id, Model model) {
		FlightModel currentFlight = flightService.getFlight(id);
		System.out.println(currentFlight.getId() +"hahaha");
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(currentFlight.getPilot().getLicenseNumber());
		currentFlight.setPilot(pilot);
		model.addAttribute("currentFlight", currentFlight);
		return "updateFlight";
	}
	
	@RequestMapping (value = "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit (@ModelAttribute FlightModel currentflight) {
		System.out.println(currentflight.getPilot().getName() + "DELFAAAAA");
		System.out.println(currentflight.getId() + "id cek cek");
		
		flightService.updateFlight(currentflight, currentflight.getId());
		return "suksesUpdate";	
	}
	
	@RequestMapping (value = "/flight/view")
	private String viewFlight (@RequestParam ("flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.findFlightByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		return "viewFlight";		
	}
	
}
