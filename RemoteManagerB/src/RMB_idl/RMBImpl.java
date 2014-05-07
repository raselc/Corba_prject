package RMB_idl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;



public class RMBImpl extends RMBPOA {

	public static Map<String, String> MontrealStation = Collections
			.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> LongueuilStation = Collections
			.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> BrossardStation = Collections
			.synchronizedMap(new HashMap<String, String>(1000));

	// private static final long serialVersionUID = 1L;

	int SPLserverPort = 3001;
	int SBPserverPort = 4000;
	int SPVMserverPort = 5000;

	/*
	 * protected StationServer() throws RemoteException { super(); // TODO
	 * Auto-generated constructor stub }
	 */

	private synchronized String generateId() {
		String number = "";
		String id = "";
		String message = "";

		int size = 1;
		int length;
		
		size += MontrealStation.size();
	
		size += LongueuilStation.size();
		
		size += BrossardStation.size();
		
		id = String.valueOf(size);
		length = 5 - id.length();
		for (int i = 0; i < length; i++) {
			number = number + "0";
		}
		number = number + id;
		return number;
	}

	public static void logFile(String fileName, String Operation, String badgeId)
			throws SecurityException {

		fileName = fileName + "ServerLog.txt";
		File log = new File(fileName);
		try {
			if (!log.exists()) {
			}
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(badgeId + " " + Operation);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Logging was Unsuccessful!!");
		}
	}

	// This function gets the station from the BadgeID 
	public static String getStation(String id) {

		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		return station;
	}

	//This function gets the key from a given value
	static String getKey(Map<String, String> record, String value) {

		String key = null;
		String hashValue = null;
		for (Map.Entry<String, String> entry : record.entrySet()) {
			StringTokenizer tokens = new StringTokenizer(entry.getValue());
			hashValue = tokens.nextToken();
			if ((value == null && entry.getValue() == null)
					|| (value != null && value.equals(hashValue))) {
				key = entry.getKey();
				break;
			}
		}
		return key;
	}

	@Override
	public boolean createCRecord(String firstName, String lastName,
			String description, String status, String badgeID) {
		// TODO Auto-generated method stub
		Boolean CreateRecordFlag = false;
		String recordId = "";

		String station;
		station = getStation(badgeID);

		switch (station) {
		// In the case of creating a criminal record in Montreal station//
		case "SPVM": {
			
			recordId = "CR" + generateId();
			MontrealStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + description + " " + status);
			CreateRecordFlag = true; // successful.
			logFile("MontrealStation", "A Criminal Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status
					+ " Crime Description: " + description, badgeID);
			System.out.println(MontrealStation.values());
			
		}
			break;

		// In the case of creating a criminal record in Longueuil station//
		case "SPL": {
			
			recordId = "CR" + generateId();
			LongueuilStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + description + " " + status);
			CreateRecordFlag = true; // successful
			logFile("LongueuilStation", "A Criminal Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status
					+ " Crime Description: " + description, badgeID);
			System.out.println(LongueuilStation.values());
			
		}
			break;

		// In the case of creating a criminal record in Brossard station//
		case "SPB": {
			
			recordId = "CR" + generateId();
			BrossardStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + description + " " + status);
			CreateRecordFlag = true; // successful
			logFile("BrossardStation", "A Criminal Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status
					+ " Crime Description: " + description, badgeID);
			System.out.println(BrossardStation.values());
			
		}
			break;

		default: {

		}
			break;
		}
		return CreateRecordFlag; // return value successful or unsuccessful//
		// return false;
	}

	@Override
	public boolean createMRecord(String firstName, String lastName,
			String address, String lastdate, String lastaddress, String status,
			String badgeID) {
		// TODO Auto-generated method stub
		Boolean CreateRecordFlag = false;
		String recordId = "";
		String station;
		station = getStation(badgeID);

		switch (station) {

		// In the case of creating a criminal record in Montreal station//
		case "SPVM": {
			
			recordId = "MR" + generateId();
			MontrealStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + address + " " + " " + lastdate + " "
					+ lastaddress + " " + status);
			CreateRecordFlag = true; // successful.
			logFile("MontrealStation", "A Missing Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status,
					badgeID);
			System.out.println(MontrealStation.values());
			
		}
			break;

		// In the case of creating a criminal record in Longueuil station//
		case "SPL": {
			
			recordId = "MR" + generateId();
			LongueuilStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + address + " " + " " + lastdate + " "
					+ lastaddress + " " + status);
			CreateRecordFlag = true; // successful
			logFile("LongueuilStation", "A Missing Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status,
					badgeID);
			System.out.println(LongueuilStation.values());
			
		}
			break;

		// In the case of creating a criminal record in Brossard station//
		case "SPB": {
			
			recordId = "MR" + generateId();
			BrossardStation.put(lastName, recordId + " " + firstName + " "
					+ lastName + " " + address + " " + " " + lastdate + " "
					+ lastaddress + " " + status);
			CreateRecordFlag = true; // successful
			logFile("BrossardStation", "A Missing Record was Created! Name:"
					+ firstName + " " + lastName + " Status: " + status,
					badgeID);
			System.out.println(BrossardStation.values());
			
		}
			break;

		default: {

		}
			break;
		}
		return CreateRecordFlag; // return value successful or unsuccessful//

		// return false;
	}

	public class SPVMUdpServer implements Runnable {

		@Override
		public void run() {

			System.out.println("Welcome to MONTREAL STATION!");
			// TODO Auto-generated method stub

			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPVMserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPVM - "
							+ String.valueOf(MontrealStation.size()) + " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	// While other server send request to Longueuil server to give the record
	// count //
	public class SPLUdpServer implements Runnable {

		@Override
		public void run() {
			System.out.println("Welcome to LONGUEUIL STATION!");
			// TODO Auto-generated method stub
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SPLserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPL - "
							+ String.valueOf(LongueuilStation.size()) + " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
					// System.out.println(message);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	// While other server send request to Brossard server to give the record
	// count //

	public class SBPUdpServer implements Runnable {

		@Override
		public void run() {
			System.out.println("Welcome to BROSSARD STATION!");
			// TODO Auto-generated method stub
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(SBPserverPort);
				byte[] buffer = new byte[10];
				while (true) {
					DatagramPacket request = new DatagramPacket(buffer,
							buffer.length);
					aSocket.receive(request);
					String message = "SPB - "
							+ String.valueOf(BrossardStation.size()) + " ";
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,
							buffer.length, request.getAddress(),
							request.getPort());
					aSocket.send(reply);
				}
			} catch (SocketException e) {
				System.out.println("Socket: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO: " + e.getMessage());
			} finally {
				if (aSocket != null)
					aSocket.close();
			}
		}

	}

	public void runThreads() {
		Thread Montreal = new Thread(new SPVMUdpServer());
		Thread Longueuil = new Thread(new SPLUdpServer());
		Thread Brossard = new Thread(new SBPUdpServer());

		Montreal.start();
		Longueuil.start();
		Brossard.start();
	}

	@Override
	public String getRecordCounts(String badgeID) {
		// TODO Auto-generated method stub
		DatagramSocket DPISSocket = null;

		String message = "";
		byte[] buffer = new byte[10];
		String send = "send count";
		try {
			DPISSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			byte[] m = send.getBytes();

			DatagramPacket request1 = new DatagramPacket(m, send.length(),
					aHost, SPVMserverPort);
			DPISSocket.send(request1);
			DatagramPacket reply1 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply1);
			message += new String(reply1.getData());

			DatagramPacket request2 = new DatagramPacket(m, send.length(),
					aHost, SPLserverPort);
			DPISSocket.send(request2);
			DatagramPacket reply2 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply2);
			message += new String(reply2.getData());

			DatagramPacket request3 = new DatagramPacket(m, send.length(),
					aHost, SBPserverPort);
			DPISSocket.send(request3);
			DatagramPacket reply3 = new DatagramPacket(buffer, buffer.length);
			DPISSocket.receive(reply3);
			message += new String(reply3.getData());

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (DPISSocket != null)
				DPISSocket.close();
		}

		return message;
	}

	@Override
	public boolean editCRecord(String lastName, String recordID,
			String newStatus, String badgeID) {
		// TODO Auto-generated method stub
		String station;
		station = getStation(badgeID);
		Boolean GetRecordFlag = false;

		switch (station) {

		// In the case of creating a criminal record in Montreal station//
		case "SPVM": { // Montreal
		
			if (MontrealStation.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						MontrealStation.get(lastName));
				int i = 0;
				int length = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == length) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				MontrealStation.remove(lastName);
				MontrealStation.put(lastName,
						mod_rec.substring(0, mod_rec.length()));
				System.out.println(MontrealStation.values());
				GetRecordFlag = true; // successful.
				logFile("MontrealStation", "Successfully Edited the Record! New Status: "+ newStatus, badgeID);
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;

		// In the case of editing a criminal record in Longueuil station//
		case "SPL": {
			
			if (LongueuilStation.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						LongueuilStation.get(lastName));
				int i = 0;
				int lngth = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == lngth) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				LongueuilStation.remove(lastName);
				LongueuilStation.put(lastName,
						mod_rec.substring(0, mod_rec.length()));
				System.out.println(LongueuilStation.values());
				GetRecordFlag = true; // successful.
				logFile("LongueuilStation", "Successfully Edited the Record! New Status: "+ newStatus, badgeID);
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;
		// In the case of editing a criminal record in Brossard station//
		case "SPB": {
			
			if (BrossardStation.containsKey(lastName)) {
				StringTokenizer tokens = new StringTokenizer(
						BrossardStation.get(lastName));
				int i = 0;
				int lngth = tokens.countTokens() - 1;
				String mod_rec = "";
				while (tokens.hasMoreElements()) {
					if (i == lngth) {
						mod_rec = mod_rec + newStatus;
						break;
					} else {
						mod_rec = mod_rec + tokens.nextElement() + " ";
					}
					i++;
				}
				BrossardStation.remove(lastName);
				BrossardStation.put(lastName,
						mod_rec.substring(0, mod_rec.length()));
				System.out.println(BrossardStation.values());
				logFile("BrossardStation", "Successfully Edited the Record! New Status: "+ newStatus, badgeID);
				GetRecordFlag = true; // successful.
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;
		default: {

		}
			break;
		}
		return GetRecordFlag; // return value successful or unsuccessful//
		// return false;
	}

	@Override
	public boolean transferRecord(String recordID, String bagdeID,
			String remoteStation) {
		// TODO Auto-generated method stub
		Boolean GetRecordFlag = false;
		String station;
		String key;
		station = getStation(bagdeID);

		switch (station) {
		case "SPVM": { // Montreal
			key = getKey( MontrealStation, recordID);
			
			if (MontrealStation.containsKey(key)) {
				if (remoteStation.equals("SPL")) {
					LongueuilStation.put(key, MontrealStation.get(key));
					logFile("MontrealStation",
							"Record Transferred to Longueuil!", bagdeID);
					logFile("LongueuilStation",
							"Record Transferred from Montreal!", bagdeID);
					System.out.println(LongueuilStation.values());
				}
				if (remoteStation.equals("SPB")) {
					BrossardStation.put(key, MontrealStation.get(key));
					logFile("MontrealStation",
							"Record Transferred to Brossard!", bagdeID);
					logFile("BrossardStation",
							"Record Transferred from Montreal!", bagdeID);
					System.out.println(BrossardStation.values());
				}
				MontrealStation.remove(key);

				System.out.println(MontrealStation.values());
				GetRecordFlag = true; // successful.
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;
		case "SPL": { // Longueuil
			key = getKey(LongueuilStation, recordID);
			
			if (LongueuilStation.containsKey(key)) {
				if (remoteStation.equals("SPVM")) {
					MontrealStation.put(key, LongueuilStation.get(key));
					logFile("LongueuilStation",
							"Record Transferred to Montreal!", bagdeID);
					logFile("MontrealStation",
							"Record Transferred from Longueuil!", bagdeID);
					System.out.println(MontrealStation.values());
				}
				if (remoteStation.equals("SPB")) {
					BrossardStation.put(key, LongueuilStation.get(key));
					logFile("LongueuilStation",
							"Record Transferred to Brossard!", bagdeID);
					logFile("BrossardStation",
							"Record Transferred from Longueuil!", bagdeID);
					System.out.println(BrossardStation.values());
				}
				LongueuilStation.remove(key);

				System.out.println(LongueuilStation.values());
				GetRecordFlag = true; // successful.
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;
		case "SPB": { // Brossard
			key = getKey(BrossardStation, recordID);
			
			if (BrossardStation.containsKey(key)) {
				if (remoteStation.equals("SPL")) {
					LongueuilStation.put(key, BrossardStation.get(key));
					logFile("BrossardStation",
							"Record Transferred to Longueuil!", bagdeID);
					logFile("LongueuilStation",
							"Record Transferred from Brossard!", bagdeID);
					System.out.println(LongueuilStation.values());
				}
				if (remoteStation.equals("SPVM")) {
					MontrealStation.put(key, BrossardStation.get(key));
					logFile("BrossardStation",
							"Record Transferred to Montreal.", bagdeID);
					logFile("MontrealStation",
							"Record Transferred from Brossard!", bagdeID);
					System.out.println(MontrealStation.values());
				}
				BrossardStation.remove(key);

				System.out.println(BrossardStation.values());
				GetRecordFlag = true; // successful.
			} else {
				GetRecordFlag = false;
			}
			
		}
			break;

		default: {

		}
			break;
		}
		return GetRecordFlag; // return value
		// return false;
	}

}
