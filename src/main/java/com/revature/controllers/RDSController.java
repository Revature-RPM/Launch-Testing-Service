package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.DTOs.ProjectDTO;
import com.revature.services.RDSDTOEndpoint;
import com.revature.services.RDSService;

import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;

@RestController
@RequestMapping("/rds")
public class RDSController {
	
	@Autowired
	private RDSService rdsService;
	XMLController xmlController = new XMLController();
	RDSDTOEndpoint rdsDTOEndpoint = new RDSDTOEndpoint();
	@PostMapping("")
/**
	 * post request for setting up an RDS DB of any type
	 * 
	 * @param DTO containing project language/rds engine/instanceID
	 * @return
	 */

	public CreateDbInstanceResponse createDatabase(@RequestBody ProjectDTO projectDTO) {
		return rdsService.CreateRds(xmlController.getProject());
	}
	
	@GetMapping("/getRDSEndpoint")
	public String getRDSEndpoint(@RequestBody ProjectDTO projectDTO) {
		return rdsDTOEndpoint.retrieveEndpoint(projectDTO);
		
	}

	

}
