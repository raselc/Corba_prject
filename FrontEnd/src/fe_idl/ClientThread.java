package fe_idl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.ORB;

public class ClientThread implements Runnable {

	/**
	 * @param args
	 */

	String badgeId = "";
	String lastName = "";
	String firstName = "";
	String description = "";
	String status = "";
	String address = "";
	String lastDate = "";
	String lastLocation = "";
	String recordId = "";
	String message = "";
	String remoteStation = "";
	String operation = "";
	private String[] args;

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

	// Get station from id
	public static String getStation(String id) {
		// System.out.println("get station");
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		// System.out.println("found station");
		return station;
	}

	public ClientThread(String badgeId, String firstName, String lastName,
			String description, String address, String lastDate,
			String lastLocation, String status, String recordID,
			String remoteStation, String operation) {
		this.badgeId = badgeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.address = address;
		this.lastDate = lastDate;
		this.lastLocation = lastLocation;
		this.status = status;
		this.recordId = recordID;
		this.remoteStation = remoteStation;
		this.operation = operation;
	}

	public void run() {
		ORB orb = ORB.init(args, null);
		BufferedReader br;
		String result = null;
		try {
			br = new BufferedReader(new FileReader("ior.txt"));

			String spvmior = br.readLine();
			br.close();
			org.omg.CORBA.Object o = orb.string_to_object(spvmior);
			fe FeObj = feHelper.narrow(o);
			result = FeObj.operation(badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordId, remoteStation, operation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		logFile(badgeId, result);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// create crecord
		Thread client1 = new Thread(new ClientThread("SPVM1001", "fname1", "lname1",	"descrip1", "", "", "", "free", "", "", "createCR"));
		Thread client2 = new Thread(new ClientThread("SPL1001", "fname2", "lname2",	"descrip2", "", "", "", "free", "", "", "createCR"));
		Thread client3 = new Thread(new ClientThread("SPB1001", "fname3", "lname3",	"descrip3", "", "", "", "free", "", "", "createCR"));
		Thread client4 = new Thread(new ClientThread("SPVM1002", "fname4", "lname4",	"descrip4", "", "", "", "free", "", "", "createCR"));

		// create mrecord
		Thread client5 = new Thread(new ClientThread("SPVM1002", "fname5", "lname5",
				"", "address5", "121112", "lastloc1", "notfound", "", "", "createMR"));
		Thread client6 = new Thread(new ClientThread("SPL1003", "fname6", "lname6",
				"", "address6", "121112", "lastloc1", "found", "", "", "createMR"));
		Thread client7 = new Thread(new ClientThread("SPB1004", "fname7", "lname7",
				"", "address7", "121112", "lastloc1", "notfound", "", "", "createMR"));
		Thread client8 = new Thread(new ClientThread("SPVM1001", "fname8", "lname8",
				"", "address8", "121112", "lastloc1", "found", "", "", "createMR"));

		// edit crecord
		Thread client9 = new Thread(new ClientThread("SPVM0001", "", "lname", "",
				"", "", "", "captured", "CR00001", "", "EditCR"));
		Thread client10 = new Thread(new ClientThread("SPL0002", "", "lname10", "",
				"", "", "", "captured", "CR0002", "", "EditCR"));
		Thread client11 = new Thread(new ClientThread("SPB0003", "", "lname11", "",
				"", "", "", "captured", "CR00003", "", "EditCR"));
		Thread client12 = new Thread(new ClientThread("SPVM0004", "", "lname12", "",
				"", "", "", "captured", "CR00004", "", "EditCR"));

		// view record
		Thread client13 = new Thread(new ClientThread("SPVM9999", "", "", "", "", "",
				"", "", "", "", "display"));
		Thread client14 = new Thread(new ClientThread("SPL0001", "", "", "", "", "",
				"", "", "", "", "display"));
		Thread client15 = new Thread(new ClientThread("SPB2001", "", "", "", "", "",
				"", "", "", "", "display"));
		Thread client16 = new Thread(new ClientThread("SPVM3001", "", "", "", "",
				"", "", "", "", "", "display"));

		// transfer record
		Thread client17 = new Thread(new ClientThread("SPVM1001", "", "", "", "",
				"", "", "", "CR00001", "SPL", "transfer"));
		Thread client18 = new Thread(new ClientThread("SPB1004", "", "", "", "", "",
				"", "", "CR10001", "SPB", "transfer"));
		Thread client19 = new Thread(new ClientThread("SPL1003", "", "", "", "", "",
				"", "", "CR10001", "SPL", "transfer"));
		Thread client20 = new Thread(new ClientThread("SPVM1031", "", "", "", "",
				"", "", "", "CR10001", "SPVM", "transfer"));

		client1.start();
		client2.start();
		client3.start();
		client4.start();

		client5.start();
		client6.start();
		client7.start();
		client8.start();

		client9.start();
		client10.start();
		client11.start();
		client12.start();

		client13.start();
		client14.start();
		client15.start();
		client16.start();

		client17.start();
		client18.start();
//		client19.start();
//		client20.start();
	}

}
