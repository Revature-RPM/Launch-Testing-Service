package com.revature.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.FileService;

import com.revature.models.Project;

@RestController
@RequestMapping("/xml")
public class XMLController {
	
	FileService fileService = new FileService();
	
	@PostMapping("") 
	public Project runXMLParser(@RequestBody Project pom) {
//		try {
//			byte[] XMLData = Files.readAllBytes(Paths.get(url));
//			ObjectMapper om = new XmlMapper();
//			Pom pom = om.readValue(XMLData, Pom.class);
//			System.out.println(pom);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//	}
		System.out.println(pom);
		return pom;
	}
	
	
	@GetMapping("/getfile")
	public void getFile(@RequestBody String filePath) {
		fileService.FindFile(filePath);
		
	}
	
}
