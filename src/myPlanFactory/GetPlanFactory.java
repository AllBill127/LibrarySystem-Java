package myPlanFactory;

/**
 * GetPlanFactory is a class to create a factory of reader privilege plans
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 *
 */
public class GetPlanFactory
{
	/**
	 * Creates a new reader privilege plan
	 * 
	 * @param planType name of reader privilege plan to be created
	 * @return new Plan subclass object based on given plan name or null if given name is not an existing plan
	 */
    public Plan getPlan(String planType)
    {  
    	if(planType == null)  
    		return null;  
    	
    	if(planType.equalsIgnoreCase("Beginner"))  
    		return new BeginnerPlan();
    	
        else if(planType.equalsIgnoreCase("Intermediate"))  
        	return new IntermediatePlan();   
    	
        else if(planType.equalsIgnoreCase("BookWorm"))
        	return new BookWormPlan();
    	
     return null; 
    }
}
