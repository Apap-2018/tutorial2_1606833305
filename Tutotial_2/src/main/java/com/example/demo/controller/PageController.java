package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	@RequestMapping("/viral")
	public String index() {
		return "viral";
	}
	
	@RequestMapping("/challenge")
	public String challenge(@RequestParam(value="name") String name, Model model) {
		model.addAttribute("name", name);
		return "challenge";
	}
	
	@RequestMapping(value= {"/challenge", "/challenge/{name}"})
	public String challengePath (@PathVariable Optional<String> name, Model model) {
		if (name.isPresent()) {
			model.addAttribute("name", name.get());
		}
		else {
			model.addAttribute("name", "KB");
		}
		return "challenge";
	}
	
	@RequestMapping("/generator")
	public String viralGenerator(@RequestParam (value = "a", defaultValue ="0") int number1, @RequestParam (value = "b", defaultValue = "0") int number2, Model model) {	
		String kata = "h";
		String output = "";
		if (number1 <= 1) {
			kata += "m";
		}
		
		else if (number1 > 1) {
			for (int i = 0; i<number1 ; i++) {
				kata+= "m";
			}
		}
		
		
		if (number2 <= 1) {
			output = kata;
		}
		else if (number2 > 1) {
			for (int i = 0; i<number2; i++) {
				output += kata;
				output += " ";
			}
		}
		
		model.addAttribute("a", number1);
		model.addAttribute("b", number2);
		model.addAttribute("output", output);
		
		return "generator";
	}
}
