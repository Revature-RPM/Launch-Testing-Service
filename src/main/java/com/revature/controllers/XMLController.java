package com.revature.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.FileService;
import com.revature.DTOs.ProjectDTO;
import com.revature.models.Project;
import com.revature.models.Property;

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
	
	/**
	 * Post mapping to convert a POM to a more useful object
	 * @param filePath full path to the POM file that needs to be parsed to determine its reqs
	 * @return
	 */
	@PostMapping("/getfile")
	public Project getFile(@RequestBody String filePath) {
		return fileService.ConvertFileToPOJO();
	}
	
	@GetMapping("/getproject")
	public ProjectDTO getProject() {
		return fileService.GetProjectDetails(fileService.ConvertFileToPOJO());
	}
	
}
