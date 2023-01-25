package myPlanFactory;

/**
 * BegginerPlan extends {@code Plan}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 *
 */
public class BeginnerPlan extends Plan
{
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		this.getDayCost();
		this.getDaysToReturn();
		this.getBookLimit();
		return "Begginer plan: \ndayCost = " + dayCost + " ct. \ndaysToReturn = " + daysToReturn + "\nbookLimit = " + bookLimit;
	}
	
	/**
	 * Set Beginner reader privilege plan day cost
	 */
	public void getDayCost()
	{
		dayCost = 10;
	}
	
	/**
	 *  Set Beginner reader privilege plan default return term
	 */
	public void getDaysToReturn()
	{
		daysToReturn = 14;
	}
	
	/**
	 *	@return Beginner reader privilege plan literature limit
	 */
	public int getBookLimit()
	{
		bookLimit = 3;
		return bookLimit;
	}
	
	/**
	 *	@return Beginner reader privilege plan name
	 */
	public String getName()
	{
		return "Beginner";
	}
}
