package myExceptions;

/**
 * LiteratureAlreadyTakenException extends {@code LiteratureException} and is thrown when literature 
 * is currently already taken by user when trying to take new literature
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class LiteratureAlreadyTakenException extends LiteratureException
{
private static final long serialVersionUID = 1L;
	
	/**
	 * Create new exception message "Literature already taken"
	 */
	public LiteratureAlreadyTakenException()
	{
		super("Literature already taken");
	}
}
