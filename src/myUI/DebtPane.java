package myUI;

import java.awt.Color;
import javax.swing.JFrame;

import myUser.User;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * DebtPain extends {@code Thread} and creates a window for returning debt in a new thread
 * @author Aleksandras Bilevièius
 * @version 1.0
 * @since 2021-05-17
 */
public class DebtPane extends Thread
{
	private JFrame frame;
	private User user;
	private JTextField txtMoney;
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
	 * Create and start DebtPane thread
	 */
	public DebtPane(User user)
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
		frame.setBounds(100, 100, 360, 280);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JLabel lblPaneName = new JLabel("Return Debt");
		lblPaneName.setHorizontalTextPosition(SwingConstants.CENTER);
		lblPaneName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPaneName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPaneName.setForeground(SystemColor.info);
		lblPaneName.setFont(new Font("Arial", Font.BOLD, 16));
		lblPaneName.setBounds(10, 10, 326, 50);
		frame.getContentPane().add(lblPaneName);
		
		lblDebt = new JLabel("Currenr debt: " + user.getDebt()/100 + "." + user.getDebt()%100 + " eur.");
		lblDebt.setForeground(SystemColor.info);
		lblDebt.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDebt.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDebt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDebt.setBounds(20, 70, 316, 28);
		frame.getContentPane().add(lblDebt);
		
		txtMoney = new JTextField();
		txtMoney.setForeground(SystemColor.textInactiveText);
		txtMoney.setFont(new Font("Arial", Font.PLAIN, 14));
		txtMoney.setText("Insert amount to return");
		txtMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtMoney.setForeground(SystemColor.inactiveCaptionText);
				txtMoney.setText("");
			}
		});
		txtMoney.setBounds(30, 117, 150, 30);
		frame.getContentPane().add(txtMoney);
		txtMoney.setColumns(10);
		
		JButton btnReturnDebt = new JButton("Return Debt");
		btnReturnDebt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				returnDebtPressed();
			}
		});
		btnReturnDebt.setBackground(UIManager.getColor("Button.background"));
		btnReturnDebt.setFont(new Font("Arial", Font.BOLD, 14));
		btnReturnDebt.setBounds(192, 117, 120, 30);
		frame.getContentPane().add(btnReturnDebt);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		btnClose.setBackground(UIManager.getColor("Button.background"));
		btnClose.setFont(new Font("Arial", Font.BOLD, 14));
		btnClose.setBounds(123, 181, 100, 30);
		frame.getContentPane().add(btnClose);
		
	}
	
	/**
	 * If return debt button is pressed, get input from user and subtract input amount from this users debt.
	 * If input amount is more than debt amount then return change to user  
	 */
	private void returnDebtPressed()
	{
		double money = Double.valueOf(txtMoney.getText()) * 100;
		if(money < user.getDebt())
		{
			user.returnDebt(money);
			txtMoney.setForeground(SystemColor.textInactiveText);
			txtMoney.setText("Insert amount to return");
		}
		else
		{
			double change = money - user.getDebt();
			user.returnDebt(money - change);
			txtMoney.setForeground(SystemColor.inactiveCaptionText);
			txtMoney.setText("Your change is " + change/100);
		}

		lblDebt.setText("Currenr debt: " + user.getDebt()/100 + "." + user.getDebt()%100 + " eur.");
	}
}
