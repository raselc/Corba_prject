package RMA_idl;

/**
 * Interface definition: RMA.
 * 
 * @author OpenORB Compiler
 */
public interface RMAOperations
{
    /**
     * Operation createCRecord
     */
    public boolean createCRecord(String firstName, String lastName, String description, String status, String badgeId);

    /**
     * Operation createMRecord
     */
    public boolean createMRecord(String firstName, String lastName, String address, String lastdate, String lastaddress, String status, String badgeId);

    /**
     * Operation getRecordCounts
     */
    public String getRecordCounts(String badgeId);

    /**
     * Operation editCRecord
     */
    public boolean editCRecord(String lastName, String recordID, String newStatus, String badgeId);

    /**
     * Operation transferRecord
     */
    public boolean transferRecord(String recordID, String bagdeId, String remoteStation);

}
