package RMA_idl;

/** 
 * Helper class for : RMA
 *  
 * @author OpenORB Compiler
 */ 
public class RMAHelper
{
    /**
     * Insert RMA into an any
     * @param a an any
     * @param t RMA value
     */
    public static void insert(org.omg.CORBA.Any a, RMA_idl.RMA t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract RMA from an any
     *
     * @param a an any
     * @return the extracted RMA value
     */
    public static RMA_idl.RMA extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return RMA_idl.RMAHelper.narrow( a.extract_Object() );
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
     * Return the RMA TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "RMA" );
        }
        return _tc;
    }

    /**
     * Return the RMA IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:RMA_idl/RMA:1.0";

    /**
     * Read RMA from a marshalled stream
     * @param istream the input stream
     * @return the readed RMA value
     */
    public static RMA_idl.RMA read(org.omg.CORBA.portable.InputStream istream)
    {
        return(RMA_idl.RMA)istream.read_Object(RMA_idl._RMAStub.class);
    }

    /**
     * Write RMA into a marshalled stream
     * @param ostream the output stream
     * @param value RMA value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, RMA_idl.RMA value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to RMA
     * @param obj the CORBA Object
     * @return RMA Object
     */
    public static RMA narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMA)
            return (RMA)obj;

        if (obj._is_a(id()))
        {
            _RMAStub stub = new _RMAStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to RMA
     * @param obj the CORBA Object
     * @return RMA Object
     */
    public static RMA unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof RMA)
            return (RMA)obj;

        _RMAStub stub = new _RMAStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
