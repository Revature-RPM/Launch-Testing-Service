package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.RDSService;

@RestController
@RequestMapping("/rds")
public class RDSController {
	
	@Autowired
	private RDSService rdsService;
	@PostMapping("")
	public String createDatabase(@RequestBody String dialect) {
		
		return null;
	}
}
