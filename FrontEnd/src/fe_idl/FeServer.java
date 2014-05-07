package fe_idl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;



public class FeServer {

	public static void main(String[] args) throws InvalidName,ServantAlreadyActive, WrongPolicy, ObjectNotActive,
			FileNotFoundException, AdapterInactive {
		// TODO Auto-generated method stub
		ORB orb = ORB.init(args, null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		FeImpl FeObj = new FeImpl();
		byte[] id = rootPOA.activate_object(FeObj);
		org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
		String spvmior = orb.object_to_string(ref);

		// System.out.println(ior);

		PrintWriter file = new PrintWriter("ior.txt");
		file.println(spvmior);
		file.close();

		System.out.println("The Server is UP and Running");
		rootPOA.the_POAManager().activate();
		orb.run();
	}

}
