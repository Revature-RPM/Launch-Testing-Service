package com.revature.services;

import org.springframework.stereotype.Service;

import com.revature.DTOs.ProjectDTO;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;

@Service
public class RDSService {
	
	public String CreateRds(ProjectDTO projectDTO) {
		//RdsClient rds = new Client();
		String instanceClass = "db.t2.micro";
		String instanceIdentifier = projectDTO.getInstanceId()+projectDTO.getdBLanguage();
		String engine = projectDTO.getLanguage();
		RdsClient rds = RdsClient.create();
		CreateDbInstanceRequest createDbInstanceRequest = CreateDbInstanceRequest.builder()
				.dbInstanceClass(instanceClass)
				.dbInstanceIdentifier(instanceIdentifier)
				.engine(engine)
				.masterUsername("dwightbrown")
				.masterUserPassword(System.getenv("JDBC_PASSWORD"))
				.allocatedStorage(20)
				.build();
		String response = rds.createDBInstance(createDbInstanceRequest).toString();
		return response;
	}
	
	public String CreateInstanceIdentifier() {
		
		
		return null;
	}
}
