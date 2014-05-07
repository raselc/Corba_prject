package fe_idl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class FeImpl extends fePOA {

	Sequencer s = new Sequencer();
	int RMreceive = 5001;
	int MULTICAST_PORT = 6789;
	int RM1Count=0;
	int RM2Count=0;
	int RM3Count=0;

	//Loggin the File
	public static void logFile(String Operation) throws SecurityException {
		// System.out.println("loggin file");
		File log = new File("FeLog.txt");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		try {
			if (!log.exists()) {
			}
			log.setWritable(true);
			FileWriter fileWriter = new FileWriter(log, true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Operation + " " + dateFormat.format(date));
			bufferedWriter.newLine();
			bufferedWriter.close();
			// System.out.println("logged file");
		} catch (IOException e) {
			System.out.println("COULD NOT LOG!!");
		}
	}

	/**
	 * transmitt multi server 
	 * 
	 * @throws IOException
	 **/

	public synchronized void Transmission(String sendRequest)
			throws IOException {
		sendRequest = sendRequest + s.getSequence();
		System.out.println("Transmitting to RMs with message:" + sendRequest);
		InetAddress group = InetAddress.getByName("228.5.6.7");
		MulticastSocket s = new MulticastSocket(MULTICAST_PORT);
		s.joinGroup(group);
		DatagramPacket hi = new DatagramPacket(sendRequest.getBytes(),
				sendRequest.length(), group, 6789);
		s.send(hi);
		s.close();
	}

	/**receive transmission*/
	public synchronized String receiveTransmission() throws IOException {
		DatagramSocket aSocket = null;
		aSocket = new DatagramSocket(RMreceive);
		byte[] buffer = new byte[100];
		String[] result = new String[3];
		java.util.Arrays.fill(result,"false  ,  false , false");
		int i = 0;
		while (i < 3) {
			aSocket.setSoTimeout(5000);
			try{
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request);
			result[i] = new String(request.getData()).trim();
			}catch (SocketTimeoutException s){
				System.out.println("server time out");
			}
			i++;
		}
		aSocket.close();
		System.out.println("reply from RMS:" + result[0] + result[1]
				+ result[2]);

		return verifyResult(result[0], result[1], result[2]);
	}

	//sends faulty message to RMs
	public void reportFaulty(String RM) throws IOException {
		System.out.println("Faulty RM: " + RM);
		String message = "  ";
		String response = "  ";
		
		if(RM.equals("RM1")){
			if(RM1Count == 3){
				System.out.println("-------Restarting----------");
				System.out.println("Restarting Remote Manager A");
				System.out.println("-------Restarting----------");
				message = " , " + " , " +  " , "+ " , " + " , " + " , "+ " , " + " , " + " , "+ " , " + "resetRM1" + "  , ";
				Transmission(message);
				RM1Count = 0;
			}
			else
				RM1Count ++;
		}
		else if(RM.equals("RM2")){
			if(RM2Count == 3){
				System.out.println("-------Restarting----------");
				System.out.println("Restarting Remote Manager B");
				System.out.println("-------Restarting----------");
				message = " , " + " , " +  " , "+ " , " + " , " + " , "+ " , " + " , " + " , "+ " , " + "resetRM2" + "  , ";
				Transmission(message);
				RM2Count = 0;
			}
			else
				RM2Count ++;
		}
		else if(RM.equals("RM3")){
			if(RM3Count == 3){
				System.out.println("-------Restarting----------");
				System.out.println("Restarting Remote Manager C");
				System.out.println("-------Restarting----------");
				message = " , " + " , " +  " , "+ " , " + " , " + " , "+ " , " + " , " + " , "+ " , " + "resetRM1" + "  , ";
				Transmission(message);
				RM3Count = 0;
			}
			else
				RM3Count ++;
		}
		
		
	}

	//verifies result and selects the corrent result
	public String verifyResult(String message1, String message2,
			String message3) throws IOException {
		String result = "";
		StringTokenizer tokens = new StringTokenizer(message1, ",");
		String rm1 = new String(tokens.nextToken()).trim();
		String rm1Msg = new String(tokens.nextToken()).trim();
		String rm1Seq = new String(tokens.nextToken()).trim();

		tokens = new StringTokenizer(message2, ",");
		String rm2 = new String(tokens.nextToken()).trim();
		String rm2Msg = new String(tokens.nextToken()).trim();
		String rm2Seq = new String(tokens.nextToken()).trim();

		tokens = new StringTokenizer(message3, ",");
		String rm3 = new String(tokens.nextToken()).trim();
		String rm3Msg = new String(tokens.nextToken()).trim();
		String rm3Seq = new String(tokens.nextToken()).trim();
		
		if(rm1Seq.equals("false"))
			rm1Seq = rm2Seq;
		else if(rm2Seq.equals("false"))
			rm2Seq = rm1Seq;
		else if(rm3Seq.equals("false"))
			rm3Seq = rm2Seq;
		
		System.out.println("RM name:"+rm1 + rm2 + rm3);
		
		if(rm3.equals("false")&&(rm1.equals("RM1") && rm2.equals("RM2"))||(rm1.equals("RM2") && rm2.equals("RM1")))
			rm3 = "RM3";
		else if(rm3.equals("false")&&(rm1.equals("RM1") && rm2.equals("RM3"))||(rm1.equals("RM3") && rm2.equals("RM1")))
			rm3 = "RM2";
		else if(rm3.equals("false")&&(rm1.equals("RM2") && rm2.equals("RM3"))||(rm1.equals("RM3") && rm2.equals("RM2")))
			rm3 = "RM1";	
		
		
		System.out.println(" new RM name:"+rm1 + rm2 + rm3);
		
		if (rm1Seq.equals(rm2Seq) && rm1Seq.equals(rm3Seq)	&& rm2Seq.equals(rm3Seq)) {
			if (!rm1Msg.equals(rm2Msg) && !rm1Msg.equals(rm3Msg)) {
				if (rm2Msg.equals(rm3Msg))
					result = rm2Msg;
				reportFaulty(rm1);
			} else if (!rm2Msg.equals(rm1Msg) && !rm2Msg.equals(rm3Msg)) {
				if (rm1Msg.equals(rm3Msg))
					result = rm1Msg;
				reportFaulty(rm2);
			} else if (!rm3Msg.equals(rm1Msg) && !rm3Msg.equals(rm2Msg)) {
				if (rm1Msg.equals(rm2Msg))
					result = rm1Msg;
				reportFaulty(rm3);
			} else
				result = rm1Msg;
		}

		else
			result = "false";
		return result;
	}

	
	//OPERATION 
	@Override
	public String operation(String badgeId, String firstName, String lastName,
			String description, String address, String lastDate,
			String lastLocation, String status, String recordID,
			String remoteStation, String operation) {
		// TODO Auto-generated method stub
		String response = "";
		String message = "";
		message = badgeId + " , " + firstName + " , " + lastName + " , "
				+ description + " , " + address + " , " + lastDate + " , "
				+ lastLocation + " , " + status + " , " + recordID + " , "
				+ remoteStation + " , " + operation + "  , ";

		System.out.println("message to RM:" + message);
		try {
			Transmission(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			response = receiveTransmission().trim();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("result: " + response);

		return response;
	}

}
