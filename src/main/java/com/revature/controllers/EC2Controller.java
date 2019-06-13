package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.EC2Service;

@RestController
@RequestMapping("ec2")
public class EC2Controller {
	@Autowired
	EC2Service ecService;
	
	@PostMapping("")
	public String createEc2() {
		
		return ecService.createInstance();
	}
}
