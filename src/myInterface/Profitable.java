package myInterface;

/**
 * Profitable interface predefines methods that counts days for debt
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public interface Profitable
{
	/**
	 * Invokes countDays(int userId) method
	 * 
	 * @param userId identification number of the user who has taken this literature
	 * @return if return term has not ended 0, or number of days since return term has ended
	 */
	long daysTaken(int userId);
	
	/**
	 * 
	 * @param userId identification number of the user who has taken this literature
	 * @return if return term has not ended 0, or number of days since return term has ended 
	 */
	long countDays(int userId);
}
