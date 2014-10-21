package fileSystem;

/**
 * Exception Klasse fuer das fileSystem
 *
 * @author Daniel Gauditz
 */
public class fileSystemException extends Exception
{

    /**
     * Creates a new instance of <code>fileSystemException</code> without detail
     * message.
     */
    public fileSystemException()
    {
    }

    /**
     * Constructs an instance of <code>fileSystemException</code> with the
     * specified detail message.
     * @param msg the detail message.
     */
    public fileSystemException(String msg)
    {
        super(msg);
    }
}
