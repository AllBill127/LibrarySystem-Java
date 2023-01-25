package myUI;

import javax.swing.JFrame;
import myUser.User;
import myUtility.Utilities;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * UserPain extends {@code Thread} and creates a user window in a new thread
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class UserPane extends Thread
{
	private JFrame frame;
	private User user;
	private JScrollPane bookTableScrollPane;
	private JTable bookTable;
	private JLabel lblDebt;
	
	/**
	 * Initialize window
	 */
	public void run()
	{
		initialize();
		frame.setVisible(true);
	}
	
	/**
	 * Create and start UserPane thread
	 */
	public UserPane(User user)
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
		frame.setBounds(100, 100, 620, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				    logOutPressed();
				  }
		});
		frame.setLocationRelativeTo(null);
		
		JLabel lblUsername = new JLabel(user.getUsername());
		lblUsername.setBounds(372, 65, 231, 28);
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setForeground(SystemColor.info);
		lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
		frame.getContentPane().add(lblUsername);
		
		JButton btnLogOut = new JButton("->");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logOutPressed();
			}
		});
		btnLogOut.setMargin(new Insets(2, 2, 2, 2));
		btnLogOut.setBounds(546, 10, 50, 35);
		btnLogOut.setBackground(UIManager.getColor("Button.background"));
		btnLogOut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogOut.setFont(new Font("Arial", Font.BOLD, 24));
		frame.getContentPane().add(btnLogOut);
		
		createBookTable();
		
		JLabel lblTableName = new JLabel("Currently taken books");
		lblTableName.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTableName.setForeground(SystemColor.info);
		lblTableName.setFont(new Font("Arial", Font.BOLD, 16));
		lblTableName.setBounds(45, 65, 231, 28);
		frame.getContentPane().add(lblTableName);
		
		JButton btnTakeBooks = new JButton("Take new Books");
		btnTakeBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takeBooksPressed();
			}
		});
		btnTakeBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTakeBooks.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnTakeBooks.setFont(new Font("Arial", Font.BOLD, 14));
		btnTakeBooks.setBounds(44, 269, 281, 44);
		frame.getContentPane().add(btnTakeBooks);
		
		JButton btnReturnBooks = new JButton("Return books");
		btnReturnBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				returnBookPressed();
			}
		});
		btnReturnBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		btnReturnBooks.setFont(new Font("Arial", Font.BOLD, 14));
		btnReturnBooks.setAlignmentX(0.5f);
		btnReturnBooks.setBounds(44, 212, 281, 44);
		frame.getContentPane().add(btnReturnBooks);
		
		lblDebt = new JLabel("Debt: " + user.getDebt()/100 + "." + user.getDebt()%100 + " eur.");
		lblDebt.setHorizontalTextPosition(SwingConstants.LEFT);
		lblDebt.setHorizontalAlignment(SwingConstants.LEFT);
		lblDebt.setForeground(SystemColor.info);
		lblDebt.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDebt.setBounds(372, 94, 140, 28);
		frame.getContentPane().add(lblDebt);
		
		JTextArea lblPlan = new JTextArea(user.getPlan().toString());
		lblPlan.setBackground(Color.DARK_GRAY);
		lblPlan.setForeground(SystemColor.info);
		lblPlan.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPlan.setBounds(372, 123, 231, 85);
		frame.getContentPane().add(lblPlan);
		
		JButton btnUserOptions = new JButton("Change user data");
		btnUserOptions.setFont(new Font("Arial", Font.BOLD, 14));
		btnUserOptions.setBounds(372, 270, 195, 44);
		frame.getContentPane().add(btnUserOptions);
		
		JButton btnReturnDebt = new JButton("Return debt");
		btnReturnDebt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				returnDebtPressed();
			}
		});
		btnReturnDebt.setFont(new Font("Arial", Font.BOLD, 14));
		btnReturnDebt.setBounds(372, 213, 195, 44);
		frame.getContentPane().add(btnReturnDebt);
		
		JButton btnUpdateDebt = new JButton("Update");
		btnUpdateDebt.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateDebt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblDebt.setText("Debt: " + user.getDebt()/100 + "." + user.getDebt()%100 + " eur.");
			}
		});
		btnUpdateDebt.setFont(new Font("Arial", Font.BOLD, 10));
		btnUpdateDebt.setMargin(new Insets(2, 2, 2, 2));
		btnUpdateDebt.setBounds(500, 98, 47, 20);
		frame.getContentPane().add(btnUpdateDebt);
		
		JButton btnUpdateTable = new JButton("Update");
		btnUpdateTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.remove(bookTableScrollPane);
				createBookTable();
			}
		});
		btnUpdateTable.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateTable.setMargin(new Insets(2, 2, 2, 2));
		btnUpdateTable.setFont(new Font("Arial", Font.BOLD, 10));
		btnUpdateTable.setBounds(278, 68, 47, 20);
		frame.getContentPane().add(btnUpdateTable);
	}
	
	/**
	 * If log out button or window close button is pressed, update users file, dispose of user window and create new LoginPane
	 */
	private void logOutPressed()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Utilities.updateUsers(user);
				frame.dispose();
				new LoginPane();
			}
		});
	}
	
	/**
	 * If return book button is pressed, create new ReturnBookPane for this user
	 */
	private void returnBookPressed()
	{
		new ReturnBookPane(this.user);
	}
	
	/**
	 * If take book button is pressed, create new TakeBooksPane for this user
	 */
	private void takeBooksPressed()
	{
		new TakeBooksPane(this.user);
	}
	
	/**
	 * If return debt button is pressed, create new DebtPane for this user
	 */
	private void returnDebtPressed()
	{
		new DebtPane(this.user);
	}
	
	/**
	 * Creates a new table from this users literature list
	 */
	private void createBookTable()
	{
		bookTableScrollPane = new JScrollPane();
		bookTableScrollPane.setBounds(45, 92, 280, 110);
		frame.getContentPane().add(bookTableScrollPane);
		String[] columnNames = {"Literature ID", "Title", "Days left"};
		Object[][] data = new Object[user.getLiteratureCount()][columnNames.length];
		for(int i = 0; i < user.getLiteratureCount(); ++i)
		{
			data[i][0] = user.getBookFromList(i).getId();
			data[i][1] = user.getBookFromList(i).getTitle();
			data[i][2] = user.getDaysLeft(i);
		}
		bookTable = new JTable(data, columnNames);
		bookTable.setEnabled(false);
		resizeColumnWidth(bookTable);
		bookTableScrollPane.setViewportView(bookTable);
	}
	
	/**
	 * Resizes given table so cells are adjusted to bet fit text in them
	 * @param table a table to be resized
	 */
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount() - 1; column++) {
	        int width = Math.max(15, table.getColumnModel().getColumn(column).getPreferredWidth());
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    columnModel.getColumn(2).setPreferredWidth(60);
	}
}
