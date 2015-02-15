package edu.pitt.ui;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;

/**
 * This UI is a login interface
 * 
 * @author leileiliu
 *
 */
public class LoginUI  {
	private JFrame frmBankLogin;
	private JTextField txtLoginName;
	private JTextField txtPassword;
	private JLabel lblLoginName;
	private JLabel lblPassword;
	private JButton btnlogin;
	private JButton btnExit;
	
	public LoginUI() {
		initialize() ;
	}


	/**
	 * Create the frame.
	 */
	public void initialize() {
		frmBankLogin = new JFrame();
		frmBankLogin.setTitle("Bank 1017 Login");
		frmBankLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankLogin.setBounds(100, 100, 450, 300);
		frmBankLogin.getContentPane().setLayout(null);

		lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(48, 62, 90, 16);
		frmBankLogin.getContentPane().add(lblLoginName);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(48, 111, 90, 16);
		frmBankLogin.getContentPane().add(lblPassword);

		txtLoginName = new JTextField();
		txtLoginName.setBounds(171, 56, 230, 28);
		frmBankLogin.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setBounds(171, 105, 230, 28);
		frmBankLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);

		/**
		 * Only the user input the correct login name and the password can he/she continue seeing the next interface
		 */
		btnlogin = new JButton("Login");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginName =  txtLoginName.getText();
				String pin = null;
				if(isInteger(txtPassword.getText())){
					pin = txtPassword.getText();
					Bank bank = new Bank();
					for(Customer customer: bank.getCustomerList()){
						if(customer.getLoginName().equalsIgnoreCase(loginName)){
							if(customer.getPin().equalsIgnoreCase(pin)){
								AccountDetailsUI ad = new AccountDetailsUI(customer);
							}
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Password must be numerical");
				}
				Security s = new Security();
				Customer c = s.validateLogin(loginName,Integer.parseInt(pin));
				if(c!=null){
					AccountDetailsUI ad = new AccountDetailsUI(c); 
					frmBankLogin.setVisible(false);

					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							JOptionPane.showMessageDialog(null,"Session expired");
							System.exit(0);
						}
					};
					
					Timer timer = new Timer(60 * 1000,taskPerformer);
					timer.setRepeats(false);
					timer.start();
				}
				else{
					JOptionPane.showMessageDialog(null,"Invalid Login");
				}
				
			}
		});
		btnlogin.setBounds(233, 171, 61, 29);
		frmBankLogin.getContentPane().add(btnlogin);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBankLogin.setVisible(false);
				System.exit(0);
			}
		});
		btnExit.setBounds(321, 171, 61, 29);
		frmBankLogin.getContentPane().add(btnExit);

		frmBankLogin.setVisible(true);

	}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// only got here if we didn't return false
		return true;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
