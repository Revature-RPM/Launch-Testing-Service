package com.revature.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.revature.models.ConnectionVariables;
import com.revature.models.EnvironmentVariable;

/**
 * Service to handle Docker files.
 * 
 * @author Java, MAY 19 - USF
 *
 */
public interface DockerfileService {

	/**
	 * Generate a Docker file for a server container in memory in order to save it in an S3 bucket.
	 * @param projectId Project id for this deployment
	 * @param gitHubUrl GitHub URL
	 * @param pomLocation Location of the POM (Java) without including pom.xml
	 * @param connectionVariables Connection variables for this server container
	 * @param environmentVariables Other environment variables needed for this app to run
	 * @return In memory Docker file for application server
	 * @throws IOException 
	 */
	public File generateTomcatServerDockerfile(String projectId, String gitHubUrl, String pomLocation,
			ConnectionVariables connectionVariables, List<EnvironmentVariable> environmentVariables) throws IOException;
	
	/**
	 * Generate a Docker file for a postgresql server container in memory in order to save it in an S3 bucket.
	 * @param projectId Project id for this deployment
	 * @param sqlScriptUrl URL of the SQL file in the S3 bucket
	 * @return In memory Docker file for database server
	 * @throws IOException 
	 */
	public File generatePostgreSQLDockerfile(String projectId, String sqlScriptUrl) throws IOException;

}
