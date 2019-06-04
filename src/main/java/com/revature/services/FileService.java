package com.revature.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.revature.models.Property;

public class FileService {

	public void FindFile(String filePath){
		 String filepath = "C:\\Users\\User\\Documents\\revature\\Project 3\\pom.xml";
		File location = new File(filepath);
		if( location.exists()) {
			System.out.println("file exists "+location.exists());
			try {
			XmlMapper xmlMapper = new XmlMapper();
			String xml = inputStreamToString(new FileInputStream(location));
			Property property = xmlMapper.readValue(xml, Property.class);
			System.out.println(property);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String inputStreamToString(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}
	
}
