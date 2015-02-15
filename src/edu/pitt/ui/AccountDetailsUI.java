package edu.pitt.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Account;
import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.bank.Transaction;

/**
 * This UI is designed for showing users the account details of their account
 * 
 * @author leileiliu
 *
 */
public class AccountDetailsUI {

	private JFrame frmBankAccountDetails;
	private JTextField txtAmount;
	private JLabel lblGreeting;
	private JLabel lblYourAccounts;
	private JComboBox cboAccounts;
	private JLabel lblAccountDetails;
	private JLabel lblAmount;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnShowTransaction;
	private JButton btnExit;
	private Customer accountOwner;


	/**
	 * Create the application.
	 */
	public AccountDetailsUI(Customer c) {
		accountOwner = c;
		initialize();
		frmBankAccountDetails.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankAccountDetails = new JFrame();
		frmBankAccountDetails.setTitle("Bank1017 Account Details");
		frmBankAccountDetails.setBounds(100, 100, 450, 300);
		frmBankAccountDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankAccountDetails.getContentPane().setLayout(null);

		String name = accountOwner.getFirstName() + " " + accountOwner.getLastName(); 
		String greeting = ", " + "welcome to 1017 bank. You have the following permissions in this system: ";
		Security s = new Security();
		String permissions = "";
		for(String permi: s.listUserGroups(accountOwner.getCustomerID())){
			permissions += permi;
		}
		lblGreeting = new JLabel("<html>"+ name + greeting + permissions+"</html>");
		lblGreeting.setBounds(6, 6, 438, 50);
		frmBankAccountDetails.getContentPane().add(lblGreeting);

		lblYourAccounts = new JLabel("Your Accounts:");
		lblYourAccounts.setBounds(6, 68, 103, 16);
		frmBankAccountDetails.getContentPane().add(lblYourAccounts);

		/**
		 * combox can list all the accounts the user has
		 */
		cboAccounts = new JComboBox();
		cboAccounts.setBounds(121, 64, 289, 27);
		accountOwner.findPersonalAccount();
		for(String ac: accountOwner.getAccountIDList()){
			cboAccounts.addItem(ac);
		}
		cboAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bank b  = new Bank();
				for(Account account : b.getAccountList()){
					if(account.getAccountID().equalsIgnoreCase((String) cboAccounts
							.getSelectedItem())){
						lblAccountDetails.setText("<html>Account Type: " + 
								account.getType() + "<br>Balance: $"+ account.getBalance()+ "<br>Interest Rate: " + 
								account.getInterestRate()*100+"%"+ "<br>Penalty: $"+ account.getPenalty()+"</html>");
					}
				}
			}
		});
		frmBankAccountDetails.getContentPane().add(cboAccounts);

		lblAccountDetails = new JLabel();
		lblAccountDetails.setBounds(6, 109, 216, 109);
		frmBankAccountDetails.getContentPane().add(lblAccountDetails);

		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(234, 116, 61, 16);
		frmBankAccountDetails.getContentPane().add(lblAmount);

		txtAmount = new JTextField();
		txtAmount.setBounds(307, 110, 103, 28);
		frmBankAccountDetails.getContentPane().add(txtAmount);
		txtAmount.setColumns(10);

		btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(230, 159, 81, 29);
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginUI.isInteger(txtAmount.getText())){
					Bank b  = new Bank();
					for(Account account : b.getAccountList()){
						double amount = Double.parseDouble(txtAmount.getText());
						if(account.getAccountID().equalsIgnoreCase((String) cboAccounts
								.getSelectedItem())){
							account.setBalance(account.getBalance() + amount);
							lblAccountDetails.setText("<html>Account Type: " + 
									account.getType() + "<br>Balance: $"+ account.getBalance()+ "<br>Interest Rate: " + 
									account.getInterestRate()*100+"%"+ "<br>Penalty: $"+ account.getPenalty()+"</html>");
							Transaction t = new Transaction(account.getAccountID(), account.getType(),amount, account.getBalance());
						}
					}
				}
			}
		});
		frmBankAccountDetails.getContentPane().add(btnDeposit);

		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(323, 159, 87, 29);
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginUI.isInteger(txtAmount.getText())){
					Bank b  = new Bank();
					for(Account account : b.getAccountList()){
						double amount = Double.parseDouble(txtAmount.getText());
						if(account.getAccountID().equalsIgnoreCase((String) cboAccounts
								.getSelectedItem())){
							if(account.getBalance() - amount>0){
							account.setBalance(account.getBalance() - amount);
							lblAccountDetails.setText("<html>Account Type: " + 
									account.getType() + "<br>Balance: $"+ account.getBalance()+ "<br>Interest Rate: " + 
									account.getInterestRate()*100+"%"+ "<br>Penalty: $"+ account.getPenalty()+"</html>");
							Transaction t = new Transaction(account.getAccountID(), account.getType(),amount, account.getBalance());
							}
							else{
								JOptionPane.showMessageDialog(null, "You can not withdraw at the amount greater than your existing amount.");
							}
							}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please enter a number.");
				}
			}
		});
		frmBankAccountDetails.getContentPane().add(btnWithdraw);

		btnShowTransaction = new JButton("Show Transaction");
		btnShowTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bank b  = new Bank();
				for(Account account : b.getAccountList()){
					if(account.getAccountID().equalsIgnoreCase((String) cboAccounts
							.getSelectedItem())){
						transactionUI tran = new transactionUI(account.getAccountID());
						frmBankAccountDetails.setVisible(false);
					}
				}
			}
		});
		btnShowTransaction.setBounds(167, 221, 144, 29);
		
		frmBankAccountDetails.getContentPane().add(btnShowTransaction);

		btnExit = new JButton("Exit");
		btnExit.setBounds(323, 221, 87, 29);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBankAccountDetails.setVisible(false);
			}
		});
		frmBankAccountDetails.getContentPane().add(btnExit);
	}
}
