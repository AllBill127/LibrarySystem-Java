package myPlanFactory;

import java.io.Serializable;

/**
 * Abstract Plan class for different reader privilege plans 
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public abstract class Plan
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	protected long daysToReturn;
	protected long dayCost;
	protected int bookLimit;
	
	/**
	 * Sets number of days for default return term based on reader privilege plan
	 */
	abstract public void getDaysToReturn();
	
	/**
	 * Sets one day cost based on reader privilege plan after return term has ended based on reader privilege plan
	 */
    abstract public void getDayCost();
    
    /**
     * @return maximum number of literatures a user can have at the same time based on reader privilege plan
     */
    abstract public int getBookLimit();
    
    /**
     * @return name of reader privilege plan
     */
    abstract public String getName();
    
    /**
     * Counts debt in cents for returning literature late based on reader privilege plan
     * @param days amount of days that have passed since the end of return term 
     * @return debt in cents for returning literature after the end of return term
     */
    public long countDebt(long days)
	{
		if(days > daysToReturn)
			return (days - daysToReturn) * dayCost;
		else
			return 0;
	}
    
    /**
     * 
     * @return default return term based on reader privilege plan
     */
    public long getReturnPeriod()
    {
    	return daysToReturn;
    }
}
