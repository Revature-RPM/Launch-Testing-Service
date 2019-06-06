package com.revature.services;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;

@Service
public class RDSService {
	
	/**
	 * Provisioning an AWS RDS instance
	 * @return the AWS response string showing all spun up server stats
	 */
	public String CreateRds() {
		//RdsClient rds = new Client();
		String instanceClass = "db.t2.micro";//required for free tier
		String instanceIdentifier = "devinandtomarecool";//name the RDS instance
		String engine = "postgres";//engine type (postgres/SQL/MySQL etc)
		RdsClient rds = RdsClient.create(); //create an empty client used for talking to AWS RDS SDK
		CreateDbInstanceRequest createDbInstanceRequest = CreateDbInstanceRequest.builder()//start creating the request
				.dbInstanceClass(instanceClass)
				.dbInstanceIdentifier(instanceIdentifier)
				.engine(engine)
				.masterUsername("dwightbrown")//admin username for the Username
				.masterUserPassword(System.getenv("JDBC_PASSWORD"))//admin password
				.allocatedStorage(20)//number of storage in GB
				.build();//formally build request
		String response = rds.createDBInstance(createDbInstanceRequest).toString();//send provision request get provision response
		return response;
	}
}
