package com.revature.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revature.DTOs.ProjectDTO;
import com.revature.models.Project;

public class FileService {
	public Project ConvertFileToPOJO() {
		try {
		ObjectMapper objectMapper = new XmlMapper();
        // Reads from XML and converts to POJO
		String readContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\amnaazmi786\\Documents\\revature\\ProjectTest\\pom.xml")));
		Project project = objectMapper.readValue(readContent, Project.class);
		System.out.println(project);
        return project;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public ProjectDTO GetProjectDetails(Project project) {
    	ProjectDTO projectDTO = new ProjectDTO();
    	
    	for(int i = 0; i<project.getDependencies().size(); i++) {
    		
    		switch (project.getDependencies().get(i).getGroupId()) {
    		case "org.postgresql":
    			projectDTO.setInstanceId(project.getDependencies().get(0).getArtifactId());
    			projectDTO.setdBLanguage("postgres");
    			projectDTO.setLanguage("Java Maven");
    			projectDTO.setVersion(project.getProperties().get(1).getProperty());
    			return projectDTO;
    		case "com.oracle":
    			projectDTO.setInstanceId(project.getDependencies().get(0).getArtifactId());
    			projectDTO.setdBLanguage("oracle-ee");
    			projectDTO.setLanguage("Java Maven");
    			projectDTO.setVersion(project.getProperties().get(1).getProperty());
    			return projectDTO;
    		default: 
    			break;
    		}
    	}
    	
		return null;
    	
    }
}
