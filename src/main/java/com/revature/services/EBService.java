package com.revature.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.elasticbeanstalk.ElasticBeanstalkClient;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.CreateEnvironmentResponse;
import software.amazon.awssdk.services.elasticbeanstalk.model.S3Location;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentRequest;
import software.amazon.awssdk.services.elasticbeanstalk.model.TerminateEnvironmentResponse;

@Service
public class EBService {

	public String CreateEC2() {
		String s3 = "expenseapplication"; // name of the application 
		String s3ARN = "arn:aws:s3:::expenseapplication";//arn of the S3 bucket
		//String s3Key = "It's an s3 key!!!!";//s3 key needs to be rpovided
		String versionLabel = "label1.1";//name of the version (compiled application)
		String name = "Tom is cool and so is devin";//name of the EB instance
		String stackName = "64bit Amazon Linux 2018.03 v2.8.3 running Java 8";//specific platform information
		
		
		S3Location s3Bucket = S3Location.builder()//builder pattern
				.s3Bucket(s3)//name of object in s3 bucket
				.s3Key(s3Key)//s3key to grab items
				.build();
		ElasticBeanstalkClient bean = ElasticBeanstalkClient.create();//create a generic client
		//CreateApplicationRequest 
		// Generate and attach Procfile
		/*CreateApplicationVersionRequest applicationRequest = CreateApplicationVersionRequest.builder()
				//.sourceBundle(s3Bucket)//s3 bucket object for the application
				.autoCreateApplication(true)//tells to compile application
				.versionLabel(versionLabel)//version name for use later
				.applicationName(name)//name of application
				.build();*///formal build 
		CreateEnvironmentRequest envRequest = CreateEnvironmentRequest.builder()//creation of request
				.applicationName(name)//name of application
				.versionLabel(versionLabel)//name of application version
				.environmentName("tomanddevinarecooleb")//name of spun up EC2 instance
				.solutionStackName(stackName)//environment on the EC2 instance
				.build();//formal build command
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
