package RMC_idl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class RMCImpl extends RMCPOA {

	public static Map<String, String> MontrealRecord = Collections.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> LongueuilRecord = Collections.synchronizedMap(new HashMap<String, String>(1000));
	public static Map<String, String> BrossardRecord = Collections.synchronizedMap(new HashMap<String, String>(1000));
	
	/** UDP ports **/
	int M_UDPPort = 3000;
	int L_UDPPort = 4050;
	int B_UDPPort = 5100;
	
	/**
	 * function to get total number of records in hashmap for each police station
	 * @return
	 */
	private synchronized String getNumber(){
		String number = "";
		String id="";
		int size=1;
		int length;
		
		DatagramSocket DPISSocket = null;
		String message1 = "";
		String message2 = "";
		String message3 = "";
		String bufferData = "bdata";
		byte[] buffer = new byte[10];
		try {
			
			DPISSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			byte[]m = bufferData.getBytes();
			
			/** get count from montreal udp server **/
			DatagramPacket request1 =new DatagramPacket (m,bufferData.length(),aHost,M_UDPPort);
			DPISSocket.send(request1);
			DatagramPacket reply1 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply1);
			message1 = new String(reply1.getData());
			/** get count from longueil udp server **/
			DatagramPacket request2 =new DatagramPacket (m,bufferData.length(),aHost,L_UDPPort);
			DPISSocket.send(request2);
			DatagramPacket reply2 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply2);
			message2 = new String(reply2.getData());
			/** get count from brossard udp server **/
			DatagramPacket request3 =new DatagramPacket (m,bufferData.length(),aHost,B_UDPPort);
			DPISSocket.send(request3);
			DatagramPacket reply3 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply3);
			message3 = new String(reply3.getData());
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if(DPISSocket != null) DPISSocket.close();
		}
		
		size += Integer.parseInt(message1.replaceFirst(".*?(\\d+).*", "$1"));
		size += Integer.parseInt(message2.replaceFirst(".*?(\\d+).*", "$1"));
		size += Integer.parseInt(message3.replaceFirst(".*?(\\d+).*", "$1"));
		
		id = String.valueOf(size);

		length = 5 - id.length();
		for(int i = 0; i < length; i++) {
			number = number + "0";
		}
		number = number + id;
		return number;
	}
	
	/**
	 * function to get station prefix from badgeId
	 * @param id
	 * @return
	 */
	public static String getStation(String id) {
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		return station;
	}

	/**
	 * function to write to server log
	 * @param fileName
	 * @param mssg
	 * @param badgeId
	 * @throws SecurityException
	 */
	public static void writeToLog(String fileName, String mssg, String badgeId) throws SecurityException {
		fileName= fileName +"ServerLog.txt";	
		File log = new File(fileName);
		try {
			if(!log.exists()){
			}
			log.setWritable(true);
			Date date = new Date();
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(badgeId + "=>" + mssg + " => " + date.toString());
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch(IOException e) {
			System.out.println("Problem occurs when writing to the log file.");
		}
	}
	
	/** this function will start the UDP servers **/
	public void runServer() {
		Thread t1 = new Thread(new MUdpServer());
		Thread t2 = new Thread(new LUdpServer());
		Thread t3 = new Thread(new BUdpServer());
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	public class MUdpServer implements Runnable {
		public void run() {		
			DatagramSocket aSocket = null;
			try{
				aSocket = new DatagramSocket(M_UDPPort);
				byte[] buffer = new byte[10];
				while(true){
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket.receive(request);
					String message = String.valueOf(MontrealRecord.size());
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket.send(reply);
				}
			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket != null) aSocket.close();}
		}
		
	}
	
	public class LUdpServer implements Runnable {
		public void run() {
			DatagramSocket aSocket = null;
			try{
				aSocket = new DatagramSocket(L_UDPPort);
				byte[] buffer = new byte[10];
				while(true){
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket.receive(request);
					String message = String.valueOf(LongueuilRecord.size());
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket.send(reply);
				}
			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket != null) aSocket.close();}
		}
		
	}
	
	public class BUdpServer implements Runnable {
		public void run() {
			DatagramSocket aSocket = null;
			try {
				aSocket = new DatagramSocket(B_UDPPort);
				byte[] buffer = new byte[10];
				while(true){
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket.receive(request);
					String message = String.valueOf(BrossardRecord.size());
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket.send(reply);
				}
			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket != null) aSocket.close();}
		}
		
	}
	
	/*
	 * createCrecord Implementation
	 * @see polinfsys.PolinfsysOperations#createCRecord(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean createCRecord(String firstName, String lastName, String description, String status, String badgeId) {
		String stationPrefix = "";
		stationPrefix = getStation(badgeId);
		Boolean CreateRecordFlag = false;
		String recordID = "";
		switch (stationPrefix) {
			case "SPVM":{ //Montreal
					recordID = "CR" + getNumber();
					MontrealRecord.put(recordID, firstName + "#" + lastName + "#" + description + "#" + status);
					writeToLog("SPVM","Criminal record added.", badgeId);
					CreateRecordFlag = true; //successful.
	
					System.out.println("Criminal Record: " + recordID + " created by officer " + badgeId);
			} break;
			case "SPL": { //Longueuil
					recordID = "CR" + getNumber();
					LongueuilRecord.put(recordID, firstName + "#" + lastName + "#" + description + "#" + status);
					writeToLog("SPL","Criminal record added.", badgeId);
					CreateRecordFlag = true; //successful
					//System.out.println("CR: "+LongueuilRecord.keySet());
					System.out.println("Criminal Record: " + recordID + " created by officer " + badgeId);
			} break;
			case "SPB": { //Brossard
					recordID = "CR" + getNumber();
					BrossardRecord.put(recordID, firstName + "#" + lastName + "#" + description + "#" + status);
					writeToLog("SPB","Criminal record added.", badgeId);
					CreateRecordFlag = true; //successful

					System.out.println("Criminal Record: " + recordID + " created by officer " + badgeId);

				//}
			} break;

			default:{

			} break;
		}
		return CreateRecordFlag; //return value
	}

	/*
	 * createMRecord implementation
	 * @see polinfsys.PolinfsysOperations#createMRecord(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean createMRecord(String firstName, String lastName, String address, String lastDate,
			String lastLocation, String status, String badgeId) {

		String stationPrefix = "";
		stationPrefix = getStation(badgeId);
		Boolean CreateMRecordFlag = false;
		String recordID = "";
		
		switch (stationPrefix) {
			case "SPVM":{ //Montreal
					recordID = "MR" + getNumber();
					MontrealRecord.put(recordID, firstName + "#" + lastName + "#" + address + "#" + lastDate + "#" + lastLocation + "#" + status);
					writeToLog("SPVM","Missing record added.", badgeId);
					CreateMRecordFlag = true; //successful.

					System.out.println("Missing Record: " + recordID + " created by officer " + badgeId);

				//}
			} break;
			case "SPL": { //Longueuil
					recordID = "MR" + getNumber();
					LongueuilRecord.put(recordID, firstName + "#" + lastName + "#" + address + "#" + lastDate + "#" + lastLocation + "#" + status);
					writeToLog("SPL","Missing record added.", badgeId);
					CreateMRecordFlag = true; //successful

					System.out.println("Missing Record: " + recordID + " created by officer " + badgeId);
			} break;
			case "SPB": { //Brossard

					recordID = "MR" + getNumber();
					BrossardRecord.put(recordID, firstName + "#" + lastName + "#" + address + "#" + lastDate + "#" + lastLocation + "#" + status);
					writeToLog("SPB","Missing record added.", badgeId);
					CreateMRecordFlag = true; //successful

					System.out.println("Missing Record: " + recordID + " created by officer " + badgeId);
			} break;

			default:{

			} break;
		}
		return CreateMRecordFlag; //return value
	}


	@Override
	public String getRecordCounts(String badgeId) {
		DatagramSocket DPISSocket = null;
		String message = "";
		String bufferData = "bdata";
		byte[] buffer = new byte[10];
		try {
			
			DPISSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			byte[]m = bufferData.getBytes();
			
			/** get data from montreal udp server **/
			DatagramPacket request1 =new DatagramPacket (m,bufferData.length(),aHost,M_UDPPort);
			DPISSocket.send(request1);
			DatagramPacket reply1 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply1);
			message += "SPVM: ";
			message += new String(reply1.getData());
			message += " ";
			/** get data from longueil udp server **/
			DatagramPacket request2 =new DatagramPacket (m,bufferData.length(),aHost,L_UDPPort);
			DPISSocket.send(request2);
			DatagramPacket reply2 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply2);
			message += "SPL: ";
			message += new String(reply2.getData());
			message += " ";
			/** get data from brossard udp server **/
			DatagramPacket request3 =new DatagramPacket (m,bufferData.length(),aHost,B_UDPPort);
			DPISSocket.send(request3);
			DatagramPacket reply3 =new DatagramPacket (buffer,buffer.length);
			DPISSocket.receive(reply3);
			message += "SPB: ";
			message += new String(reply3.getData());
			message += " ";
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if(DPISSocket != null) DPISSocket.close();
		}
		System.out.println("Get Record Count: "+message);
		return message;
	}

	/*
	 * editCRecord implementation
	 * @see polinfsys.PolinfsysOperations#editCRecord(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean editCRecord (String lastName, String recordID, String newStatus, String badgeId) {
		
		Boolean GetRecordFlag = false;
		String stationPrefix = "";
		stationPrefix = getStation(badgeId);
		switch (stationPrefix) {
			case "SPVM":{ //Montreal
					if(MontrealRecord.containsKey(recordID)) {
						StringTokenizer tokens = new StringTokenizer(MontrealRecord.get(recordID), "#");
						int i = 0;
						int lngth = tokens.countTokens() - 1;
						String mod_rec = "";
						while (tokens.hasMoreElements()) {
							if(i == lngth) {
								mod_rec = mod_rec + newStatus;
							} else {
								mod_rec = mod_rec + tokens.nextElement() + "#";
							}
							i++;						   
						}
						MontrealRecord.remove(recordID);
						MontrealRecord.put(recordID, mod_rec.substring(0, mod_rec.length() -1 ));
						writeToLog("SPVM","Criminal record Modified.",badgeId);

						System.out.println("Record: " + recordID + " updated by officer " + badgeId);
						
						GetRecordFlag = true; //successful.
					} else {
						System.out.println("Record: " + recordID + " not found to be updated. Officer " + badgeId);
						GetRecordFlag = false;
					}

			} break;
			case "SPL": { //Longueuil
					if(LongueuilRecord.containsKey(recordID)) {
						StringTokenizer tokens = new StringTokenizer(LongueuilRecord.get(recordID), "#");
						int i = 0;
						int lngth = tokens.countTokens() - 1;
						String mod_rec = "";
						while (tokens.hasMoreElements()) {
							if(i == lngth) {
								mod_rec = mod_rec + newStatus;
							} else {
								mod_rec = mod_rec + tokens.nextElement() + "#";
							}
							i++;						   
						}
						LongueuilRecord.remove(recordID);
						LongueuilRecord.put(recordID, mod_rec.substring(0, mod_rec.length() -1 ));
						writeToLog("SPL","Criminal record Modified.",badgeId);

						System.out.println("Record: " + recordID + " updated by officer " + badgeId);

						GetRecordFlag = true; //successful.
					} else {
						System.out.println("Record: " + recordID + " not found to be updated. Officer " + badgeId);
						GetRecordFlag = false;
					}

			} break;
			case "SPB": { //Brossard

					if(BrossardRecord.containsKey(recordID)) {
						StringTokenizer tokens = new StringTokenizer(BrossardRecord.get(recordID), "#");
						int i = 0;
						int lngth = tokens.countTokens() - 1;
						String mod_rec = "";
						while (tokens.hasMoreElements()) {
							if(i == lngth) {
								mod_rec = mod_rec + newStatus;
							} else {
								mod_rec = mod_rec + tokens.nextElement() + "#";
							}
							i++;						   
						}
						BrossardRecord.remove(recordID);
						BrossardRecord.put(recordID, mod_rec.substring(0, mod_rec.length() -1 ));
						writeToLog("SPB","Criminal record Modified.",badgeId);

						System.out.println("Record: " + recordID + " updated by officer " + badgeId);

						GetRecordFlag = true; //successful.
					} else {
						System.out.println("Record: " + recordID + " not found to be updated. Officer " + badgeId);
						GetRecordFlag = false;
					}

			} break;

			default:{

			} break;
		}
		return GetRecordFlag; //return value
	}

	/*
	 * transferRecord implementation
	 * @see polinfsys.PolinfsysOperations#transferRecord(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean transferRecord(String badgeId, String recordID, String remoteStation) {
		Boolean GetRecordFlag = false;
		String stationPrefix = "";
		stationPrefix = getStation(badgeId);
		
		switch (stationPrefix) {
			case "SPVM":{ //Montreal
				
					if(MontrealRecord.containsKey(recordID)) {
						if(remoteStation.equals("SPL")) {
							LongueuilRecord.put(recordID, MontrealRecord.get(recordID));
							writeToLog("SPVM","Record Transferred to SPL.",badgeId);
							writeToLog("SPL","Record Transferred from SPVM.",badgeId);
				
						}
						if(remoteStation.equals("SPB")) {
							BrossardRecord.put(recordID, MontrealRecord.get(recordID));
							writeToLog("SPVM","Record Transferred to SPB.",badgeId);
							writeToLog("SPB","Record Transferred from SPVM.",badgeId);
				
						}
						MontrealRecord.remove(recordID);
																		
						System.out.println("Record: " + recordID + " Transferred to " + remoteStation + ". Officer " + badgeId);
										
						GetRecordFlag = true; //successful.
					} else {
						writeToLog("SPVM","Record Not Found to be Transferred.",badgeId);
						System.out.println("Record: " + recordID + " Not Found to be Transferred. Officer " + badgeId);
						GetRecordFlag = false;
					}
				} break;
			case "SPL": { //Longueuil
				
					if(LongueuilRecord.containsKey(recordID)) {
						if(remoteStation.equals("SPVM")) {
							MontrealRecord.put(recordID, LongueuilRecord.get(recordID));
							writeToLog("SPL","Record Transferred to SPVM.",badgeId);
							writeToLog("SPVM","Record Transferred from SPL.",badgeId);
				
						}
						if(remoteStation.equals("SPB")) {
							BrossardRecord.put(recordID, LongueuilRecord.get(recordID));
							writeToLog("SPL","Record Transferred to SPB.",badgeId);
							writeToLog("SPB","Record Transferred from SPL.",badgeId);
				
						}
						LongueuilRecord.remove(recordID);
															
						System.out.println("Record: " + recordID + " Transferred to " + remoteStation + ". Officer " + badgeId);
										
						GetRecordFlag = true; //successful.
					} else {
						writeToLog("SPL","Record Not Found to be Transferred.",badgeId);
						System.out.println("Record: " + recordID + " Not Found to be Transferred. Officer " + badgeId);
						GetRecordFlag = false;
					}
				//}
			} break;
			case "SPB": { //Brossard
					if(BrossardRecord.containsKey(recordID)) {
						if(remoteStation.equals("SPL")) {
							LongueuilRecord.put(recordID, BrossardRecord.get(recordID));
							writeToLog("SPB","Record Transferred to SPL.",badgeId);
							writeToLog("SPL","Record Transferred from SPB.",badgeId);
							//System.out.println("TR: "+LongueuilRecord.keySet());
						}
						if(remoteStation.equals("SPVM")) {
							MontrealRecord.put(recordID, BrossardRecord.get(recordID));
							writeToLog("SPB","Record Transferred to SPVM.",badgeId);
							writeToLog("SPVM","Record Transferred from SPB.",badgeId);
							//System.out.println("TR: "+MontrealRecord.keySet());
						}
						BrossardRecord.remove(recordID);
												
						System.out.println("Record: " + recordID + " Transferred to " + remoteStation + ". Officer " + badgeId);
										
						GetRecordFlag = true; //successful.
					} else {
						writeToLog("SPB","Record Not Found to be Transferred.",badgeId);
						System.out.println("Record: " + recordID + " Not Found to be Transferred. Officer " + badgeId);
						GetRecordFlag = false;
					}
				
			} break;

			default:{

			} break;
		}
		return GetRecordFlag; //return value
	}

}
