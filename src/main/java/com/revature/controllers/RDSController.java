package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.DTOs.ProjectDTO;
import com.revature.DTOs.RdsDTO;


import com.revature.services.RDSService;

@RestController
@RequestMapping("/rds")
public class RDSController {
	
	@Autowired
	private RDSService rdsService;
	
	XMLController xmlController = new XMLController();
	
	@PostMapping("")




	/**

	 * post request for setting up an RDS DB of any type
	 * 
	 * @param DTO containing project language/rds engine/instanceID
	 * @return
	 */


	public RdsDTO createDatabase(@RequestBody ProjectDTO projectDTO) {
		return rdsService.CreateRds(xmlController.getProject());

	
	}
}
