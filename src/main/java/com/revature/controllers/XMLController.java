package com.revature.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revature.models.Pom;
import com.revature.services.FileService;

@RestController
@RequestMapping("/xml")
public class XMLController {
	
	FileService fileService = new FileService();
	
	@PostMapping("")
	public void runXMLParser(@RequestBody String url) {
		try {
			byte[] XMLData = Files.readAllBytes(Paths.get(url));
			ObjectMapper om = new XmlMapper();
			Pom pom = om.readValue(XMLData, Pom.class);
			System.out.println(pom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("/getfile")
	public void getFile(@RequestBody String filePath) {
		fileService.FindFile(filePath);
		
	}
	
}
