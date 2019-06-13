package com.revature.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revature.DTOs.ProjectDTO;
import com.revature.models.Project;

public class FileService {
	/**
	 * Converts a given pom.xml into a workable project POJO  
	 * @return 
	 */
	public Project convertFileToPOJO() {
		try {
		//Jackson Objmapper creation (taking in XML files)
		ObjectMapper objectMapper = new XmlMapper();
        // Reads from XML and converts to POJO
		String readContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\amnaazmi786\\Documents\\revature\\ProjectTest\\pom.xml")));//read a file (currently file location is hardcoded)
		Project project = objectMapper.readValue(readContent, Project.class);//read content into a project object
		System.out.println(project);//print project 
        return project;//return POJO
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;//returns null if failure
	}
	
	/**
	 * Given project POJO create a DTO representing it's information
	 * @param project
	 * @return a DTO representing the important information sued to provision resources and run 
	 */
    public ProjectDTO getProjectDetails(Project project) {
    	ProjectDTO projectDTO = new ProjectDTO();//create base DTO object
    	
    	for(int i = 0; i<project.getDependencies().size(); i++) {//rip through all of the POJO's dependencies
    		switch (project.getDependencies().get(i).getGroupId()) {//checking for specific dependencies used to determine certain required resources
    		case "org.postgresql"://case where RDs needed is PostgreSQL
    			projectDTO.setInstanceId(project.getDependencies().get(0).getArtifactId());//get artifact
    			projectDTO.setdBLanguage("postgres");//set name of dependency
    			projectDTO.setLanguage("Java Maven");//set name of source (were only checking pom.xml so weknow its maven at this time
    			projectDTO.setVersion(project.getProperties().get(1).getProperty());//set what version (could be necessary if they want to run older versions of RDS engines
    			return projectDTO;//return the required info (could be changed to a setter but current state only requires the one dependency)
    		case "com.oracle"://case for OracleDB
    			projectDTO.setInstanceId(project.getDependencies().get(0).getArtifactId());//get artifact
    			projectDTO.setdBLanguage("oracle-ee");//name of dependency
    			projectDTO.setLanguage("Java Maven");//name of where source
    			projectDTO.setVersion(project.getProperties().get(1).getProperty());//set value
    			return projectDTO;
    		default: //case for if we dont have a dependency match
    			break;//break because we don't effectively deal with it here (for cases where the project libraries handle it or its a project we dont have a template for
    		}
    	}    	
		return null;
    	
    }
}
