package fe_idl;

/**
 * Interface definition: fe.
 * 
 * @author OpenORB Compiler
 */
public class _feStub extends org.omg.CORBA.portable.ObjectImpl
        implements fe
{
    static final String[] _ids_list =
    {
        "IDL:fe_idl/fe:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = fe_idl.feOperations.class;

    /**
     * Operation operation
     */
    public String operation(String badgeId, String firstName, String lastName, String description, String address, String lastDate, String lastLocation, String status, String recordID, String remoteStation, String operation)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("operation",true);
                    _output.write_string(badgeId);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(description);
                    _output.write_string(address);
                    _output.write_string(lastDate);
                    _output.write_string(lastLocation);
                    _output.write_string(status);
                    _output.write_string(recordID);
                    _output.write_string(remoteStation);
                    _output.write_string(operation);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("operation",_opsClass);
                if (_so == null)
                   continue;
                fe_idl.feOperations _self = (fe_idl.feOperations) _so.servant;
                try
                {
                    return _self.operation( badgeId,  firstName,  lastName,  description,  address,  lastDate,  lastLocation,  status,  recordID,  remoteStation,  operation);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
