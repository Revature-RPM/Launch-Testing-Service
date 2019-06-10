package com.revature.services;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.revature.DTOs.ProjectDTO;

import com.revature.DTOs.RdsDTO;

import software.amazon.awssdk.services.rds.RdsClient;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceRequest;
import software.amazon.awssdk.services.rds.model.CreateDbInstanceResponse;
import software.amazon.awssdk.services.rds.model.DBInstance;
import software.amazon.awssdk.services.rds.model.DescribeDbInstancesResponse;

@Service

public class RDSService {

		
		
		

	/**
	 * Provisioning an AWS RDS instance
	 * @return the AWS response string showing all spun up server stats
	 */

public RdsDTO CreateRds(ProjectDTO projectDTO) {
		//RdsClient rds = new Client();
	RdsDTO rdsDTO = new RdsDTO();
		String instanceClass = "db.t2.micro";//required for free tier
		String securityGroupName = "revaturerpmgroup";
		String securityGroupId = "190408-usf-nec-java";
		List<String> secGroup= new ArrayList();
		secGroup.add(securityGroupId);
		secGroup.add(securityGroupName);
		String instanceIdentifier = projectDTO.getInstanceId()+"-"+projectDTO.getdBLanguage();//name the RDS instance
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

		CreateDbInstanceResponse resp = rds.createDBInstance(createDbInstanceRequest);//send provision request get provision response
        
		int busyWaitingTime=3000;
            String status ="";
            while(!status.equalsIgnoreCase("available")) {
            	try {
            		Thread.sleep(busyWaitingTime);
            	}catch(InterruptedException ex) {
            		
            	}
            	DescribeDbInstancesResponse describeDbInstances = rds.describeDBInstances();
                List<DBInstance> dbInstances = describeDbInstances.dbInstances();
                if(dbInstances.get(0).endpoint() !=null) {
                
                rdsDTO.setInstanceURL(dbInstances.get(0).endpoint().address());
                rdsDTO.setPassword(System.getenv("JDBC_PASSWORD"));
                rdsDTO.setSecurityGroup(dbInstances.get(0).vpcSecurityGroups().toString());
                rdsDTO.setUsername("dwightbrown");
                status = dbInstances.get(0).dbInstanceStatus();
                }
                busyWaitingTime = 60000;
            }
            System.out.println(rdsDTO.toString());
		return rdsDTO;
	}
}

	

