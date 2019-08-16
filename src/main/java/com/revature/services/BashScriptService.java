package com.revature.services;

/**
 * Service to generate Bash script that is going to be run in the new ec2 instance.
 * 
 * @author Java, MAY 19 - USF
 *
 */
public interface BashScriptService {
	
	/**
	 * Generate Bash script in order to install Docker, build and run the containers
	 * inside of the EC2 instance.
	 * @param dbDockerfileUrl Docker file URL for data base in S3 bucket
	 * @param appDockerfileUrl Docker file URL for the application server in S3 bucket 
	 * @return Bash script
	 */
	public String generateBashScript(String dbDockerfileUrl, String appDockerfileUrl);

}
