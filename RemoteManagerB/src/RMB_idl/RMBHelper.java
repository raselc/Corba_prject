package RMB_idl;

/** 
 * Helper class for : RMB
 *  
 * @author OpenORB Compiler
 */ 
public class RMBHelper
{
    /**
     * Insert RMB into an any
     * @param a an any
     * @param t RMB value
     */
    public static void insert(org.omg.CORBA.Any a, RMB_idl.RMB t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract RMB from an any
     *
     * @param a an any
     * @return the extracted RMB value
     */
    public static RMB_idl.RMB extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return RMB_idl.RMBHelper.narrow( a.extract_Object() );
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
     * Return the RMB TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "RMB" );
        }
        return _tc;
    }

    /**
     * Return the RMB IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:RMB_idl/RMB:1.0";

    /**
     * Read RMB from a marshalled stream
     * @param istream the input stream
     * @return the readed RMB value
     */
    public static RMB_idl.RMB read(org.omg.CORBA.portable.InputStream istream)
    {
        return(RMB_idl.RMB)istream.read_Object(RMB_idl._RMBStub.class);
    }

    /**
     * Write RMB into a marshalled stream
     * @param ostream the output stream
     * @param value RMB value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, RMB_idl.RMB value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to RMB
     * @param obj the CORBA Object
     * @return RMB Object
     */
    public static RMB narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMB)
            return (RMB)obj;

        if (obj._is_a(id()))
        {
            _RMBStub stub = new _RMBStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to RMB
     * @param obj the CORBA Object
     * @return RMB Object
     */
    public static RMB unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMB)
            return (RMB)obj;

        _RMBStub stub = new _RMBStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
