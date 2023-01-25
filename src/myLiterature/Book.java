package myLiterature;

/**
 * Book extends {@code Literature}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class Book extends Literature
{
	private static final long serialVersionUID = 1L;
	
	protected String author;
	
	//============= Constructs ==============
	/**
	 * Constructs a new empty Book object
	 */
	public Book()
	{
		super();
		this.author = null;
	}
	
	/**
	 * Constructs a new Journal object with specified values
	 * 
	 * @param id identification number of this literature
	 * @param title full title of this literature
	 * @param author full name of this books author
	 * @param publisher publishing house name of this literature
	 * @param year year of this literatures publication
	 * @param count number of this literatures copies in the library
	 */
	public Book(int id, String title, String author, String publisher, int year, int count)
	{
		super(id, title, publisher, year, count);
		this.author = author;
	}
	
	
	//============= Setters ==============
	/**
	 * Sets this books author name
	 * @param author
	 */
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	
	//============= Getters ==============
	/**
	 * 
	 * @return full name of this books author
	 */
	public String getAuthor()
	{
		return this.author;
	}
	
	
	//============= Methods ==============	
	public String toString()
	{
		String listStream = "";
        for(int i = 0; i < this.userId.size(); ++i)
        {
        	listStream += this.userId.get(i).toString();
        }
        
		return "\n*Literature(Book) ID: " + this.id + "\nTitle: " + this.title + "\nAuthor: " + this.author + "\nPublisher: " + this.publisher + "\nYear: " + this.year + "\nAvailable: " + this.count + "\nUser count: " + this.userCount + "\nUserId's: " + listStream;
	}
	
	/**
	 * 
	 */
	public void take(int userId)
	{
		super.take(userId, 3);
	}
	
	/**
	 * 
	 */
	public void take(int userId, int extraDays)
	{
		super.take(userId, extraDays+3);
	}
	
	//=============== Cloning ===================
	/**
	 * Creates a deep copy of this book
	 */
	public Object clone() throws CloneNotSupportedException
	{
		Book copy = (Book)super.clone();
		copy.author = this.author;
		
		return copy;
	}
}
