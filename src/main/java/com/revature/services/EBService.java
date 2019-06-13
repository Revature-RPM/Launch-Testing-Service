package com.revature.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.elasticbeanstalk.ElasticBeanstalkClient;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateApplicationRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateApplicationResponse;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateApplicationVersionRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentResponse;
import software.amazon.awssdk.services.elasticbeanstalk.model.S3Location;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentResponse;
import software.amazon.awssdk.services.rds.model.AuthorizeDbSecurityGroupIngressResponse;

@Service
public class EBService {

	/**
	 * Code for Generation of EC2 instance
	 * 
	 * @return EC2 Response object containing Information
	 */
	public String CreateEC2() {
		
		//S3 Variables Set
		String s3 = "norobotsplzdontfindme"; // This will eventually be the project s3 location
		// String s3ARN = "arn:aws:s3:::expenseapplication"; //ARN of the S3
		//String s3Key = "It's an s3 key!!!!";//S3 Key
		String versionLabel = "procfilewithebex1.5";//Version label used to link application 
		String objectURL = "https://norobotsplzdontfindme.s3.amazonaws.com/spring-boot.jar";//FQDN pointing to your jar
		String name = "spring-boot";//name of application
		String stackName = "64bit Amazon Linux 2018.03 v2.8.3 running Java 8";//OS information
		//S3 object creation
		S3Location s3Bucket = S3Location.builder()//static builder
				.s3Bucket(s3)//set attribute name
				.s3Key("springboot.zip")//set where S3 key is
				.build();//build obj
		
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();//vreate client of EB interaction
		//unused code for project compilation (can be reviewed and uncommented if RPM goes the direction of submitting uncompiled projects
		//CreateApplicationRequest
		//CreateApplicationRequest req = CreateApplicationRequest.builder()
		//		.applicationName(name)
		//		.build();
		//Generate and attach Procfile
		//bean.createApplication(req);
		//AuthorizeDbSecurityGroupIngressResponse
		
		//turning information stored in s3 bucket into a working application
		CreateApplicationVersionRequest applicationRequest = CreateApplicationVersionRequest.builder()//static builder of version request
				.sourceBundle(s3Bucket)//point to created s3 object
				.applicationName(name)//name of application
				.versionLabel(versionLabel)//name you want the version to be
				.build();//build request
		bean.createApplicationVersion(applicationRequest);//runrequest
		
		//create environment through EB
		CreateEnvironmentRequest envRequest = CreateEnvironmentRequest.builder()//static builder
				.applicationName(name)//same name as just used of application
				.versionLabel(versionLabel)//name of the version (MUST match above)
				.environmentName("nemooo")//name of the environment(what will show up as on EB console
				.solutionStackName(stackName)//tech stack information that the Ec2 will run with
				.build();//build request
		// CreateApplicationVersionResponse resp = bean.createApplicationVersion(applicationRequest);//create new version (if commented this is because we already have a version)
		CreateEnvironmentResponse resptoo = bean.createEnvironment(envRequest);//send EB request and make environment
		return resptoo.toString();//return data of provisioned environment
	}
	
	/**
	 * delete Ec2 Resources
	 * @return termination information
	 */
	public String deleteEC2() {
		//create client for EB interaction
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();
		
		//create a termination request
		TerminateEnvironmentRequest terminate = TerminateEnvironmentRequest.builder()//static builder
				.environmentName("nemo")//name of EBenvironment we want to delete
				.terminateResources(true)//tell it to terminate all Ec2 resources
				.build();//build request
		TerminateEnvironmentResponse resp = bean.terminateEnvironment(terminate);//run termination
		return resp.toString();//return termination information
	}

}
