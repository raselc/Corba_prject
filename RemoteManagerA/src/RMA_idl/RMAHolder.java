package RMA_idl;

/**
 * Holder class for : RMA
 * 
 * @author OpenORB Compiler
 */
final public class RMAHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal RMA value
     */
    public RMA_idl.RMA value;

    /**
     * Default constructor
     */
    public RMAHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public RMAHolder(RMA_idl.RMA initial)
    {
        value = initial;
    }

    /**
     * Read RMA from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = RMAHelper.read(istream);
    }

    /**
     * Write RMA into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        RMAHelper.write(ostream,value);
    }

    /**
     * Return the RMA TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return RMAHelper.type();
    }

}
