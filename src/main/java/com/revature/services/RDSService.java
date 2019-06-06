package com.revature.services;


import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.revature.DTOs.ProjectDTO;

import software.amazon.awssdk.annotations.Generated;
import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.DBCluster;
import software.amazon.awssdk.services.rds.model.DBClusterEndpoint;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbClusterEndpointsRequest;
import software.amazon.awssdk.services.rds.model.DescribeDbClusterEndpointsResponse;

@Service
@Generated(value="com.amazonaws:aws-java-sdk-code-generator")
public class RDSService extends Object implements Serializable, Cloneable {

	/**
	 * Provisioning an AWS RDS instance
	 * @return the AWS response string showing all spun up server stats
	 */
public CreateDbInstanceResponse CreateRds(ProjectDTO projectDTO) {
		//RdsClient rds = new Client();
		String instanceClass = "db.t2.micro";//required for free tier
		String instanceIdentifier = projectDTO.getInstanceId()+""+projectDTO.getdBLanguage();//name the RDS instance
		String engine = projectDTO.getdBLanguage();//engine type (postgres/SQL/MySQL etc)
		RdsClient rds = RdsClient.create(); //create an empty client used for talking to AWS RDS SDK
		CreateDbInstanceRequest createDbInstanceRequest = CreateDbInstanceRequest.builder()//start creating the request
				.dbInstanceClass(instanceClass)
				.dbInstanceIdentifier(instanceIdentifier)
				.engine(engine)
				.masterUsername("dwightbrown")//admin username for the Username
				.masterUserPassword(System.getenv("JDBC_PASSWORD"))//admin password
				.allocatedStorage(20)//number of storage in GB
				.build();//formally build request
	            return rds.createDBInstance(createDbInstanceRequest);//send provision request get provision response
		
	}

	
	/*
	 * public String getRDSUrl() { String endpoint =
	 * dbInstance.endpoint().toString(); return endpoint; }
	 */
	 
	
}
