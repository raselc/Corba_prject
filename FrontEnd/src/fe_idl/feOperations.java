package fe_idl;

/**
 * Interface definition: fe.
 * 
 * @author OpenORB Compiler
 */
public interface feOperations
{
    /**
     * Operation operation
     */
    public String operation(String badgeId, String firstName, String lastName, String description, String address, String lastDate, String lastLocation, String status, String recordID, String remoteStation, String operation);

}
