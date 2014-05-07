package RMB_idl;

/**
 * Holder class for : RMB
 * 
 * @author OpenORB Compiler
 */
final public class RMBHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal RMB value
     */
    public RMB_idl.RMB value;

    /**
     * Default constructor
     */
    public RMBHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public RMBHolder(RMB_idl.RMB initial)
    {
        value = initial;
    }

    /**
     * Read RMB from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = RMBHelper.read(istream);
    }

    /**
     * Write RMB into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        RMBHelper.write(ostream,value);
    }

    /**
     * Return the RMB TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return RMBHelper.type();
    }

}
