package com.revature.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.web.multipart.MultipartFile;

/**
 * File utilities for our project.
 * 
 * @author Java, MAY 19 - USF
 *
 */
public class FileHelper {
	
	/**
	 * Get content from a text file and return it as String.
	 * @param fileName File name in the URL.
	 * @return File content
	 */
	public static String getTextFileContent(String fileName) {
		Scanner sc = null;
		String fileContent = "";

		try {
			File file = new File(FileHelper.class.getClassLoader().getResource(fileName).getFile());
			sc = new Scanner(file);

			while (sc.hasNextLine()) {
				fileContent += sc.nextLine() + "\n";
			}

		} catch (Exception e) {
			// TODO: handle exception, Log the exception
			e.printStackTrace();
		} finally {
			sc.close();
		}
		
		return fileContent;
	}
	
	/**
	 * Got this from Project service. This method convert a multipart file into a regular
	 * Java file, this way we are able to store it and handle it.
	 * 
	 * @param multipartFile File sent to the server.
	 * @return Java File object
	 * @throws IOException
	 */
	public static File convert(MultipartFile multipartFile) throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}

}
