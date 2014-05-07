package fe_idl;

/**
 * Interface definition: fe.
 * 
 * @author OpenORB Compiler
 */
public class fePOATie extends fePOA
{

    //
    // Private reference to implementation object
    //
    private feOperations _tie;

    //
    // Private reference to POA
    //
    private org.omg.PortableServer.POA _poa;

    /**
     * Constructor
     */
    public fePOATie(feOperations tieObject)
    {
        _tie = tieObject;
    }

    /**
     * Constructor
     */
    public fePOATie(feOperations tieObject, org.omg.PortableServer.POA poa)
    {
        _tie = tieObject;
        _poa = poa;
    }

    /**
     * Get the delegate
     */
    public feOperations _delegate()
    {
        return _tie;
    }

    /**
     * Set the delegate
     */
    public void _delegate(feOperations delegate_)
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
     * Operation operation
     */
    public String operation(String badgeId, String firstName, String lastName, String description, String address, String lastDate, String lastLocation, String status, String recordID, String remoteStation, String operation)
    {
        return _tie.operation( badgeId,  firstName,  lastName,  description,  address,  lastDate,  lastLocation,  status,  recordID,  remoteStation,  operation);
    }

}
