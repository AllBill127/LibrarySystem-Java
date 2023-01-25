package myExceptions;

/**
 * LiteratureLimitException extends {@code LiteratureException} and is thrown when literature 
 * limit is reached when trying to take new literature
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class LiteratureLimitException extends LiteratureException
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create new exception message "Literature limit reached"
	 */
	public LiteratureLimitException()
	{
		super("Literature limit reached");
	}
}
