package myUtility;


import java.io.*;
import java.util.LinkedList;

import myLiterature.*;
import myUser.User;

/**
 * Utilities class with methods for writing/reading objects to/from binary files
 * {@link myUser.User}
 * 
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class Utilities
{
	/**
	 * Writes a given object to a given binary file
	 * @param obj Object to write to file
	 * @param fileName File name with extention to write to
	 * @param append if True then appends given file, if False - rewrites or creates given files
	 * @throws IOException
	 */
	public static void writeToBinary(Object obj, String fileName, boolean append) throws IOException
	{
		File file = new File(fileName);
		ObjectOutputStream objOutStream = null;
		
		try
		{
			if(file.exists() != true || !append)
				objOutStream = new ObjectOutputStream(new FileOutputStream(fileName));
			else
				objOutStream = new AppendableObjectOutputStream(new FileOutputStream(fileName, append));
				
			objOutStream.writeObject(obj);
			objOutStream.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				if(objOutStream != null)
					objOutStream.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Reads all objects from a given binary file
	 * @param fileName file name with extention to read from
	 * @return LinkedList of objects read from given file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static LinkedList readFromBinary(String fileName) throws IOException, ClassNotFoundException
	{
		File file = new File(fileName);
		LinkedList list = new LinkedList();
		
		if(file.exists())
		{
			ObjectInputStream objInStream = null;
			
			try
			{
				objInStream = new ObjectInputStream(new FileInputStream(fileName));
				while(true)
				{
					list.add(objInStream.readObject());
				}
			}
			catch(IOException e)
			{
				//e.printStackTrace();
			}finally{
				try
				{
					if(objInStream != null)
						objInStream.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
			
			return list;
		}
		
		return null;
	}
	
	/**
	 * Updates "Users.bin" binary file by reading current file, changing given User object in it and rewriting the file
	 * @param user User object that changed and needs updating
	 */
	@SuppressWarnings("unchecked")
	public static void updateUsers(User user)
	{
		LinkedList<User> users = new LinkedList<>();
		try
		{
			users = Utilities.readFromBinary("Users.bin");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		int i;
		for(i = 0; i < users.size(); ++i)
			if(users.get(i).getId() == user.getId())
				break;
		
		users.set(i, user);
		
		writeListToBinary(users, "Users.bin", false);
	}
	
	/**
	 * Writes a whole given list of objects to a given file
	 * @param list LinkedList of objects to be written
	 * @param fileName file name with extention to write to
	 * @param append if True - appends given list to given file, if False - rewrites or creates a given file
	 */
	@SuppressWarnings("rawtypes")
	public static void writeListToBinary(LinkedList list, String fileName, boolean append)
	{
		try
		{
			if(append)
			{
				for(int j = 0; j < list.size(); ++j)
					writeToBinary(list.get(j), fileName, true);
			}
			else
			{
				writeToBinary(list.get(0), fileName, false);
				for(int j = 1; j < list.size(); ++j)
					writeToBinary(list.get(j), fileName, true);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that initializes Books.bin and Users.bin files for testing
	 */
	public static void initTestLibrary()
	{
		//================== List of books in archive ===================
		LinkedList<Literature> BookArchive = new LinkedList<>();
		
		BookArchive.add(new Journal(321, "SpaceX's StarShip project", "Science News", 15, 2021, 2));
		BookArchive.add(new Book(654, "Harry Potter: The Beginning", "Joanne Rowling", "Bloomsbury Publishing", 1997, 20));
		BookArchive.add(new Book(987, "Hjkl Lkjh", "You He", "Dublin.co", 2001, 17));
		BookArchive.add(new Book(753, "Wine making secrets", "Mario the Italian", "Italy.readers", 2001, 17));
		BookArchive.add(new Journal(951, "National Geography", "Science News", 489, 2021, 1));

		Utilities.writeListToBinary(BookArchive, "Books.bin", false);
		
		
		//==================== List of archive users ====================
		LinkedList<User> userList = new LinkedList<>();
		
		userList.add(new User(111, "iki", "", "", "", "Intermediate"));
		userList.add(new User(222, "Mike Wasauski", "", "", "", "Beginner"));
		userList.add(new User(333, "Spike", "", "", "", "Beginner"));
		userList.add(new User(444, "Nike", "", "", "", "Beginner"));
		userList.add(new User(711, "Like", "", "", "", "Beginner"));
		userList.add(new User(711, "Fight", "", "", "", "Beginner"));
		userList.add(new User(711, "Rock", "", "", "", "Beginner"));
		userList.add(new User(711, "Paper", "", "", "", "Beginner"));
		userList.add(new User(711, "Scizors", "", "", "", "Beginner"));
		
		Utilities.writeListToBinary(userList, "Users.bin", false);
	}
}
