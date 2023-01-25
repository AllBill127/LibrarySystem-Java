package myExceptions;

/**
 * LiteratureAllTakenException extends {@code LiteratureException} and is thrown when literature 
 * is out of copies when trying to take new literature
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class LiteratureAllTakenException extends LiteratureException
{
	private static final long serialVersionUID = 1L;
	
	private int userId;
	
	/**
	 * Create new exception message "Literature is all taken"
	 */
	public LiteratureAllTakenException()
	{
		super("Literature is all taken");
	}
	
	/**
	 * Create new exception message "Literature is all taken" and save user identification number 
	 * who tried to take the literature
	 * @param userId
	 */
	public LiteratureAllTakenException(int userId)
	{
		super("Literature is all taken");
		this.userId = userId;
	}
	
	/**
	 * 
	 * @return user identification number who tried to take literature which was out of copies at that moment
	 */
	public int getUserId()
	{
		return this.userId;
	}
}
