package myUI;

import java.lang.Thread;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import myUtility.*;
import myUser.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.LinkedList;
import javax.swing.SwingConstants;

import myLiterature.Book;
import myLiterature.Journal;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * LoginPane extends {@code Thread} and creates a login window in a new thread
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class LoginPane extends Thread
{

	private LinkedList<User> users = new LinkedList<>();
	private JFrame frame;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private User user;
	private JLabel loginWarning = new JLabel("");
	
	private JFrame newU;
	private JFrame newB;
	

	/**
	 * Load user list and initialize window
	 */
	@SuppressWarnings("unchecked")
	public void run()
	{
		try
		{
			users = (LinkedList<User>) Utilities.readFromBinary("Users.bin");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Create and start LoginPane thread
	 */
	public LoginPane()
	{
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
		frame.setBounds(100, 100, 420, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 36));
		lblLogin.setForeground(SystemColor.info);
		lblLogin.setBounds(150, 43, 110, 53);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(SystemColor.info);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
		lblUsername.setBounds(57, 150, 86, 25);
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(173, 149, 178, 25);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(SystemColor.info);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
		lblPassword.setBounds(57, 201, 86, 25);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(173, 201, 178, 24);
		frame.getContentPane().add(txtPassword);
		
		// Define loginWarning label
		loginWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginWarning.setHorizontalTextPosition(SwingConstants.CENTER);
		loginWarning.setHorizontalAlignment(SwingConstants.CENTER);
		loginWarning.setFont(new Font("Arial", Font.PLAIN, 14));
		loginWarning.setForeground(Color.RED);
		loginWarning.setBackground(Color.DARK_GRAY);
		loginWarning.setBounds(56, 106, 294, 34);
		frame.getContentPane().add(loginWarning);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				loginPressed();
			}
		});
		
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setBounds(79, 288, 103, 53);
		frame.getContentPane().add(btnLogin);
		
		JButton btnCancel = new JButton("Exit");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exitPressed();
			}
		});
		btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
		btnCancel.setBounds(225, 288, 103, 53);
		frame.getContentPane().add(btnCancel);
		
		JButton btnNewUser = new JButton("New User");
		btnNewUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newUserPressed();
			}
		});
		btnNewUser.setBounds(10, 352, 100, 21);
		frame.getContentPane().add(btnNewUser);
		
		JButton btnNewBook = new JButton("New book");
		btnNewBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newBookPressed();
			}
		});
		btnNewBook.setBounds(296, 352, 100, 21);
		frame.getContentPane().add(btnNewBook);
	}
	
	/**
	 * When login button is pressed get inputed username and password. If match from existing users is found 
	 * dispose of this window and create new UserPane for matched user
	 * 
	 */
	private void loginPressed()
	{
		String Username = txtUsername.getText();
		String Password = txtPassword.getText();
		
		for(int i = 0; i < users.size(); ++i)
		{
			if(users.get(i).getUsername().equals(Username) && users.get(i).getPassword().equals(Password))
			{
				user = (User) users.get(i);
				break;
			}
		}
		
		if(user == null)
		{
			loginWarning.setText("Incorrect username or password");	
			txtUsername.setText("");
			txtPassword.setText("");
			txtUsername.requestFocus();
		}
		else
		{
			frame.dispose();
			new UserPane(user);
		}
	}
	
	/**
	 * If new book button is pressed open a new window were a new book can be added to the library
	 */
	public void newBookPressed()
	{
		newB = new JFrame();
		newB.getContentPane().setBackground(Color.DARK_GRAY);
		newB.setBounds(100, 100, 440, 230);
		newB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newB.setLocationRelativeTo(null);
		newB.getContentPane().setLayout(null);
		
		final JTextField txtType = new JTextField();
		txtType.setBounds(10, 10, 100, 25);
		newB.getContentPane().add(txtType);
		
		final JTextField txtId = new JTextField();
		txtId.setBounds(120, 10, 70, 25);
		newB.getContentPane().add(txtId);
		
		final JTextField txtTitle = new JTextField();
		txtTitle.setBounds(200, 10, 100, 25);
		newB.getContentPane().add(txtTitle);
		
		final JTextField txtAuthor = new JTextField(); //different
		txtAuthor.setBounds(310, 10, 100, 25);
		newB.getContentPane().add(txtAuthor);
		
		final JTextField txtPublisher = new JTextField();
		txtPublisher.setBounds(10, 50, 100, 25);
		newB.getContentPane().add(txtPublisher);
		
		final JTextField txtPublicationNr = new JTextField(); //different
		txtPublicationNr.setBounds(120, 50, 70, 25);
		newB.getContentPane().add(txtPublicationNr);
		
		final JTextField txtYear = new JTextField();
		txtYear.setBounds(200, 50, 100, 25);
		newB.getContentPane().add(txtYear);
		
		final JTextField txtCount = new JTextField();
		txtCount.setBounds(310, 50, 100, 25);
		newB.getContentPane().add(txtCount);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//add user to this.userList and append to Users.bin
				int id = Integer.valueOf(txtId.getText()), publicationNr = Integer.valueOf(txtPublicationNr.getText()), 
						year = Integer.valueOf(txtYear.getText()), count = Integer.valueOf(txtCount.getText());
				String title = txtTitle.getText(), author = txtAuthor.getText(), publisher = txtPublisher.getText();
				try
				{
					if(txtType.getText().equals("Book"))
					{
						Utilities.writeToBinary(new Book(id, title, author, publisher, year, count), "Books.bin", true);
					}
					else if(txtType.getText().equals("Journal"))
					{
						Utilities.writeToBinary(new Journal(id, title, publisher, publicationNr, year, count), "Books.bin", true);
					}
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				newB.dispose();
			}
		});
		btnAdd.setBounds(180, 100, 80, 25);
		newB.getContentPane().add(btnAdd);
		
		JLabel lblInfo = new JLabel("1.Type 2.ID 3.Title 4.Author 5.Publisher 6.PublicationNr 7.Year 8.Count");
		lblInfo.setForeground(SystemColor.info);
		lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
		lblInfo.setBounds(10, 140, 420, 30);
		newB.getContentPane().add(lblInfo);
		
		newB.setVisible(true);
	}
	
	/**
	 * If new user button is pressed open a new window were a new user can be added
	 */
	public void newUserPressed()
	{
		newU = new JFrame();
		newU.getContentPane().setBackground(Color.DARK_GRAY);
		newU.setBounds(100, 100, 440, 230);
		newU.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newU.setLocationRelativeTo(null);
		newU.getContentPane().setLayout(null);
		
		final JTextField txtId = new JTextField();
		txtId.setBounds(10, 10, 100, 25);
		newU.getContentPane().add(txtId);
		
		final JTextField txtUsername = new JTextField();
		txtUsername.setBounds(120, 10, 70, 25);
		newU.getContentPane().add(txtUsername);
		
		final JTextField txtPassword = new JTextField();
		txtPassword.setBounds(200, 10, 100, 25);
		newU.getContentPane().add(txtPassword);
		
		final JTextField txtEmail = new JTextField();
		txtEmail.setBounds(310, 10, 100, 25);
		newU.getContentPane().add(txtEmail);
		
		final JTextField txtPhoneNr = new JTextField();
		txtPhoneNr.setBounds(10, 50, 100, 25);
		newU.getContentPane().add(txtPhoneNr);
		
		final JTextField txtPlan = new JTextField(); //different
		txtPlan.setBounds(120, 50, 70, 25);
		newU.getContentPane().add(txtPlan);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//add user to this.userList and append to Users.bin
				int id = Integer.valueOf(txtId.getText());
				String username = txtUsername.getText(), password = txtPassword.getText(), 
						email = txtEmail.getText(), phoneNr = txtPhoneNr.getText(), plan = txtPlan.getText();
				
				users.add(new User(id, username, password, email, phoneNr, plan));
				try
				{
					Utilities.writeToBinary(users.getLast(), "Users.bin", true);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				newU.dispose();
			}
		});
		btnAdd.setBounds(180, 100, 80, 25);
		newU.getContentPane().add(btnAdd);
		
		JLabel lblInfo = new JLabel("1.ID 2.Username 3.Password 4.Email 5.PhoneNr 6.PlanName");
		lblInfo.setForeground(SystemColor.info);
		lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
		lblInfo.setBounds(40, 140, 420, 30);
		newU.getContentPane().add(lblInfo);
		
		newU.setVisible(true);
	}
	
	/**
	 * When exit button or window close button is pressed exit the whole program
	 */
	public void exitPressed()
	{
		System.exit(0);
	}
}
