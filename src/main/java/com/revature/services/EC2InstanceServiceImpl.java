package com.revature.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairResponse;
import software.amazon.awssdk.services.ec2.model.CreateSecurityGroupRequest;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.IpPermission;
import software.amazon.awssdk.services.ec2.model.IpRange;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Tag;

/**
 * This service is going to be responsible for spinning up an EC2 instance based on the
 * given configuration.
 * 
 * The code was based on the examples from the official AWS SDK documentation.
 * https://github.com/awsdocs/aws-doc-sdk-examples
 * 
 * @author Java, MAY 19 - USF
 *
 */
@Component
public class EC2InstanceServiceImpl implements EC2InstanceService {
	
	private String ec2TagName; // Tag for EC2 instances
	private String amiId; // Machine image for this EC2
	private String securityGroupName; // Security group name
	private String keyName; // Key name to secure the EC2 and generate the .PEM file
	private String vpcId; // VPC id for this EC2
	
	private Ec2Client ec2Client; // EC2 client
	private String securityGroupDescription = "Revature RPM"; // This value is just informational

	@Autowired
	public void setEc2Client(Ec2Client ec2Client) {
		this.ec2Client = ec2Client;
	}
	
	@Value("${aws.config.ec2-tag-name}")
	public void setEc2TagName(String ec2TagName) {
		this.ec2TagName = ec2TagName;
	}

	@Value("${aws.config.ec2-ami-id}")
	public void setAmiId(String amiId) {
		this.amiId = amiId;
	}

	@Value("${aws.config.ec2-group-name}")
	public void setSecurityGroupName(String securityGroupName) {
		this.securityGroupName = securityGroupName;
	}

	@Value("${aws.config.ec2-key-name}")
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Value("${aws.config.ec2-vpc-id}")
	public void setVpcId(String vpcId) {
		this.vpcId = vpcId;
	}

	/**
	 * This method is used to spin up a new EC2 instance every time a project gets approved.
	 * In order to spin up a new EC2 instance, we need to go through many steps:
	 * 1. Create a new security group for our EC2.
	 * 2. Create access rules for our EC2 (port 80, 5432, 22).
	 * 3. Create key pairs to secure our EC2 instances and to generate a .PEM file in case we
	 * 	  want to access our EC2 using SSH.
	 * 4. Create a new EC2 instance, we need to specify:
	 * 	 	a) The machine image.
	 * 		b) The type of instance.
	 * 		c) The key name (key pair).
	 * 		d) The security group (s) (in this case we use the name).
	 * 		e) The user data (bash script). This contains all the commands that are going to be run
	 * 		   when creating our new EC2 instance. They need to be encoded using Base64.
	 * 		   This script will install docker, grab the docker files for the project from our
	 * 		   S3 bucket, build the docker containers and run them.
	 * 
	 * From this method we are able to return the instance id of the newly created EC2, this way
	 * we will be able to obtain the public DNS for this instance.
	 * 
	 */
	@Override
	public String spinUpEC2Instance(String bashScript) throws UnsupportedEncodingException {
		
		// Create security group for this EC2
		createSecurityGroupRequest();
		
		// Create access rules for accessing the EC2
		createEC2AccessRules();
		        
		// Create key pair request and store .PEM file in case we need to access the EC2 using ssh
		createKeyPairRequest();
				
		// Create instance in ec2
		RunInstancesRequest runInstanceRequest = RunInstancesRequest.builder()
				.imageId(amiId)
				.instanceType(InstanceType.T2_MICRO)
				.maxCount(1)
				.minCount(1)
				.keyName(keyName)
				.securityGroups(securityGroupName)
				.userData(Base64.getEncoder().encodeToString(bashScript.getBytes("UTF-8"))).build();

		// Run instance
		RunInstancesResponse runInstanceResponse = ec2Client.runInstances(runInstanceRequest);

		// Getting the newly instance id
		String instanceId = runInstanceResponse.instances().get(0).instanceId();

		// Add tag to the instance
		addTagToInstance(instanceId);

		return instanceId;
	}

	/**
	 * Add a tag to the EC2 instance.
	 * @param instanceId Instance id.
	 */
	private void addTagToInstance(String instanceId) {
		Tag tag = Tag.builder().key("Name").value(ec2TagName).build();

		CreateTagsRequest tag_request = CreateTagsRequest.builder()
				.resources(instanceId)
				.tags(tag)
				.build();

		try {
			ec2Client.createTags(tag_request);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Create key-pair object to secure our EC2 and generate .PEM file in case we need to
	 * connect to our EC2 using SSH. Currently we are using the same key pair to secure all our
	 * EC2 instances, but if we want to secure our EC2 better we can generate our key pair
	 * using random values (perhaps using a library like StringUtils from Apache).
	 */
	private void createKeyPairRequest() {
		CreateKeyPairRequest createKeyPairRequest =  CreateKeyPairRequest.builder()
				.keyName(keyName).build();
		
		try {
			
			CreateKeyPairResponse createKeyPairResponse = ec2Client .createKeyPair(createKeyPairRequest);

			try (PrintStream out = new PrintStream(new FileOutputStream(keyName + ".pem"))) {
				// Saving the .pem file in case we need to login the system using ssh 
				out.print(createKeyPairResponse.keyMaterial());
			} catch (FileNotFoundException e1) {
				// TODO Handle file exception
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// We just catch this exception because the key pair already exist
			// we do not need to do anything
			System.out.println("Pair response already exist.");
		}
	}

	/**
	 * Established access rules for this EC2. We allow external connections from port 80, 22
	 * and 5432. This way, we are able to access our web server, our EC2 using SSH and our PEM
	 * file and our database server (PostgreSQL).
	 * 
	 * If the access rules already exist, we just catch them to avoid our program from crashing.
	 */
	private void createEC2AccessRules() {
		// Creating rules to access the ec2 instance from outside
		IpRange ip_range = IpRange.builder()
		    .cidrIp("0.0.0.0/0").build();

		IpPermission ip_perm = IpPermission.builder()
		    .ipProtocol("tcp")
		    .toPort(80)
		    .fromPort(80)
		    .ipRanges(ip_range)
		    .build();

		IpPermission ip_perm2 = IpPermission.builder()
		    .ipProtocol("tcp")
		    .toPort(22)
		    .fromPort(22)
		    .ipRanges(ip_range)
		    .build();
		
		IpPermission ip_perm3 = IpPermission.builder()
		        .ipProtocol("tcp")
		        .toPort(5432)
		        .fromPort(5432)
		        .ipRanges(ip_range)
		        .build();

		AuthorizeSecurityGroupIngressRequest authSecGroupInRequest = AuthorizeSecurityGroupIngressRequest.builder()
		        .groupName(securityGroupName)
		        .ipPermissions(ip_perm, ip_perm2, ip_perm3)
		        .build();

		try {
			// Creating rule for the ec2 instance
			ec2Client.authorizeSecurityGroupIngress(authSecGroupInRequest);
		} catch (Exception e) {
			// We just catch this exception because the rules already exist
			// we do not need to do anything
			System.out.println("Rule already exist.");
		}
	}

	/**
	 * Creating security group for the EC2 instances that will be created.
	 * If the security group already exist, this will throw an exception. We just need to catch
	 * the exception because we don't want our program to crash, we just reuse the existing one.
	 */
	private void createSecurityGroupRequest() {
		CreateSecurityGroupRequest createSecurityGroupRequest = CreateSecurityGroupRequest.builder()
				.groupName(securityGroupName)
				.description(securityGroupDescription)
				.vpcId(vpcId)
				.build();
		
		try {
			// Creating Security Group
			ec2Client.createSecurityGroup(createSecurityGroupRequest);
		} catch (Exception e) {
			// We just catch this exception because the security group already exist
			// we do not need to do anything
			System.out.println("Security group already exist.");
		}
	}

	/**
	 * Get the new public DNS to for the EC2 instance.
	 * Because spinning up a new EC2 instance takes time, plus the time that we add, when we
	 * install docker, download the necessary files to create our containers, download
	 * the dependencies for our projects using Maven, we are not able to obtain the public DNS
	 * of our new EC2, in which we are deploying our project, right away. Then, we need to get
	 * this value later on once our EC2 is ready to be accessed.
	 * 
	 * Once solution to this problem might be to create a custom amazon machine with all the
	 * software that we need already installed. This way we might be able to speed up this process.
	 * 
	 * @param instaceId EC2 instance id
	 */
	@Override
	public String getEC2InstancePublicDNS(String instaceId) {
		
		String ec2PublicDns = "";
		
		String nextToken = null;
		
        do {
            DescribeInstancesRequest desRequest = DescribeInstancesRequest.builder()
            		.nextToken(nextToken).build();
            
            DescribeInstancesResponse desResponse = ec2Client.describeInstances(desRequest);

            for (Reservation reservation : desResponse.reservations()) {
                for (Instance instance : reservation.instances()) {
                	if (instance.instanceId().equals(instaceId)) {
						
                		ec2PublicDns = instance.publicDnsName();
					}
                }
            }
            
            nextToken = desResponse.nextToken();


        } while (nextToken != null);
        
        return ec2PublicDns;
	}

}
