package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.EBService;

@RestController
@RequestMapping("ebs")
public class EBController {
	@Autowired
	private EBService ebService;
	
	@PostMapping("")
	public String CreateEC2() {
		return ebService.CreateEC2();
	}
	@DeleteMapping("")
	public String DeleteEC2() {
		return ebService.deleteEC2();
	}
}
