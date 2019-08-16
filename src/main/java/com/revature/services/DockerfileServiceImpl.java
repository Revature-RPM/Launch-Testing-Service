package com.revature.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.models.ConnectionVariables;
import com.revature.models.EnvironmentVariable;
import com.revature.utils.FileHelper;

/**
 * Service to handle Docker files.
 * 
 * The files we create will have the following naming convention to identify which file
 * belongs to which project:
 * 
 * {name_of_file}-{project_id}
 * 
 * Example: DockerfileTomcat-ab12cdf
 * 
 * @author Java, MAY 19 - USF
 *
 */
@Component
public class DockerfileServiceImpl implements DockerfileService {
	
	private final String DOCKERFILE_POSTGRES = "DockerfilePostgres";
	private final String DOCKERFILE_TOMCAT = "DockerfileTomcat";
	private final String DOCKER_FILE_DIR = "dockerfiles/";

	/**
	 * Tomcat server Docker file obtained from: https://hub.docker.com/_/tomcat
	 * 
	 * This method grabs the docker file from the classpath and replaces the placeholder
	 * with the values specific for the project that we want to deploy. In this case we
	 * have to set up the following values:
	 * 
	 * %gitHubUrl% -> GitHub URL of the project.
	 * %pomLocation% -> Location of the POM file inside the project.
	 * %db_url% -> Name of the Environment variable that will contain the URL of the database
	 * 			   server for this project. We don't need the database server's URL, we just need the
	 * 			   name of the variable, we provide the database server in a docker container.
	 * %db_username% -> Name of the Environment variable that will contain the username of the user
	 * 			   that connects to the database. We don't need the username, we just need the name 
	 * 			   of the variable.
	 * %db_password%  -> Name of the Environment variable that will contain the password of the user
	 * 				that connects to the database.
	 * 
	 * Example (Java code, in the project that will be deployed to the EC2):
	 * 
	 * Connection parameters:
	 * 
	 * url = System.getenv("TRMS_URL");
	 * user = System.getenv("TRMS_USER");
	 * password = System.getenv("TRMS_PASS");
	 *  
	 * Creating the connection:
	 *  
	 * Class.forName("org.postgresql.Driver");
	 * Connection connection = DriverManager.getConnection(url, user, password);
	 *  
	 * Also, we can specify other environment variables:
	 *  
	 * Example:
	 *  
	 * List<EnvironmentVariable> envVars = new ArrayList<EnvironmentVariable>();
	 * envVars.add(new EnvironmentVariable("JWT_TOKEN", "local-deploy"));
	 *  
	 * This will be written in the docker file replacing the %enviromentVariables% placeholder.
	 * 
	 * envVarStr += "ENV " + envVar.getVariableName() + " " + envVar.getVariableValue() + "\n";
	 *  
	 * This will be written in the docker file like this: ENV JWT_TOKEN local-deploy
	 *  
	 */
	@Override
	public File generateTomcatServerDockerfile(
			String projectId,
			String gitHubUrl,
			String pomLocation,
			ConnectionVariables conVar,
			List<EnvironmentVariable> environmentVariables) throws IOException {
		
		// Loading the Docker file, it has placeholder values (denoted by %%) that will be 
		// replaced with the specific values for this project
		String dockerfileContent = FileHelper.getTextFileContent(DOCKER_FILE_DIR + DOCKERFILE_TOMCAT);
		
		// Replace placeholder values
		dockerfileContent = dockerfileContent.replaceAll("%gitHubUrl%", gitHubUrl + ".git");
		dockerfileContent = dockerfileContent.replaceAll("%pomLocation%", pomLocation);
		dockerfileContent = dockerfileContent.replaceAll("%db_url%", conVar.getUrlVariableName());
		dockerfileContent = dockerfileContent.replaceAll("%db_username%", conVar.getUsernameVariableName());
		dockerfileContent = dockerfileContent.replaceAll("%db_password%", conVar.getPasswordVariableName());
		
		String envVarStr = "";
		
		for (EnvironmentVariable envVar : environmentVariables) {
			envVarStr += "ENV " + envVar.getVariableName() + " " + envVar.getVariableValue() + "\n";
		}
		
		dockerfileContent = dockerfileContent.replaceAll("%enviromentVariables%", envVarStr);
		
		// Create a new file and write the content of the docker file with
		// the placeholder already replaced by our data
		File dockerfile = new File(DOCKERFILE_TOMCAT + "-" + projectId);
		dockerfile.deleteOnExit();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(dockerfile));
	    out.write(dockerfileContent);
	    out.close();
		
		return dockerfile;
	}

	/**
	 * PostgreSQL Docker file that will set up the database for our project.
	 * 
	 * This method will grab the docker file from the classpath and will replace the placeholder
	 * with the URL of our SQL script that creates the tables for our project.
	 * 
	 * Example:
	 * 
	 * %sqlScriptUrl% will be replaced by this
	 * https://revature-jose-test-bucket.s3.us-east-2.amazonaws.com/trms.sql
	 *  
	 * This way our docker container will download the script and will create the database for us.
	 */
	@Override
	public File generatePostgreSQLDockerfile(String projectId, String sqlScriptUrl) throws IOException {
		// Loading the Docker file, it has placeholder values (denoted by %%) that will be 
		// replaced with the specific values for this project
		String dockerfileContent = FileHelper.getTextFileContent(DOCKER_FILE_DIR + DOCKERFILE_POSTGRES);
		
		dockerfileContent = dockerfileContent.replaceAll("%sqlScriptUrl%", sqlScriptUrl);
		
		// Create a new file and write the content of the docker file with
		// the placeholder already replaced by our data
		File dockerfile = new File(DOCKERFILE_POSTGRES + "-" + projectId);
		dockerfile.deleteOnExit();
		
		BufferedWriter out = new BufferedWriter(new FileWriter(dockerfile));
	    out.write(dockerfileContent);
	    out.close();
		
		return dockerfile;
	}

}
