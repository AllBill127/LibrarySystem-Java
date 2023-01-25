package myLiterature;

import myInterface.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

/**
 * Abstract base class for any type of literature object
 * 
 * {@link myInterface.Profitable}
 * {@link myInterface.Takeable}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public abstract class Literature implements Profitable, Takeable, Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String title;
	protected String publisher;
	protected int year;
	protected int count;
	protected int userCount = 0;
	protected LinkedList<Integer> userId = new LinkedList<>();
	protected LinkedList<LocalDate> outDate = new LinkedList<>();
	protected LinkedList<Integer> extraDays = new LinkedList<>();

	// ============= Constructs ==============
	/**
	 * Constructs a new empty Literature object
	 */
	public Literature()
	{
		this(0, null, null, 0, 0);
	}

	/**
	 * Constructs a new Literature object with given values and 3 lists: 
	 * "userId" for user id's that have taken this literature,
	 * "outDate" when was this literature taken by user with the same index,
	 * "extraDays" how many extra days to return a user with the same index requested
	 * 
	 * @param id identification number of this literature
	 * @param title full title of this literature
	 * @param publisher publishing house name of this literature
	 * @param year year of this literatures publication
	 * @param count number of this literatures copies in the library
	 */
	public Literature(int id, String title, String publisher, int year, int count)
	{
		this.id = id;
		this.title = title;
		this.publisher = publisher;
		this.year = year;
		this.count = count;
	}

	// ============= Setters ==============
	/**
	 * Sets this literatures identification number
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Sets this literatures title
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Sets this literatures publishing house name
	 * @param publisher
	 */
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	/**
	 * Sets this literatures publication year
	 * @param year
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	/**
	 * Sets this literatures number of copies
	 * @param count
	 */
	public void setCount(int count)
	{
		this.count = count;
	}

	// ============= Getters ==============
	/**
	 * 
	 * @return this literatures identification number
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * 
	 * @return this literatures full title
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * 
	 * @return this literatures publishing house name
	 */
	public String getPublisher()
	{
		return this.publisher;
	}

	/**
	 * 
	 * @return this literatures publication year
	 */
	public int getYear()
	{
		return this.year;
	}

	/**
	 * 
	 * @return this literatures number of copies
	 */
	public int getCount()
	{
		return this.count;
	}
	
	/**
	 * 
	 * @return number of users that have taken this literature
	 */
	public int getUserCount()
	{
		return this.userCount;
	}

	/**
	 * 
	 * @param i user index in outDate list
	 * @return date when a indexed user took this literature
	 */
	public LocalDate getOutDate(int i)
	{
		return this.outDate.get(i);
	}

	/**
	 * 
	 * @param i user index in userId list
	 * @return identification number of indexed user 
	 */
	public int getUserId(int i)
	{
		return this.userId.get(i);
	}
	
	/**
	 * Returns the number of days that passed since the user took this literature
	 * @param userId identification number of a user
	 * @return negative number means that extra days requested by the user exceed the number of days since taking this literature
	 */
	 public long getDaysTaken(int userId) 
	 { 
		 int i; 
		 for(i = 0; i < this.userId.size(); ++i)
			 if(this.userId.get(i) == userId) 
				 break;
		 
		 LocalDate inDate = LocalDate.now(); 
		 long timeTaken = this.outDate.get(i).until(inDate, ChronoUnit.DAYS) - this.extraDays.get(i);
		 
		 return timeTaken;
	 }

	// ============= Methods ==============
	public String toString()
	{
		String listStream = "";
        for(int i = 0; i < this.userId.size(); ++i)
        {
        	listStream += this.userId.get(i).toString();
        }
		
		return "\n*Literature ID: " + this.id + "\nTitle: " + this.title + "\nPublisher: " + this.publisher + "\nYear: "
				+ this.year + "\nAvailable: " + this.count + "\nUser count: " + this.userCount + "\nUserId's: " + listStream;
	}

	/**
	 * Take this literature with default return term and save information in userId, outDate and extraDays lists, 
	 * decreases this.count and increases this.userCount by 1
	 */
	public void take(int userId)
	{
		if (this.count > 0)
		{
			this.count--;
			this.userCount++;
			this.outDate.add(LocalDate.now());
			this.extraDays.add(0);
			this.userId.add(userId);
		} else
			System.out.println("Literature is out of stock");
	}

	/**
	 * Take this literature with extended return term and save information in userId, outDate and extraDays lists, 
	 * decreases this.count and increases this.userCount by 1
	 */
	public void take(int userId, int extraDays) 
	{
		if (this.count > 0)
		{
			this.count--;
			this.userCount++;
			this.outDate.add(LocalDate.now());
			this.extraDays.add(extraDays);
			this.userId.add(userId);
		} else
			System.out.println("Literature is out of stock");
	}

	/**
	 * Deletes user information from userId, OutDate and ExtraDays lists, increases this.count and decreases this.userCount by 1
	 */
	final public void returnItem(int userId)
	{
		int i;
		for (i = 0; i < this.userId.size(); i++)
			if (this.userId.get(i) == userId)
				break;

		this.count++;
		this.userCount--;
		this.outDate.remove(i);
		this.extraDays.remove(i);
		this.userId.remove(i);
	}

	/**
	 * Counts how many days have passed since return term of this literature for the user has ended
	 */
	public long countDays(int userId)
	{
		int i;
		for (i = 0; i < this.userId.size(); i++)
			if (this.userId.get(i) == userId)
				break;

		LocalDate inDate = LocalDate.now();
		long timeTaken = this.outDate.get(i).until(inDate, ChronoUnit.DAYS) - this.extraDays.get(i);

		if (timeTaken < 0)
			timeTaken = 0;

		return timeTaken;
	}

	// ============= Polymorphic methods ==============
	/**
	 * 
	 */
	public long daysTaken(int userId)
	{
		return countDays(userId);
	}

	// =============== Cloning ===================
	/**
	 * Creates a deep copy this literature 
	 */
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException
	{
		Object copy = super.clone();

		((Literature) copy).outDate = (LinkedList<LocalDate>) this.outDate.clone();
		((Literature) copy).extraDays = (LinkedList<Integer>) this.extraDays.clone();
		((Literature) copy).userId = (LinkedList<Integer>) this.userId.clone();

		return copy;
	}

}
