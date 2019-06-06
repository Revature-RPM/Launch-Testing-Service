package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.DTOs.ProjectDTO;

public class RDSDTOEndpoint {
	@Autowired
	RDSService rdsService = new RDSService();
	
	
	public String retrieveEndpoint(ProjectDTO projectDTO) {
		String endpoint = rdsService.CreateRds(projectDTO).toString();
		return endpoint;
		
	}
}