package myPlanFactory;

/**
 * IntermediatePlan extends {@code Plan}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 *
 */
public class IntermediatePlan extends Plan
{
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		this.getDayCost();
		this.getDaysToReturn();
		this.getBookLimit();
		return "Intermediate plan: \ndayCost = " + dayCost + " ct. \ndaysToReturn = " + daysToReturn + "\nbookLimit = " + bookLimit;
	}
	
	/**
	 * Set Intermediate reader privilege plan day cost
	 */
	public void getDayCost()
	{
		dayCost = 7;
	}
	
	/**
	 *  Set Intermediate reader privilege plan default return term
	 */
	public void getDaysToReturn()
	{
		daysToReturn = 28;
	}
	
	/**
	 *	@return Intermediate reader privilege plan literature limit
	 */
	public int getBookLimit()
	{
		bookLimit = 5;
		return bookLimit;
	}
	
	/**
	 *	@return Intermediate reader privilege plan name
	 */
	public String getName()
	{
		return "Intermediate";
	}
}
