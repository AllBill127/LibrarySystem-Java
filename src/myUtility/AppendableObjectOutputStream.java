package myUtility;

import java.io.*;

/**
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class AppendableObjectOutputStream extends ObjectOutputStream
{
	/**
	 * Creates appendable ObjectOutputStream object from OutputStream set to appendable
	 * @param out OutputStream object
	 * @throws IOException
	 */
	protected AppendableObjectOutputStream(OutputStream out) throws IOException
	{
		super(out);
	}
	
	/**
	 * 
	 */
	@Override
	protected void writeStreamHeader() throws IOException
	{
		reset();
	}

}
