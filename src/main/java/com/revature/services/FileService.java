package com.revature.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revature.models.Project;

public class FileService {
	public Project ConvertFileToPOJO() {
		try {
		ObjectMapper objectMapper = new XmlMapper();
        // Reads from XML and converts to POJO
		String readContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\User\\Documents\\revature\\Project 3\\pom.xml")));
		Project project = objectMapper.readValue(readContent, Project.class);
		System.out.println(project);
        return project;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
