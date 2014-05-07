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

	public class Client2 {

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

	
		public Client2(String badgeId, String firstName, String lastName,
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
			Client2 client1 =new Client2("SPVM1001", "fname", "lname",	"descrip", "", "", "", "free", "", "", "createCR");
			Client2 client2 = new Client2("SPL1001", "fname2", "lname2",	"descrip2", "", "", "", "free", "", "", "createCR");
			Client2 client3 = new Client2("SPB1001", "fname3", "lname3",	"descrip3", "", "", "", "free", "", "", "createCR");
			Client2 client4 = new Client2("SPVM1002", "fname4", "lname4",	"descrip4", "", "", "", "free", "", "", "createCR");

			// create mrecord
			Client2 client5 = new Client2("SPVM1002", "fname5", "lname5",
					"", "address5", "121112", "lastloc1", "notfound", "", "", "createMR");
			Client2 client6 = new Client2("SPL1003", "fname6", "lname6",
					"", "address6", "121112", "lastloc1", "found", "", "", "createMR");
			Client2 client7 = new Client2("SPB1004", "fname7", "lname7",
					"", "address7", "121112", "lastloc1", "notfound", "", "", "createMR");
			Client2 client8 =new Client2("SPVM1001", "fname8", "lname8",
					"", "address8", "121112", "lastloc1", "found", "", "", "createMR");

			// edit crecord
			Client2 client9 = new Client2("SPVM0001", "", "lname", "",
					"", "", "", "captured", "CR00001", "", "EditCR");
			Client2 client10 = new Client2("SPL0002", "", "lname10", "",
					"", "", "", "captured", "CR0002", "", "EditCR");
			Client2 client11 = new Client2("SPB0003", "", "lname11", "",
					"", "", "", "captured", "CR00003", "", "EditCR");
			Client2 client12 = new Client2("SPVM0004", "", "lname12", "",
					"", "", "", "captured", "CR00004", "", "EditCR");

			// view record
			Client2 client13 = new Client2("SPVM0004", "", "", "", "", "",
					"", "", "", "", "display");
			Client2 client14 = new Client2("SPL0001", "", "", "", "", "",
					"", "", "", "", "display");
			Client2 client15 = new Client2("SPB2001", "", "", "", "", "",
					"", "", "", "", "display");
			Client2 client16 = new Client2("SPVM3001", "", "", "", "",
					"", "", "", "", "", "display");

			// transfer record
			Client2 client17 = new Client2("SPVM1001", "", "", "", "",
					"", "", "", "CR00001", "SPL", "transfer");
			Client2 client18 = new Client2("SPB1004", "", "", "", "", "",
					"", "", "CR10001", "SPB", "transfer");
			Client2 client19 = new Client2("SPL1003", "", "", "", "", "",
					"", "", "CR10001", "SPL", "transfer");
			Client2 client20 = new Client2("SPVM1031", "", "", "", "",
					"", "", "", "CR10001", "SPVM", "transfer");

			client1.run();
			client2.run();
			client3.run();
			client4.run();

/*			client5.run();
			client6.run();
			client7.run();
			client8.run();

			client9.run();
			client10.run();
			client11.run();
			client12.run();

			client13.run();
			client14.run();
			client15.run();
			client16.run();

			client17.run();
			client18.run();
			client19.run();
			client20.run();
	*/	
		}

	}


