package myUser;

import java.io.Serializable;
import java.util.LinkedList;

import myExceptions.*;
import myLiterature.Literature;
import myPlanFactory.*;

/**
 * A class for user object
 * 
 * @see myLiterature
 * @see myPlanFactory
 * @see myExceptions
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 *
 */
public class User
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	static GetPlanFactory getPlanFactory = new GetPlanFactory();
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String phoneNr;
	private Plan plan;
	private long debt = 0;
	private int literatureCount = 0;
	private LinkedList<Literature> list = new LinkedList<>();
	private LinkedList<Integer> userIndex = new LinkedList<>();

	//============= Constructors ==============
	/**
	 * Constructs a new empty User object
	 */
	public User()
	{
		this(0, null, null, null, null, null);
	}
	
	/**
	 * Constructs a new User object with given values and 2 lists:
	 * "list" a list of literature objects taken by this user,
	 * "userIndex" a list of indexes which represent in what place the user was added when saving information in taken literature
	 * 
	 * @param id identification number of this user
	 * @param username full name of this user
	 * @param password password of this user
	 * @param email email address of this user
	 * @param phoneNr phone number of this user
	 * @param plan name of the reader privileges plan for this user
	 */
	public User(int id, String username, String password, String email, String phoneNr, String plan)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNr = phoneNr;
		this.plan = getPlanFactory.getPlan(plan);
	}
	
	
	//============= Setters ==============
	/**
	 * Sets identification number for this user
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the full name of this user
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * Sets the password of this user
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/**
	 * Sets the email address of this user
	 * @param email
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * Sets the phone number of this user
	 * @param phoneNr
	 */
	public void setPhoneNr(String phoneNr)
	{
		this.phoneNr = phoneNr;
	}
	
	/**
	 * Sets the reader privileges plan of this user 
	 * @param plan name of the plan
	 */
	public void setPlan(String plan)
	{
		this.plan = getPlanFactory.getPlan(plan);
	}
	
	
	//============= Getters ==============
	/**
	 * 
	 * @return user identification number
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * 
	 * @return full name of this user
	 */
	public String getUsername()
	{
		return this.username;
	}
	
	/**
	 * 
	 * @return password of this user
	 */
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * 
	 * @return email address of this user
	 */
	public String getEmail()
	{
		return this.email;
	}
	
	/**
	 * 
	 * @return phone number of this user
	 */
	public String getPhoneNr()
	{
		return this.phoneNr;
	}
	
	/**
	 * 
	 * @return Plan class object which shows this users reader privileges plan 
	 */
	public Plan getPlan()
	{
		return this.plan;
	}
	
	/**
	 * 
	 * @return this users debt in cents for returning literature after the end of return term
	 */
	public long getDebt()
	{
		return this.debt;
	}
	
	/**
	 * 
	 * @return number of literatures this user currently has
	 */
	public int getLiteratureCount()
	{
		return this.literatureCount;
	}
	
	/**
	 * 
	 * @param index literatures index in the list of this users currently taken literature
	 * @return Literature class object which is literature taken by this user 
	 */
	public Literature getBookFromList(int index)
	{
		return list.get(index);
	}
	
	/**
	 * 
	 * @param bookIndex literatures index in the list of this users currently taken literature
	 * @return number of days left until return term ends
	 */
	public long getDaysLeft(int bookIndex)
	{ 
		this.plan.getDaysToReturn();
		long taken = this.getBookFromList(bookIndex).getDaysTaken(this.getId()); 
		long days = this.plan.getReturnPeriod() - taken; 
		return days;
	}
	
	
	//============= Methods ==============
	public String toString()
	{
        String listStream = "";
        for(int i = 0; i < this.list.size(); ++i)
        {
        	listStream += this.list.get(i).toString();
        }
		
		return "User class\nID: " + this.id + "\nUsername: " + this.username + "\nEmail: " + this.email + "\nPhone number: " + this.phoneNr + "\n" + this.plan.toString() + "\nDept: " + this.debt/100 + "." + this.debt%100 + "eur." + "\nBook count: " + this.literatureCount + "\nTaken books:" + listStream;
	}
	
	/**
	 * If this user can take given literature, saves users info in given Literature object with default return term, 
	 * gives this user given literature clone and updates this users info
	 * 
	 * @param literature Literature object to be taken by this user
	 * @throws LiteratureAllTakenException throws exception if given literature has no copies left
	 * @throws LiteratureLimitException throws exception if this user currently has reached literature limit specified in reader privileges plan
	 * @throws LiteratureAlreadyTakenException throws exception if this user currently already has given literature
	 */
	public void takeBook(Literature literature) throws LiteratureAllTakenException, LiteratureLimitException, LiteratureAlreadyTakenException
	{
		for(int i = 0; i < this.literatureCount; ++i)
			if(this.list.get(i).getId() == literature.getId())		// If book not yet taken continue
				throw new LiteratureAlreadyTakenException();
		
		int literatureLimit = this.plan.getBookLimit();
		if(this.literatureCount >= literatureLimit)					// If user can have more books continue
		{
			throw new LiteratureLimitException();
		}
		else if(literature.getCount() > 0)							// If book count is not 0 take book
		{
			this.literatureCount++;
			userIndex.add(literature.getUserCount());
			literature.take(this.id);
			try
			{
				list.add((Literature)literature.clone());		//Polymorphic clone() call
			} 
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
		}
		else
			throw new LiteratureAllTakenException();
	}
	
	/**
	 * If this user can take given literature, saves users info in given Literature object with extra days
	 *  added to default return term, gives this user given literature clone and updates this users info
	 * 
	 * @param literature Literature object to be taken by this user
	 * @throws LiteratureAllTakenException throws exception if given literature has no copies left
	 * @throws LiteratureLimitException throws exception if this user currently has reached literature limit specified in reader privileges plan
	 * @throws LiteratureAlreadyTakenException throws exception if this user currently already has given literature
	 */
	public void takeBook(Literature literature, int extraDays) throws LiteratureAllTakenException, LiteratureLimitException, LiteratureAlreadyTakenException
	{
		for(int i = 0; i < this.literatureCount; ++i)
			if(this.list.get(i).getId() == literature.getId())
				throw new LiteratureAlreadyTakenException();
		
		int literatureLimit = this.plan.getBookLimit();
		if(this.literatureCount >= literatureLimit)
		{
			throw new LiteratureLimitException();
		}
		else if(literature.getCount() > 0)
		{
			this.literatureCount++;
			userIndex.add(literature.getUserCount());
			literature.take(this.id, extraDays);
			try
			{
				list.add((Literature)literature.clone());		//Polymorphic clone() call
			}
			catch (CloneNotSupportedException e)
			{
				e.printStackTrace();
			}
		}
		else
			throw new LiteratureAllTakenException();
	}
	
	/**
	 * Deletes this users info from specified literature, counts if literature was returned late and 
	 * increases this users debt if necessary, removes specified literature from this users list and updates this users info
	 * @param listIndex literatures index in the list of this users currently taken literature
	 */
	public void returnBook(int listIndex)
	{	
		long days = list.get(listIndex).daysTaken(this.id);
		
		this.plan.getDaysToReturn();					//| Using Factory-method design pattern
		this.plan.getDayCost();							//| to count different debts for returned book.
		this.debt += plan.countDebt(days);				//\	Plan can change depending on how often this library is used by a user
				
		list.get(listIndex).returnItem(this.id);
		list.remove(listIndex);
		userIndex.remove(listIndex);
		this.literatureCount--;
	}
	
	/**
	 * Returns given amount of this users debt
	 * @param d amount in cents to return 
	 */
	public void returnDebt(double d)
	{
		this.debt -= d;
	}
}
