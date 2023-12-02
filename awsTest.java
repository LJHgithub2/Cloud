/*
* Cloud Computing
* 
* Dynamic Resource Management Tool
* using AWS Java SDK Library
* 
*/

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.datamodeling.KeyPair;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeKeyPairsRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesResult;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Filter;


public class awsTest {

	static AmazonEC2 ec2;

	private static void init() throws Exception {
		//크레딧 key 입력자리(github에 업로드 X)

		
        // 다른 초기화 코드는 그대로 유지됩니다.
		ec2 = AmazonEC2ClientBuilder.standard().build();
	}

	public static void main(String[] args) throws Exception {

		init();

		Scanner menu = new Scanner(System.in);
		Scanner id_string = new Scanner(System.in);
		int number = 0;
		
		while(true)
		{
			System.out.println("                                                            ");
			System.out.println("                                                            ");
			System.out.println("------------------------------------------------------------");
			System.out.println("           Amazon AWS Control Panel using SDK               ");
			System.out.println("------------------------------------------------------------");
			System.out.println("  1. list instance                2. available zones        ");
			System.out.println("  3. start instance               4. available regions      ");
			System.out.println("  5. stop instance                6. create instance        ");
			System.out.println("  7. reboot instance              8. list images            ");
			System.out.println("  9. condor_status                10. start all instance    ");
			System.out.println("  11. stop all instance           12. terminate instance    ");
			System.out.println("  13. list SecurityGroups         14. list KeyPairs         ");
			System.out.println("  15. create KeyPairs             16. delete KeyPairs       ");
			System.out.println("                                 99. quit                   ");
			System.out.println("------------------------------------------------------------");
			
			System.out.print("Enter an integer: ");
			
			if(menu.hasNextInt()){
				number = menu.nextInt();
				}else {
					System.out.println("concentration!");
					break;
				}
			

			String instance_id = "";

			switch(number) {
			case 1: 
				listInstances();
				break;
				
			case 2: 
				availableZones();
				break;
				
			case 3: 
				System.out.print("Enter instance id: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					startInstance(instance_id);
				break;

			case 4: 
				availableRegions();
				break;

			case 5: 
				System.out.print("Enter instance id: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					stopInstance(instance_id);
				break;

			case 6: 
				System.out.print("Enter ami id: ");
				String ami_id = "";
				if(id_string.hasNext())
					ami_id = id_string.nextLine();
				
				if(!ami_id.isBlank()) 
					createInstance(ami_id);
				break;

			case 7: 
				System.out.print("Enter instance id: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					rebootInstance(instance_id);
				break;

			case 8: 
				listImages();
				break;
			case 9: 
				System.out.print("Enter instance id: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					executeCommandOnInstance(instance_id);
				break;
			case 10: 
				startAllInstances();
				break;
			case 11: 
				stopAllInstances();
				break;
			case 12:
				System.out.print("Enter instance id: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					terminateEC2(instance_id);
				break;
			case 13: 
				describeAllSecurityGroups();
				break;
			case 14: 
				describeAllKeys();
				break;
			case 15: 
				System.out.print("Enter keyname: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					createKeyPair(instance_id);
				break;
			case 16: 
				System.out.print("Enter keyname: ");
				if(id_string.hasNext())
					instance_id = id_string.nextLine();
				
				if(!instance_id.isBlank()) 
					deleteKeyPair(instance_id);
				break;


			case 99: 
				System.out.println("bye!");
				menu.close();
				id_string.close();
				return;
			default: System.out.println("concentration!");
			}

		}
		
	}

	public static void deleteKeyPair(String keyName) {
        try {
            DeleteKeyPairRequest request = new DeleteKeyPairRequest().withKeyName(keyName);
            ec2.deleteKeyPair(request);
            System.out.println("Key pair deleted successfully: " + keyName);
        } catch (Exception e) {
            System.err.println("Error deleting key pair: " + e.getMessage());
        }
    }

    public static void createKeyPair(String keyPairName) {
        try {
            CreateKeyPairRequest request = new CreateKeyPairRequest()
                    .withKeyName(keyPairName);

            CreateKeyPairResult response = ec2.createKeyPair(request);

            System.out.println("Key pair created:");
            System.out.println("Key name: " + response.getKeyPair().getKeyName());
            System.out.println("Key fingerprint: " + response.getKeyPair().getKeyFingerprint());

            // Save private key material to a file
            savePrivateKeyMaterial(response.getKeyPair().getKeyName(),response.getKeyPair().getKeyMaterial());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void savePrivateKeyMaterial(String keyName, String Material) {
        try (FileWriter writer = new FileWriter(keyName+".pem")) {
            writer.write(Material);
            System.out.println("Private key material saved " );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void describeAllKeys() {
        try {
            DescribeKeyPairsRequest request = new DescribeKeyPairsRequest();
			int keyNum=0;
            DescribeKeyPairsResult response = ec2.describeKeyPairs(request);
            for (KeyPairInfo keyPair : response.getKeyPairs()) {
				keyNum += 1;
                System.out.printf("-----------key %d ------------%nkey name : %s %n fingerprint : %s%n",
                        keyNum, keyPair.getKeyName(), keyPair.getKeyFingerprint());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void describeAllSecurityGroups() {
        try {
            DescribeSecurityGroupsRequest request = new DescribeSecurityGroupsRequest();

            DescribeSecurityGroupsResult response = ec2.describeSecurityGroups(request);
            for (SecurityGroup group : response.getSecurityGroups()) {
                System.out.println("Security Group ID: " + group.getGroupId());
                System.out.println("Group Name: " + group.getGroupName());
                System.out.println("VPC ID: " + group.getVpcId());
                System.out.println("Description: " + group.getDescription());
                System.out.println("Ingress Rules: " + group.getIpPermissions());
                System.out.println("Egress Rules: " + group.getIpPermissionsEgress());
                System.out.println("----------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void terminateEC2(String instanceId) {
		TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest()
				.withInstanceIds(instanceId);

		TerminateInstancesResult terminateResponse = ec2.terminateInstances(terminateRequest);
		System.out.println("Termination initiated for instance " + instanceId);
	}

	public static void stopAllInstances() {
		
		System.out.println("stop all instances....");
		boolean done = false;
		
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);

			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					if ("running".equals(instance.getState().getName())){
						stopInstance(instance.getInstanceId());
					}
					else{
						System.out.println(instance.getInstanceId());
					}
				}
			}

			request.setNextToken(response.getNextToken());

			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}


	public static void startAllInstances() {
		
		System.out.println("start all instances....");
		boolean done = false;
		
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);

			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					if ("stopped".equals(instance.getState().getName())){
						startInstance(instance.getInstanceId());
					}
					else{
						System.out.println(instance.getInstanceId());
					}
				}
			}

			request.setNextToken(response.getNextToken());

			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}

	public static void listInstances() {
		
		System.out.println("Listing instances....");
		boolean done = false;
		
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);

			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					System.out.printf(
						"[id] %s, " +
						"[AMI] %s, " +
						"[type] %s, " +
						"[state] %10s, " +
						"[monitoring state] %s",
						instance.getInstanceId(),
						instance.getImageId(),
						instance.getInstanceType(),
						instance.getState().getName(),
						instance.getMonitoring().getState());
				}
				System.out.println();
			}

			request.setNextToken(response.getNextToken());

			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}
	
	public static void availableZones()	{

		System.out.println("Available zones....");
		try {
			DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
			Iterator <AvailabilityZone> iterator = availabilityZonesResult.getAvailabilityZones().iterator();
			
			AvailabilityZone zone;
			while(iterator.hasNext()) {
				zone = iterator.next();
				System.out.printf("[id] %s,  [region] %15s, [zone] %15s\n", zone.getZoneId(), zone.getRegionName(), zone.getZoneName());
			}
			System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
					" Availability Zones.");

		} catch (AmazonServiceException ase) {
				System.out.println("Caught Exception: " + ase.getMessage());
				System.out.println("Reponse Status Code: " + ase.getStatusCode());
				System.out.println("Error Code: " + ase.getErrorCode());
				System.out.println("Request ID: " + ase.getRequestId());
		}
	
	}

	public static void startInstance(String instance_id)
	{
		
		System.out.printf("Starting .... %s\n", instance_id);
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DryRunSupportedRequest<StartInstancesRequest> dry_request =
			() -> {
			StartInstancesRequest request = new StartInstancesRequest()
				.withInstanceIds(instance_id);

			return request.getDryRunRequest();
		};

		StartInstancesRequest request = new StartInstancesRequest()
			.withInstanceIds(instance_id);

		ec2.startInstances(request);

		System.out.printf("Successfully started instance %s", instance_id);
	}
	
	
	public static void availableRegions() {
		
		System.out.println("Available regions ....");
		
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DescribeRegionsResult regions_response = ec2.describeRegions();

		for(Region region : regions_response.getRegions()) {
			System.out.printf(
				"[region] %15s, " +
				"[endpoint] %s\n",
				region.getRegionName(),
				region.getEndpoint());
		}
	}
	
	public static void stopInstance(String instance_id) {
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		DryRunSupportedRequest<StopInstancesRequest> dry_request =
			() -> {
			StopInstancesRequest request = new StopInstancesRequest()
				.withInstanceIds(instance_id);

			return request.getDryRunRequest();
		};

		try {
			StopInstancesRequest request = new StopInstancesRequest()
				.withInstanceIds(instance_id);
	
			ec2.stopInstances(request);
			System.out.printf("Successfully stop instance %s\n", instance_id);

		} catch(Exception e)
		{
			System.out.println("Exception: "+e.toString());
		}

	}
	
	public static void createInstance(String ami_id) {
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		
		RunInstancesRequest run_request = new RunInstancesRequest()
			.withImageId(ami_id)
			.withInstanceType(InstanceType.T2Micro)
			.withMaxCount(1)
			.withMinCount(1);

		RunInstancesResult run_response = ec2.runInstances(run_request);

		String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();

		System.out.printf(
			"Successfully started EC2 instance %s based on AMI %s",
			reservation_id, ami_id);
	
	}

	public static void rebootInstance(String instance_id) {
		
		System.out.printf("Rebooting .... %s\n", instance_id);
		
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

		try {
			RebootInstancesRequest request = new RebootInstancesRequest()
					.withInstanceIds(instance_id);

				RebootInstancesResult response = ec2.rebootInstances(request);

				System.out.printf(
						"Successfully rebooted instance %s", instance_id);

		} catch(Exception e)
		{
			System.out.println("Exception: "+e.toString());
		}

		
	}
	
	public static void listImages() {
		System.out.println("Listing images....");

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
	
		DescribeImagesRequest request = new DescribeImagesRequest();
		request.getFilters().add(new Filter().withName("name").withValues("aws-htcondor-slave"));
	
		DescribeImagesResult results = ec2.describeImages(request);
	
		for (Image image : results.getImages()) {
			System.out.printf("[ImageID] %s, [Name] %s, [Owner] %s\n",
					image.getImageId(), image.getName(), image.getOwnerId());
		}
		/*
		final AmazonEC2 ec2 = Am1azonEC2ClientBuilder.defaultClient();
		
		DescribeImagesRequest request = new DescribeImagesRequest();
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		
		request.getFilters().add(new Filter().withName("name").withValues("aws-htcondor-slave"));
		request.setRequestCredentialsProvider(credentialsProvider);
		
		DescribeImagesResult results = ec2.describeImages(request);
		
		for(Image images :results.getImages()){
			System.out.printf("[ImageID] %s, [Name] %s, [Owner] %s\n", 
					images.getImageId(), images.getName(), images.getOwnerId());
		}
		*/
	}

	public static void executeCommandOnInstance(String instanceId) {
        try {
			String command = "condor_status";
            String privateKeyPath = "C:\\project\\aws_key\\master.pem"; // 개인 키 파일 경로
            String username = "ec2-user"; // 인스턴스의 사용자 이름
            String instanceIp = getInstancePublicIp(instanceId);

            if (instanceIp != null) {
                String sshCommand = String.format(
				"ssh -i \"%s\" -o StrictHostKeyChecking=no %s@ec2-%s.us-east-2.compute.amazonaws.com \"%s\"",
				privateKeyPath, username, instanceIp, command);
				System.out.println(sshCommand);
                Process process = Runtime.getRuntime().exec(sshCommand);

                printCommandOutput(process.getInputStream());
                printCommandOutput(process.getErrorStream());

                int exitCode = process.waitFor();
                System.out.println("Command execution completed with exit code: " + exitCode);
            } else {
                System.out.println("Unable to retrieve public IP of the instance with ID: " + instanceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getInstancePublicIp(String instanceId) {
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest()
				.withInstanceIds(instanceId);

		DescribeInstancesResult describeInstancesResult = ec2.describeInstances(describeInstancesRequest);

		for (Reservation reservation : describeInstancesResult.getReservations()) {
			for (Instance instance : reservation.getInstances()) {
				if (instance.getInstanceId().equals(instanceId)) {
					return instance.getPublicIpAddress().replace('.', '-');
				}
			}
		}

		// 해당 인스턴스 ID에 대한 정보를 찾지 못한 경우
		return null;
    }

    private static void printCommandOutput(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
	