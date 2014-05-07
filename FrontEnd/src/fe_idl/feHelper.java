package fe_idl;

/** 
 * Helper class for : fe
 *  
 * @author OpenORB Compiler
 */ 
public class feHelper
{
    /**
     * Insert fe into an any
     * @param a an any
     * @param t fe value
     */
    public static void insert(org.omg.CORBA.Any a, fe_idl.fe t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract fe from an any
     *
     * @param a an any
     * @return the extracted fe value
     */
    public static fe_idl.fe extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return fe_idl.feHelper.narrow( a.extract_Object() );
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
     * Return the fe TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "fe" );
        }
        return _tc;
    }

    /**
     * Return the fe IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:fe_idl/fe:1.0";

    /**
     * Read fe from a marshalled stream
     * @param istream the input stream
     * @return the readed fe value
     */
    public static fe_idl.fe read(org.omg.CORBA.portable.InputStream istream)
    {
        return(fe_idl.fe)istream.read_Object(fe_idl._feStub.class);
    }

    /**
     * Write fe into a marshalled stream
     * @param ostream the output stream
     * @param value fe value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, fe_idl.fe value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to fe
     * @param obj the CORBA Object
     * @return fe Object
     */
    public static fe narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof fe)
            return (fe)obj;

        if (obj._is_a(id()))
        {
            _feStub stub = new _feStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to fe
     * @param obj the CORBA Object
     * @return fe Object
     */
    public static fe unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof fe)
            return (fe)obj;

        _feStub stub = new _feStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
