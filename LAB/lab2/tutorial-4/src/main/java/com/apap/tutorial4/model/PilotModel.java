package com.apap.tutorial4.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;



/**
 * Pilot Model
 */

@Entity
@Table(name = "pilot")
public class PilotModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
}
