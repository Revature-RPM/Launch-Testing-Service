package com.revature.services;


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

public RdsDTO createRds(ProjectDTO projectDTO) {
		String instanceClass = "db.t2.micro";//required for free tier
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
		CreateDbInstanceResponse response = rds.createDBInstance(createDbInstanceRequest);//send provision request get provision response
		return setRdsDTO(rds);
	}

/**
 * Checks if the RDS instance is created.The instance usually takes around 3-5 minutes to be created. Checks periodically for instance status of 
 * available. If it is available the rdsDTO is the set.
 * @param rds
 * @return RdsDTO
 */
public RdsDTO setRdsDTO(RdsClient rds) {
RdsDTO rdsDTO = new RdsDTO();
int busyWaitingTime=3000;
String status ="";
while(!status.equalsIgnoreCase("available")) {//checks the status of the RDS instance 
	try {
		Thread.sleep(busyWaitingTime);//Sleeps the function because RDS instance take 3-5 minutes to be created so it prevents a blocking process
	}catch(InterruptedException ex) {
		ex.printStackTrace();
	}
	DescribeDbInstancesResponse describeDbInstances = rds.describeDBInstances();//Gets the information about the RDS instance
    List<DBInstance> dbInstances = describeDbInstances.dbInstances();
    if(dbInstances.get(0).endpoint() !=null) {//Checks if endpoint has been created
    rdsDTO.setInstanceURL(dbInstances.get(0).endpoint().address());//Setting DTO information
    rdsDTO.setPassword(System.getenv("JDBC_PASSWORD"));
    rdsDTO.setSecurityGroup(dbInstances.get(0).vpcSecurityGroups().toString());
    rdsDTO.setUsername("dwightbrown");
    status = dbInstances.get(0).dbInstanceStatus();//Updates the status to exit the loop
    }
    busyWaitingTime = 60000;
}
return rdsDTO;
}
}

