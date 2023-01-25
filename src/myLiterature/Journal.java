package myLiterature;

import myInterface.*;

/**
 * Journal extends {@code Literature}
 * 
 * {@link myInterface.TakeableWithExtention}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class Journal extends Literature
	implements TakeableWithExtention
{
	private static final long serialVersionUID = 1L;
	
	protected int publicationNr;
	
	//============= Constructs ==============
	/**
	 * Constructs a new empty Journal object
	 */
	public Journal()
	{
		super();
		this.publicationNr = 0;
	}
	
	/**
	 * Constructs a new Journal object with specified values
	 * 
	 * @param id identification number of this journal
	 * @param title full title of this journal
	 * @param publisher publishing house name of this journal
	 * @param nr issue number of this journal
	 * @param year year of this journal publication
	 * @param count number of this journal copies in the library
	 */
	public Journal(int id, String title, String publisher, int nr, int year, int count)
	{
		super(id, title, publisher, year, count);
		this.publicationNr = nr;
		this.count = count;
	}
		
	
	//============= Setters ==============
	/**
	 * Sets this journals issue number
	 * @param nr
	 */
	public void setPublicationNr(int nr)
	{
		this.publicationNr = nr;
	}
	
	
	//============= Getters ==============
	/**
	 * 
	 * @return this journals issue number
	 */
	public int getPublicationNr()
	{
		return this.publicationNr;
	}
	
	
	//============= Methods ==============
	public String toString()
	{
		String listStream = "";
        for(int i = 0; i < this.userId.size(); ++i)
        {
        	listStream += this.userId.get(i).toString();
        }
		
		return "\n*Literature(Journal) ID: " + this.id + "\nTitle: " + this.title + "\nPublisher: " + this.publisher + "\nYear: " + this.year + "\nPublication Nr.: " + this.publicationNr + "\nAvailable: " + this.count + "\nUser count: " + this.userCount + "\nUserId's: " + listStream;
	}
	
	/**
	 * 
	 */
	public void take(int userId)
	{
		super.take(userId, 2);
	}
	
	/**
	 * 
	 */
	public void take(int userId, int extraDays)
	{
		super.take(userId, extraDays+2);
	}
	
	/**
	 * Extends users return term for this journal 
	 */
	public void extendTerm(int userId, int extraDays)
	{
		int i;
		for(i = 0; i < this.userId.size(); i++)
			if(this.userId.get(i) == userId)
				break;
		
		int days = this.extraDays.get(i);
		this.extraDays.set(i, days + extraDays);
	}
	
	//=============== Cloning ===================
	/**
	 * Creates a deep copy of this journal
	 */
	public Object clone() throws CloneNotSupportedException
	{
		Journal copy = (Journal)super.clone();
		copy.publicationNr = this.publicationNr;
		
		return copy;
	}
}
