package RMC_idl;

/** 
 * Helper class for : RMC
 *  
 * @author OpenORB Compiler
 */ 
public class RMCHelper
{
    /**
     * Insert RMC into an any
     * @param a an any
     * @param t RMC value
     */
    public static void insert(org.omg.CORBA.Any a, RMC_idl.RMC t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract RMC from an any
     *
     * @param a an any
     * @return the extracted RMC value
     */
    public static RMC_idl.RMC extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return RMC_idl.RMCHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the RMC TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "RMC" );
        }
        return _tc;
    }

    /**
     * Return the RMC IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:RMC_idl/RMC:1.0";

    /**
     * Read RMC from a marshalled stream
     * @param istream the input stream
     * @return the readed RMC value
     */
    public static RMC_idl.RMC read(org.omg.CORBA.portable.InputStream istream)
    {
        return(RMC_idl.RMC)istream.read_Object(RMC_idl._RMCStub.class);
    }

    /**
     * Write RMC into a marshalled stream
     * @param ostream the output stream
     * @param value RMC value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, RMC_idl.RMC value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to RMC
     * @param obj the CORBA Object
     * @return RMC Object
     */
    public static RMC narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMC)
            return (RMC)obj;

        if (obj._is_a(id()))
        {
            _RMCStub stub = new _RMCStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to RMC
     * @param obj the CORBA Object
     * @return RMC Object
     */
    public static RMC unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMC)
            return (RMC)obj;

        _RMCStub stub = new _RMCStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
