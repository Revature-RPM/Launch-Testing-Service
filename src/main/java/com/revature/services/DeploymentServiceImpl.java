package com.revature.services;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.models.Deployment;
import com.revature.utils.FileHelper;

/**
 * Takes care of all the necessary process to deploy a project in an EC2 instance.
 * This class was designed to be the single point of access to the deployment process.
 * This way we only need to call an object from this class in the controller instead of
 * calling all the services.
 * This class is implementing the facade pattern:
 * 
 * More information about this pattern here:
 * https://www.tutorialspoint.com/design_pattern/facade_pattern
 * 
 * @author Java, MAY 19 - USF
 *
 */
@Component
public class DeploymentServiceImpl implements DeploymentService {

	private EC2InstanceService ec2InstanceService; // EC2 instance service
	private DockerfileService dockerFileService; // Docker file service
	private S3FileStorageService s3FileStorageService; // S3 file storage service
	private BashScriptService bashScriptService; // Bash script service
	
	/**
	 *  %s will be replaced by the public DNS we got from amazon
	 *  we setup projects URL to end with '/project2' this way we avoid
	 *  naming confusion when accessing our project from the web browser.
	 */
	private final String PROJECT_URL_DNS_FORMAT = "%s/project2";

	@Autowired
	public void setEc2InstanceService(EC2InstanceService ec2InstanceService) {
		this.ec2InstanceService = ec2InstanceService;
	}

	@Autowired
	public void setDockerFileService(DockerfileService dockerFileService) {
		this.dockerFileService = dockerFileService;
	}

	@Autowired
	public void setS3FileStorageService(S3FileStorageService s3FileStorageService) {
		this.s3FileStorageService = s3FileStorageService;
	}

	@Autowired
	public void setBashScriptService(BashScriptService bashScriptService) {
		this.bashScriptService = bashScriptService;
	}

	/**
	 * Deploy project to the EC2 instance using Docker files.
	 * 
	 * Steps:
	 * 
	 * 1. Save SQL script to an S3 bucket.
	 * 2. Create Docker file for a database instance.
	 * 3. Create Docker file for an application server instance.
	 * 4. Store database Docker file in the S3 bucket to make it accessible to the EC2.
	 * 5. Store application Docker file in the S3 bucket to make it accessible to the EC2.
	 * 6. Generate bash script that will be run when we spin up the EC2 instance.
	 * 7. Spin up a new EC2.
	 * 
	 * @param deployment Contains all the information necessary to deploy the project.
	 * @return EC2 instance id
	 */
	@Override
	public String deployProject(Deployment deployment) {
		
		File dbDockefile = null; // Docker file for database
		File appDockerfile = null; // Docker file for application server

		try {
			// Convert a Multipart file into a regular Java file
			File sqlScriptFile = FileHelper.convert(deployment.getSqlScript());
			
			// Store sql script file in S3 bucket
			String sqlSctiptUrl = s3FileStorageService.storeFile(sqlScriptFile);
			
			sqlScriptFile.delete(); // Delete sql file after storing it in the S3 bucket

			// Generate Docker file for database
			dbDockefile = dockerFileService.generatePostgreSQLDockerfile(deployment.getProjectId(), sqlSctiptUrl);
			
			// Generate Docker file for web application server
			appDockerfile = dockerFileService.generateTomcatServerDockerfile(
					deployment.getProjectId(),
					deployment.getGitHubUrl(),
					deployment.getPomLocation(),
					deployment.getConnVariables(),
					deployment.getEnvironmentVariables());
			
		} catch (IOException e) {
			// TODO Handle the exception
			e.printStackTrace();
		}
		
		// Store database and application Docker files in the S3 bucket
		String dbDockerfileUrl = s3FileStorageService.storeFile(dbDockefile);
		String appDockerfileUrl = s3FileStorageService.storeFile(appDockerfile);
		
		// Delete generated Docker files, we have this files in the S3 bucket, so we don't
		// need them anymore, this way we can keep our server clean
		dbDockefile.delete();
		appDockerfile.delete();
		
		// Generate bash script for the EC2 to install and run docker
		String bashScript = bashScriptService.generateBashScript(dbDockerfileUrl, appDockerfileUrl);
		
		String ec2InstanceId = null;
		
		try {
			// Spin up a new EC2 instance with the required
			ec2InstanceId = ec2InstanceService.spinUpEC2Instance(bashScript);
		} catch (UnsupportedEncodingException e) {
			// TODO Handle the exception
			e.printStackTrace();
		}
		
		return ec2InstanceId;
	}

	/**
	 * Get public DNS for the EC2 instance given the id.
	 * This will obtained from the ec2 service.
	 * 
	 * @param ec2InstanceId EC2's instance id
	 */
	@Override
	public String getEC2ProjectPublicDns(String ec2InstanceId) {
		
		String publicDns = ec2InstanceService.getEC2InstancePublicDNS(ec2InstanceId);
		
		if (publicDns == null || publicDns.isEmpty() || publicDns.equals("")) {
			return null;
		}
		
		return String.format(PROJECT_URL_DNS_FORMAT, publicDns);
	}

}
