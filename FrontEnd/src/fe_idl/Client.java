package fe_idl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

public class Client {

	/**
	 * @param args
	 */
	/** displays Menu **/
	public static void showMenu() {
		System.out.println("Please select an option (1-6)");
		System.out.println("1. Create a new Criminal Record.");
		System.out.println("2. Create a new Missing Record.");
		System.out.println("3. Edit an existing Criminal Record.");
		System.out.println("4. View Total Records.");
		System.out.println("5. Transfer Record");
		System.out.println("6. Exit");
	}

	/** LOGing is done here **/
	public static void logFile(String fileName, String Operation)
			throws SecurityException {
		fileName = fileName + "Log.txt";
		File log = new File(fileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Operation + " " + dateFormat.format(date));
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("COULD NOT LOG!!");
		}
	}

	/** Verifies badgeID **/
	public static boolean verifyId(String id) {
		Pattern p1 = Pattern.compile("SPL[0-9]{4}");
		Pattern p2 = Pattern.compile("SPB[0-9]{4}");
		Pattern p3 = Pattern.compile("SPVM[0-9]{4}");
		Matcher m1 = p1.matcher(id);
		Matcher m2 = p2.matcher(id);
		Matcher m3 = p3.matcher(id);
		if (m1.matches() || m2.matches() || m3.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/** Verifies Criminal Record id **/
	public static boolean verifyRecordId(String id) {
		Pattern p1 = Pattern.compile("CR[0-9]{5}");
		Matcher m1 = p1.matcher(id);
		if (m1.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/** Main function **/
	public static void main(String[] args) throws IOException {
		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader("ior.txt"));
		String spvmior = br.readLine();
		br.close();

		org.omg.CORBA.Object o = orb.string_to_object(spvmior);

		fe FeObj = feHelper.narrow(o);
		

		String badgeId, lastName = "", firstName = "", description = "", status = "", address = "", lastDate = "", lastLocation = "", recordId = "", message = "", remoteStation = "",operation = "";
		int choice = 0;
		String result = "";

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("\n****Welcome to Police Information System****\n");
		while (true) {
			System.out.println("Enter Your Badge ID:");
			badgeId = input.next();
			if (verifyId(badgeId) == true) {
				showMenu();
				while (true) {
					Boolean valid = false;
					
					while (!valid) {
						try {
							choice = input.nextInt();
							valid = true;
						} catch (Exception e) {
							System.out
									.println("Invalid Input, please enter an Integer");
							valid = false;
							input.nextLine();
						}
					}
					switch (choice) {
					case 1: // creates CRecord
						System.out.println("Enter First name");
						firstName = input.next();
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter Description");
						description = input.next();
						System.out.println("Enter Status");
						status = input.next();
						operation = "createCR";

						if (status.equals("captured") || status.equals("free")) {
								result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);
										logFile(badgeId, result);
									showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 2: // Creates MRecord
						System.out.println("Enter First name");
						firstName = input.next();
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter address");
						address = input.next();
						System.out.println("Enter last date seen");
						lastDate = input.next();
						System.out.println("Enter Last Location");
						lastLocation = input.next();
						System.out.println("Enter Status");
						status = input.next();
						operation = "createMR";
						if (status.equals("found") || status.equals("notfound")) {
							result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);
								logFile(badgeId, result);
							
							showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 3: // Edit Crecord
						System.out.println("Enter Last name");
						lastName = input.next();
						System.out.println("Enter Record ID");
						recordId = input.next();
						System.out.println("Enter Status");
						status = input.next();
						operation = "EditCR";
						if (verifyRecordId(recordId) == false) {
							System.out.println("Invalid ID format");
							showMenu();
							break;
						}
						if (status.equals("captured") || status.equals("free")) {
							result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);
								logFile(badgeId, "Edit criminal successful");
							showMenu();
							break;
						} else {
							System.out.println("Invalid Status");
							showMenu();
							break;
						}
					case 4: // Displays record count
						operation = "display";
						result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);

						System.out.println(result);
						logFile(badgeId, result);
						showMenu();
						break;
					case 5: // transfers record
						System.out.println("Enter Record ID:");
						recordId = input.next();
						System.out.println("Enter Remote Station:");
						remoteStation = input.next();
						operation = "transfer";
						if (verifyRecordId(recordId) == false) {
							System.out.println("Invalid ID format");
							showMenu();
							break;
						}
						result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);
						logFile(badgeId, result);
								showMenu();
						break;
					case 6: // exit
						System.exit(0);
					default:
						System.out.println("Invalid Input, please try again.");
					}
				}
			} else
				System.out.println("invalid ID");
		}
	}
}
