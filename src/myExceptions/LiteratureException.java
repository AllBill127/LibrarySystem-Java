package myExceptions;

/**
 * Base class for literature exceptions
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class LiteratureException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create new exception message
	 * @param message text to show when printStackTrace() is called
	 */
	public LiteratureException(String message)
	{
		super(message);
	}
}
