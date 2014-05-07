package fe_idl;

/**
 * Interface definition: fe.
 * 
 * @author OpenORB Compiler
 */
public abstract class fePOA extends org.omg.PortableServer.Servant
        implements feOperations, org.omg.CORBA.portable.InvokeHandler
{
    public fe _this()
    {
        return feHelper.narrow(_this_object());
    }

    public fe _this(org.omg.CORBA.ORB orb)
    {
        return feHelper.narrow(_this_object(orb));
    }

    private static String [] _ids_list =
    {
        "IDL:fe_idl/fe:1.0"
    };

    public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte [] objectId)
    {
        return _ids_list;
    }

    public final org.omg.CORBA.portable.OutputStream _invoke(final String opName,
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler)
    {

        if (opName.equals("operation")) {
                return _invoke_operation(_is, handler);
        } else {
            throw new org.omg.CORBA.BAD_OPERATION(opName);
        }
    }

    // helper methods
    private org.omg.CORBA.portable.OutputStream _invoke_operation(
            final org.omg.CORBA.portable.InputStream _is,
            final org.omg.CORBA.portable.ResponseHandler handler) {
        org.omg.CORBA.portable.OutputStream _output;
        String arg0_in = _is.read_string();
        String arg1_in = _is.read_string();
        String arg2_in = _is.read_string();
        String arg3_in = _is.read_string();
        String arg4_in = _is.read_string();
        String arg5_in = _is.read_string();
        String arg6_in = _is.read_string();
        String arg7_in = _is.read_string();
        String arg8_in = _is.read_string();
        String arg9_in = _is.read_string();
        String arg10_in = _is.read_string();

        String _arg_result = operation(arg0_in, arg1_in, arg2_in, arg3_in, arg4_in, arg5_in, arg6_in, arg7_in, arg8_in, arg9_in, arg10_in);

        _output = handler.createReply();
        _output.write_string(_arg_result);

        return _output;
    }

}
