package RMC_idl;

/**
 * Interface definition: RMC.
 * 
 * @author OpenORB Compiler
 */
public class RMCPOATie extends RMCPOA
{

    //
    // Private reference to implementation object
    //
    private RMCOperations _tie;

    //
    // Private reference to POA
    //
    private org.omg.PortableServer.POA _poa;

    /**
     * Constructor
     */
    public RMCPOATie(RMCOperations tieObject)
    {
        _tie = tieObject;
    }

    /**
     * Constructor
     */
    public RMCPOATie(RMCOperations tieObject, org.omg.PortableServer.POA poa)
    {
        _tie = tieObject;
        _poa = poa;
    }

    /**
     * Get the delegate
     */
    public RMCOperations _delegate()
    {
        return _tie;
    }

    /**
     * Set the delegate
     */
    public void _delegate(RMCOperations delegate_)
    {
        _tie = delegate_;
    }

    /**
     * _default_POA method
     */
    public org.omg.PortableServer.POA _default_POA()
    {
        if (_poa != null)
            return _poa;
        else
            return super._default_POA();
    }

    /**
     * Operation createCRecord
     */
    public boolean createCRecord(String firstName, String lastName, String description, String status, String badgeId)
    {
        return _tie.createCRecord( firstName,  lastName,  description,  status,  badgeId);
    }

    /**
     * Operation createMRecord
     */
    public boolean createMRecord(String firstName, String lastName, String address, String lastdate, String lastaddress, String status, String badgeId)
    {
        return _tie.createMRecord( firstName,  lastName,  address,  lastdate,  lastaddress,  status,  badgeId);
    }

    /**
     * Operation getRecordCounts
     */
    public String getRecordCounts(String badgeId)
    {
        return _tie.getRecordCounts( badgeId);
    }

    /**
     * Operation editCRecord
     */
    public boolean editCRecord(String lastName, String recordID, String newStatus, String badgeId)
    {
        return _tie.editCRecord( lastName,  recordID,  newStatus,  badgeId);
    }

    /**
     * Operation transferRecord
     */
    public boolean transferRecord(String recordID, String bagdeId, String remoteStation)
    {
        return _tie.transferRecord( recordID,  bagdeId,  remoteStation);
    }

}
