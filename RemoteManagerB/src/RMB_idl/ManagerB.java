package RMB_idl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.omg.CORBA.ORB;


public class ManagerB {
	private static final String[] args = null;
	int RMreply = 5001;
	static String RMID = "RM2";
	int prevSequence = 0;
	RMB spvmObj= null;
	RMB splObj = null;
	RMB spbObj = null;
	//initializes the replica
	public void initializeReplica() throws IOException {
		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader("spvm.txt"));
		String spvm = br.readLine();
		br.close();
		org.omg.CORBA.Object o = orb.string_to_object(spvm);
		spvmObj = RMBHelper.narrow(o);

		BufferedReader br1 = new BufferedReader(new FileReader("spl.txt"));
		String spl = br1.readLine();
		br1.close();
		org.omg.CORBA.Object oA = orb.string_to_object(spl);
		splObj = RMBHelper.narrow(oA);

		BufferedReader br2 = new BufferedReader(new FileReader("spb.txt"));
		String spb = br2.readLine();
		br2.close();
		org.omg.CORBA.Object oB = orb.string_to_object(spb);
		spbObj = RMBHelper.narrow(oB);
	}
	
	//receives the request and replies
	@SuppressWarnings("resource")
	public void Transmission() throws IOException {
		System.out.println("Manager B is running");
		DatagramSocket transferSocket = new DatagramSocket();
		InetAddress group = InetAddress.getByName("228.5.6.7");
		MulticastSocket s = new MulticastSocket(6789);
		s.joinGroup(group);
		InetAddress aHost = InetAddress.getByName("localhost");
		
			while (true) {
				byte[] buffer = new byte[100];
				 DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
				 s.receive(receive);
				 String message = DoOperation(new String(receive.getData()).trim());
				 buffer = message.getBytes();
				 DatagramPacket reply = new DatagramPacket(buffer,buffer.length,aHost, RMreply);
				 transferSocket.send(reply);
			}
		

	}

	// get station from id
	public static String getStation(String id) {
		// System.out.println("get station");
		String station;
		int length;
		length = id.length() - 4;
		station = id.substring(0, length);
		// System.out.println("found station");
		return station;
	}
	// DOes the main ops
	public String DoOperation(String message) throws IOException {
		System.out.println(message);
		StringTokenizer tokens = new StringTokenizer(message, ",");
		String badgeId, firstName, lastName, description, address, lastDate, lastLocation, status, recordID, remoteStation, operation, sequence;
		badgeId = new String(tokens.nextToken()).trim();
		firstName = new String(tokens.nextToken()).trim();
		lastName = new String(tokens.nextToken()).trim();
		description = new String(tokens.nextToken()).trim();
		address = new String(tokens.nextToken()).trim();
		lastDate = new String(tokens.nextToken()).trim();
		lastLocation = new String(tokens.nextToken()).trim();
		status = new String(tokens.nextToken()).trim();
		recordID = new String(tokens.nextToken()).trim();
		remoteStation = new String(tokens.nextToken()).trim();
		operation = new String(tokens.nextToken()).trim();
		sequence = new String(tokens.nextToken()).trim();
		System.out.println(badgeId + firstName + lastName + description
				+ address + lastDate + lastLocation + status + recordID
				+ remoteStation + operation + sequence);

		String station = "";
		station = getStation(badgeId);


		switch (operation) {
		case "createCR":
			if (station.equals("SPVM")) {
				message = Boolean.toString(spvmObj.createCRecord(firstName,
						lastName, description, status, badgeId));
			} else if (station.equals("SPL")) {
				message = Boolean.toString(splObj.createCRecord(firstName,
						lastName, description, status, badgeId));
			} else if (station.equals("SPB")) {
				message = Boolean.toString(spbObj.createCRecord(firstName,
						lastName, description, status, badgeId));
			}
			break;
		case "createMR":
			if (station.equals("SPVM")) {
				message = Boolean.toString(spvmObj.createMRecord(firstName,
						lastName, address, lastDate, lastLocation, status,
						badgeId)); // MR
									// create
			} else if (station.equals("SPL")) {
				message = Boolean.toString(splObj.createMRecord(firstName,
						lastName, address, lastDate, lastLocation, status,
						badgeId)); // MR
									// create
			} else if (station.equals("SPB")) {
				message = Boolean.toString(spbObj.createMRecord(firstName,
						lastName, address, lastDate, lastLocation, status,
						badgeId)); // MR
									// create
			}
			break;
		case "EditCR":
			if (station.equals("SPVM")) {
				message = Boolean.toString(spvmObj.editCRecord(lastName,
						recordID, status, badgeId));
			} else if (station.equals("SPL")) {
				message = Boolean.toString(spvmObj.editCRecord(lastName,
						recordID, status, badgeId));
			} else if (station.equals("SPB")) {
				message = Boolean.toString(spvmObj.editCRecord(lastName,
						recordID, status, badgeId));
			}
			break;
		case "display":
			if (station.equals("SPVM")) {
				message = spvmObj.getRecordCounts(badgeId);
			} else if (station.equals("SPL")) {
				message = splObj.getRecordCounts(badgeId);
			} else if (station.equals("SPB")) {
				message = spbObj.getRecordCounts(badgeId);
			}
			break;
		case "transfer":
			if (station.equals("SPVM")) {
				message = Boolean.toString(spvmObj.transferRecord(recordID,
						badgeId, remoteStation));
			} else if (station.equals("SPL")) {
				message = Boolean.toString(spvmObj.transferRecord(recordID,
						badgeId, remoteStation));
			} else if (station.equals("SPB")) {
				message = Boolean.toString(spvmObj.transferRecord(recordID,
						badgeId, remoteStation));
			}
			break;
		case "resetRM2":
			this.initializeReplica();
			message= "server restarted";
			break;
		default:
			message = "invalid request";
		}
		System.out.println("reply to RM" + message +" "+ sequence);
		if(Integer.parseInt(sequence) == prevSequence+1){
			prevSequence = Integer.parseInt(sequence);
			return RMID + " , "+ message +" , "+ sequence;
		}
		return RMID + " , "+ message +" , "+ sequence;
	}

	public static void main(String[] args) throws IOException {
		ManagerB a = new ManagerB();
		a.Transmission();

	}
}
