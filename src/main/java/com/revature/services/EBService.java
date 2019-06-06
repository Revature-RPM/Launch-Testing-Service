package com.revature.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.elasticbeanstalk.ElasticBeanstalkClient;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateApplicationVersionRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentResponse;
import software.amazon.awssdk.services.elasticbeanstalk.model.S3Location;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentResponse;

@Service
public class EBService {

	public String CreateEC2() {
		String s3 = "norobotsplzdontfindme"; // These need to be passed from somewhere
		// String s3ARN = "arn:aws:s3:::expenseapplication";
		//String s3Key = "It's an s3 key!!!!";
		String versionLabel = "label1.1";
		String objectURL = "https://norobotsplzdontfindme.s3.amazonaws.com/spring-boot.jar";
		String name = "Tom is cool and so is devin";
		String stackName = "64bit Amazon Linux 2018.03 v2.8.3 running Java 8";
		
		S3Location s3Bucket = S3Location.builder()
				.s3Bucket(s3)
				.s3Key("spring-boot.jar")
				.build();
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();
		//CreateApplicationRequest
		
		// Generate and attach Procfile
		CreateApplicationVersionRequest applicationRequest = CreateApplicationVersionRequest.builder()
				.sourceBundle(s3Bucket)
				.applicationName(name)
				.versionLabel(versionLabel)
				.build();
		CreateEnvironmentRequest envRequest = CreateEnvironmentRequest.builder()
				.applicationName(name)
				.versionLabel(versionLabel)
				.environmentName("tomanddevinarecooleb")
				.solutionStackName(stackName)
				.build();
		// CreateApplicationVersionResponse resp = bean.createApplicationVersion(applicationRequest);
		CreateEnvironmentResponse resptoo = bean.createEnvironment(envRequest);//send EB request
		return resptoo.toString();//return data of provisioned environment
	}
	public String deleteEC2() {
		
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();
		
		TerminateEnvironmentRequest terminate = TerminateEnvironmentRequest.builder()
				.environmentName("tomanddevinarecooleb")
				.terminateResources(true)
				.build();
		TerminateEnvironmentResponse resp = bean.terminateEnvironment(terminate);
		return resp.toString();
	}

}
