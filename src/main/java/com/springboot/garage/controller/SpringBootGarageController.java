package com.springboot.garage.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.garage.exception.ResourceNotFoundException;
import com.springboot.garage.model.SpringBootGarageModel;
import com.springboot.garage.repository.SpringBootGarageRepository;

@RestController
@RequestMapping("/api")
public class SpringBootGarageController {

	@Autowired
	SpringBootGarageRepository myRepository;
	
	@PostMapping("/vehicle")
	public SpringBootGarageModel createVehicle (@Valid @RequestBody SpringBootGarageModel sBGM) {
		return myRepository.save(sBGM);
	}
	@GetMapping("/vehicle/{id}")
	public SpringBootGarageModel getVehicleByID (@PathVariable(value = "id")Long vehicleID) {
		return myRepository.findById(vehicleID).orElseThrow(() -> new ResourceNotFoundException("SpringBootGarageModel", "id", vehicleID));
	}
	@GetMapping("/vehicle")
	public List<SpringBootGarageModel> getAllVehicles(){
		return myRepository.findAll();
	}
	@PutMapping("/vehicle/{id}")
	public SpringBootGarageModel updateVehicle(@PathVariable(value = "id") Long vehicleID, @Valid @RequestBody SpringBootGarageModel vehicleDetails) {
		SpringBootGarageModel sBGM = myRepository.findById(vehicleID).orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleID));
		sBGM.setVehicleType(vehicleDetails.getVehicleType());
		sBGM.setModel(vehicleDetails.getModel());
		sBGM.setManufacturer(vehicleDetails.getManufacturer());
		sBGM.setColour(vehicleDetails.getColour());
		sBGM.setRegistration(vehicleDetails.getRegistration());
		SpringBootGarageModel updateData = myRepository.save(sBGM);
		return updateData;
	}
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<?> deleteVehicle(@PathVariable(value = "id")Long vehicleID) {
		SpringBootGarageModel sBGM = myRepository.findById(vehicleID).orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleID));
		myRepository.delete(sBGM);
		return ResponseEntity.ok().build();
	}
}
