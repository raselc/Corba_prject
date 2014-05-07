package RMC_idl;

/**
 * Holder class for : RMC
 * 
 * @author OpenORB Compiler
 */
final public class RMCHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal RMC value
     */
    public RMC_idl.RMC value;

    /**
     * Default constructor
     */
    public RMCHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public RMCHolder(RMC_idl.RMC initial)
    {
        value = initial;
    }

    /**
     * Read RMC from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = RMCHelper.read(istream);
    }

    /**
     * Write RMC into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        RMCHelper.write(ostream,value);
    }

    /**
     * Return the RMC TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return RMCHelper.type();
    }

}
