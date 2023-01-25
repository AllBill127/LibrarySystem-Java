package myInterface;

/**
 * TakeableWithExtention extends {@code Takeable} and predefines methods that allow extending return term of an item
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public interface TakeableWithExtention extends Takeable
{
	/**
	 * 
	 * @param userId identification number of the user who wants to extend this literatures return term
	 * @param extraDays number of days to extend the return term
	 */
	void extendTerm(int userId, int extraDays);
}
