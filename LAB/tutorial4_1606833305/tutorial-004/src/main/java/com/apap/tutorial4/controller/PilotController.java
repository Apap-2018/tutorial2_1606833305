package com.apap.tutorial4.controller;

import java.util.ArrayList;
import java.util.List;

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
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping("/")
	private String home () {
		return "home";
	}
	

	
	@RequestMapping (value = "/pilot/view")
	private String pilotView (@RequestParam ("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		
		List <FlightModel> currentPilotFlights = new ArrayList();
		List <FlightModel> flights = flightService.selectAll();
		

		for (FlightModel flight: flights) {
			if(flight.getPilot().getLicenseNumber().equals(licenseNumber)) {
				currentPilotFlights.add(flight);
			}
		}
		
		model.addAttribute("flights", currentPilotFlights);
		return "view-pilot";
		
	}
	

	
	@RequestMapping (value = "/pilot/delete/{licenseNumber}")
	private String deletePilot (@PathVariable ("licenseNumber") long id) {
		pilotService.deletePilot(id);
		return "deletePilot";
	}
	
	@RequestMapping (value = "/pilot/add", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping (value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping (value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot (@PathVariable ("licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("currentPilot", pilot);
		System.out.println(pilot.getLicenseNumber() + "DELFA") ;
		return "updatePilot";	
	}
	
	@RequestMapping (value = "/pilot/update", method = RequestMethod.POST)
	private String updatePilotSubmit (@ModelAttribute PilotModel pilot) {
		pilotService.updatePilot(pilot, pilot.getLicenseNumber());
		return "suksesUpdate";
	}
	
	
	
}
