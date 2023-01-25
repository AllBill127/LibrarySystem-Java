package myPlanFactory;

/**
 * BookWormPlan extends {@code Plan}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 *
 */
public class BookWormPlan extends Plan
{
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		this.getDayCost();
		this.getDaysToReturn();
		this.getBookLimit();
		return "BookWorm plan: \ndayCost = " + dayCost + " ct. \ndaysToReturn = " + daysToReturn + "\nbookLimit = " + bookLimit;
	}
	
	/**
	 * Set BookWorm reader privilege plan day cost
	 */
	public void getDayCost()
	{
		dayCost = 3;
	}
	
	/**
	 *  Set BookWorm reader privilege plan default return term
	 */
	public void getDaysToReturn()
	{
		daysToReturn = 56;
	}
	
	/**
	 *	@return BookWorm reader privilege plan literature limit
	 */
	public int getBookLimit()
	{
		bookLimit = 8;
		return bookLimit;
	}
	
	/**
	 *	@return BookWorm reader privilege plan name
	 */
	public String getName()
	{
		return "BookWorm";
	}
}
