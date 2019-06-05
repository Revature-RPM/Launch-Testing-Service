package com.revature.services;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;

public class RDSService {
	
	public String CreateRds() {
		//RdsClient rds = new Client();
		String instanceClass = "db.t2.micro";
		String instanceIdentifier = "also nothing";
		String engine = "postgresql";
		RdsClient rds = RdsClient.create();
		CreateDbInstanceRequest createDbInstanceRequest = CreateDbInstanceRequest.builder()
				.dbInstanceClass(instanceClass).dbInstanceIdentifier(instanceIdentifier)
				.engine(engine).build();
		rds.createDBInstance(createDbInstanceRequest);
		return null;
	}
}
