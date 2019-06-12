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

	public String CreateEC2() {
		String s3 = "norobotsplzdontfindme"; // This will eventually be the project s3
		// String s3ARN = "arn:aws:s3:::expenseapplication";
		//String s3Key = "It's an s3 key!!!!";
		String versionLabel = "procfilewithebex1.5";
		String objectURL = "https://norobotsplzdontfindme.s3.amazonaws.com/spring-boot.jar";
		String name = "spring-boot";
		String stackName = "64bit Amazon Linux 2018.03 v2.8.3 running Java 8";
		
		S3Location s3Bucket = S3Location.builder()
				.s3Bucket(s3)
				.s3Key("springboot.zip")
				.build();
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();
		//CreateApplicationRequest
		//CreateApplicationRequest req = CreateApplicationRequest.builder()
		//		.applicationName(name)
		//		.build();
		// Generate and attach Procfile
		//bean.createApplication(req);
		//AuthorizeDbSecurityGroupIngressResponse
		CreateApplicationVersionRequest applicationRequest = CreateApplicationVersionRequest.builder()
				.sourceBundle(s3Bucket)
				.applicationName(name)
				.versionLabel(versionLabel)
				.build();
		bean.createApplicationVersion(applicationRequest);
		CreateEnvironmentRequest envRequest = CreateEnvironmentRequest.builder()
				.applicationName(name)
				.versionLabel(versionLabel)
				.environmentName("nemooo")
				.solutionStackName(stackName)
				.build();
		// CreateApplicationVersionResponse resp = bean.createApplicationVersion(applicationRequest);
		CreateEnvironmentResponse resptoo = bean.createEnvironment(envRequest);//send EB request
		return resptoo.toString();//return data of provisioned environment
	}
	public String deleteEC2() {
		
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();
		
		TerminateEnvironmentRequest terminate = TerminateEnvironmentRequest.builder()
				.environmentName("nemo")
				.terminateResources(true)
				.build();
		TerminateEnvironmentResponse resp = bean.terminateEnvironment(terminate);
		return resp.toString();
	}

}
