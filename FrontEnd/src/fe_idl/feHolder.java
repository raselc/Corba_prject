package fe_idl;

/**
 * Holder class for : fe
 * 
 * @author OpenORB Compiler
 */
final public class feHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal fe value
     */
    public fe_idl.fe value;

    /**
     * Default constructor
     */
    public feHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public feHolder(fe_idl.fe initial)
    {
        value = initial;
    }

    /**
     * Read fe from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = feHelper.read(istream);
    }

    /**
     * Write fe into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        feHelper.write(ostream,value);
    }

    /**
     * Return the fe TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return feHelper.type();
    }

}
