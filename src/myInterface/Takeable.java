package myInterface;

/**
 * Takeable interface predefines methods that allow taking and returning an item
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public interface Takeable
{
	/**
	 * 
	 * @param userId identification number of the user who is taking this literature
	 */
	void take(int userId);
	
	/**
	 * 
	 * @param userId identification number of the user who is taking this literature
	 * @param extraDays number of extra days to add to return term
	 */
	void take(int userId, int extraDays);
	
	/**
	 * 
	 * @param userId identification number of the user who has returned this literature
	 */
	void returnItem(int userId);
}