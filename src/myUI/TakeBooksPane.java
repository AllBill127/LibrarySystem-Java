package myUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import myExceptions.LiteratureAllTakenException;
import myExceptions.LiteratureAlreadyTakenException;
import myExceptions.LiteratureLimitException;
import myLiterature.Book;
import myLiterature.Journal;
import myLiterature.Literature;
import myUser.User;
import myUtility.Utilities;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;

/**
 * TakeBooksPane extends {@code Thread} and creates a window for taking new literature in a new thread
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class TakeBooksPane extends Thread
{
	LinkedList<Literature> catalog;
	private JFrame frame;
	private User user;
	private JTextField txtLiteratureId;
	
	private JScrollPane bookTableScrollPane;
	private JTable bookTable;
	
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
	public TakeBooksPane(User user)
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
		frame.setBounds(100, 100, 720, 480);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				    closePressed();
				  }
		});
		frame.setLocationRelativeTo(null);
		
		JLabel lblPaneName = new JLabel("Take Books");
		lblPaneName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPaneName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaneName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPaneName.setForeground(SystemColor.info);
		lblPaneName.setFont(new Font("Arial", Font.BOLD, 16));
		lblPaneName.setBounds(10, 10, 686, 50);
		frame.getContentPane().add(lblPaneName);
		
		JLabel lblTableName = new JLabel("Currently available books in the library");
		lblTableName.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableName.setForeground(SystemColor.info);
		lblTableName.setFont(new Font("Arial", Font.BOLD, 16));
		lblTableName.setBounds(30, 67, 645, 30);
		frame.getContentPane().add(lblTableName);
		
		JButton btnUpdateDebt = new JButton("Update");
		btnUpdateDebt.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUpdateDebt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.remove(bookTableScrollPane);
				createBookTable();
			}
		});
		btnUpdateDebt.setFont(new Font("Arial", Font.BOLD, 10));
		btnUpdateDebt.setMargin(new Insets(2, 2, 2, 2));
		btnUpdateDebt.setBounds(628, 73, 47, 20);
		frame.getContentPane().add(btnUpdateDebt);
		
		createBookTable();
		
		txtLiteratureId = new JTextField();
		txtLiteratureId.setHorizontalAlignment(SwingConstants.CENTER);
		txtLiteratureId.setForeground(SystemColor.textInactiveText);
		txtLiteratureId.setFont(new Font("Arial", Font.PLAIN, 14));
		txtLiteratureId.setText("Insert Literature ID to take");
		txtLiteratureId.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtLiteratureId.setForeground(SystemColor.inactiveCaptionText);
				txtLiteratureId.setText("");
			}
		});
		txtLiteratureId.setBounds(65, 366, 310, 30);
		frame.getContentPane().add(txtLiteratureId);
		txtLiteratureId.setColumns(10);
		
		JButton btnTakeBook = new JButton("Take Book");
		btnTakeBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				takeBookPressed();
			}
		});
		btnTakeBook.setBackground(UIManager.getColor("Button.background"));
		btnTakeBook.setFont(new Font("Arial", Font.BOLD, 14));
		btnTakeBook.setBounds(385, 365, 120, 30);
		frame.getContentPane().add(btnTakeBook);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closePressed();
			}
		});
		btnClose.setBackground(UIManager.getColor("Button.background"));
		btnClose.setFont(new Font("Arial", Font.BOLD, 14));
		btnClose.setBounds(540, 365, 100, 30);
		frame.getContentPane().add(btnClose);
	}
	
	/**
	 * If close button or window close is pressed, dispose of this window
	 */
	private void closePressed()
	{
		frame.dispose();
	}
	
	/**
	 * If take book button is pressed, get user input. If literature is in library and this user can take it, 
	 * update literature info and rewrite literature file, update user info, otherwise - show error message to user
	 */
	private void takeBookPressed()
	{
		int id = Integer.valueOf(txtLiteratureId.getText());
		
		int bookIndex = -1;
		for(int i = 0; i < catalog.size(); ++i)
		{
			if(catalog.get(i).getId() == id)
			{
				bookIndex = i;
				break;
			}
		}
		
		if(bookIndex != -1)
		{	
			boolean error = false;
			try
			{
				user.takeBook(catalog.get(bookIndex));
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						Utilities.writeListToBinary(catalog, "Books.bin", false);
					}
				});
			} catch (LiteratureAllTakenException e)
			{
				txtLiteratureId.setForeground(SystemColor.textInactiveText);
				txtLiteratureId.setText("Book with ID:" + id + " is currently unavailable");
				error = true;
			} catch (LiteratureLimitException e)
			{
				txtLiteratureId.setForeground(SystemColor.textInactiveText);
				txtLiteratureId.setText(user.getPlan().getName() + " plan book limit reached");
				error = true;
			} catch (LiteratureAlreadyTakenException e)
			{
				txtLiteratureId.setForeground(SystemColor.textInactiveText);
				txtLiteratureId.setText("Book with ID:" + id + " is already in possesion");
				error = true;
			}

			if(!error)
			{
				txtLiteratureId.setForeground(SystemColor.inactiveCaptionText);
				txtLiteratureId.setText("Book taken");
			}
		}
		else
		{
			txtLiteratureId.setForeground(SystemColor.textInactiveText);
			txtLiteratureId.setText("Book with ID:" + id + " is not in the library");
		}
	}
	
	/**
	 * Creates a new table from all literature list
	 */
	private void createBookTable()
	{
		bookTableScrollPane = new JScrollPane();
		bookTableScrollPane.setBounds(30, 96, 645, 235);
		frame.getContentPane().add(bookTableScrollPane);
		String[] columnNames = {"Literature ID", "Title", "Author", "Publisher", "Issue No.", "Year", "Available"};
		Object[][] data = new Object[catalog.size()][columnNames.length];
		for(int i = 0; i < catalog.size(); ++i)
		{
			data[i][0] = catalog.get(i).getId();
			data[i][1] = catalog.get(i).getTitle();
			
			if(catalog.get(i) instanceof Book)
				data[i][2] = ((Book)catalog.get(i)).getAuthor();	//Depends on the class
			else
				data[i][2] = "";
			
			data[i][3] = catalog.get(i).getPublisher();
			
			if(catalog.get(i) instanceof Journal)
				data[i][4] = ((Journal)catalog.get(i)).getPublicationNr();	//Depends on the class
			else
				data[i][4] = "";
			
			data[i][5] = catalog.get(i).getYear();
			data[i][6] = catalog.get(i).getCount();
		}
		bookTable = new JTable(data, columnNames);
		bookTable.setMinimumSize(new Dimension(50, 0));
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
	    for (int column = 0; column < table.getColumnCount()-3; column++) {
	        int width = Math.max(15, table.getColumnModel().getColumn(column).getPreferredWidth());
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	    columnModel.getColumn(4).setPreferredWidth(55);
		columnModel.getColumn(5).setPreferredWidth(35);
		columnModel.getColumn(6).setPreferredWidth(55);
	}
}