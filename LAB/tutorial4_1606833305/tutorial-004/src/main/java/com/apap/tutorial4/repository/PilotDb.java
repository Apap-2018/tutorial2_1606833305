package com.apap.tutorial4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apap.tutorial4.model.PilotModel;

@Repository
public interface PilotDb extends JpaRepository <PilotModel, Long>{
	PilotModel findByLicenseNumber (String licenseNumber);
}
