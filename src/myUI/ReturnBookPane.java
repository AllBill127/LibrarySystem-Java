package myUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import myUser.User;
import myUtility.Utilities;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import myLiterature.Literature;
import java.awt.Insets;

/**
 * ReturnBookPane extends {@code Thread} and creates a window for returning literature in a new thread
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class ReturnBookPane extends Thread
{
	LinkedList<Literature> catalog;
	private JFrame frame;
	private User user;
	private JTextField txtLiteratureId;
	
	/**
	 * Get all literature list from binary file and initialize window
	 */
	@SuppressWarnings("unchecked")
	public void run()
	{
		try
		{
			catalog = (LinkedList<Literature>) Utilities.readFromBinary("Books.bin");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Create and start DebtPane thread
	 */
	public ReturnBookPane(User user)
	{
		this.user = user;
		start();
	}
	
	/**
	 * Initialize the contents of the frame
	 * @wbp.parser.entryPoint
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 360, 220);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				    closePressed();
				  }
		});
		frame.setLocationRelativeTo(null);
		
		JLabel lblPaneName = new JLabel("Return Book");
		lblPaneName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPaneName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaneName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPaneName.setForeground(SystemColor.info);
		lblPaneName.setFont(new Font("Arial", Font.BOLD, 16));
		lblPaneName.setBounds(10, 10, 326, 50);
		frame.getContentPane().add(lblPaneName);
		
		txtLiteratureId = new JTextField();
		txtLiteratureId.setHorizontalAlignment(SwingConstants.CENTER);
		txtLiteratureId.setForeground(SystemColor.textInactiveText);
		txtLiteratureId.setFont(new Font("Arial", Font.PLAIN, 14));
		txtLiteratureId.setText("Insert Literature ID to return");
		txtLiteratureId.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtLiteratureId.setForeground(SystemColor.inactiveCaptionText);
				txtLiteratureId.setText("");
			}
		});
		txtLiteratureId.setBounds(68, 70, 210, 30);
		frame.getContentPane().add(txtLiteratureId);
		txtLiteratureId.setColumns(10);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.setMargin(new Insets(2, 10, 2, 10));
		btnReturnBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				returnBookPressed();
			}
		});
		btnReturnBook.setBackground(UIManager.getColor("Button.background"));
		btnReturnBook.setFont(new Font("Arial", Font.BOLD, 14));
		btnReturnBook.setBounds(47, 117, 120, 30);
		frame.getContentPane().add(btnReturnBook);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closePressed();
			}
		});
		btnClose.setBackground(UIManager.getColor("Button.background"));
		btnClose.setFont(new Font("Arial", Font.BOLD, 14));
		btnClose.setBounds(197, 117, 100, 30);
		frame.getContentPane().add(btnClose);		
	}
	
	/**
	 * If close button or window close is pressed, rewrite literature file and disose of this window
	 */
	private void closePressed()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Utilities.writeListToBinary(catalog, "Books.bin", false);
				frame.dispose();
			}
		});
	}
	
	/**
	 * If return book is pressed, get user input. If literature id matches literature in library 
	 * and user has currently taken this literature, return it
	 */
	private void returnBookPressed()
	{
		int id = Integer.valueOf(txtLiteratureId.getText());
		
		int bookIndex = 0;
		for(int i = 0; i < user.getLiteratureCount(); ++i)
		{
			if(user.getBookFromList(i).getId() == id)
			{
				bookIndex = i;
				break;
			}
		}
		
		if(user.getLiteratureCount() != 0 && user.getBookFromList(bookIndex).getId() == id)
		{			
			int i;
			for(i = 0; i < catalog.size(); ++i)
				if(user.getBookFromList(bookIndex).getId() == catalog.get(i).getId())
					break;
			
			catalog.get(i).returnItem(user.getId());
			user.returnBook(bookIndex);
			
			txtLiteratureId.setForeground(SystemColor.inactiveCaptionText);
			txtLiteratureId.setText("Book returned");
		}
		else
		{
			txtLiteratureId.setForeground(SystemColor.textInactiveText);
			txtLiteratureId.setText("Book with ID:" + id + " not taken");
		}
	}
}
