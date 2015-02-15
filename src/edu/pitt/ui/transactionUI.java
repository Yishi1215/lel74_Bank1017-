package edu.pitt.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySQLUtilities;

/**
 * This UI is designed to let the users have a look of their transaction details\\
 * 
 * @author leileiliu
 *
 */
public class transactionUI {

	private JFrame frmBankAccountTransactions;
	private JScrollPane transactionPane;
	private JTable tblTransactions;
	private String accountID;
	private JButton btnExit;


	/**
	 * Create the application.
	 */
	public transactionUI(String ac) {
		accountID = ac;
		initialize();
		frmBankAccountTransactions.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankAccountTransactions = new JFrame();
		frmBankAccountTransactions.setTitle("Bank1017 Account Transactions");
		frmBankAccountTransactions.setBounds(100, 100, 450, 300);
		frmBankAccountTransactions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		transactionPane = new JScrollPane();
		frmBankAccountTransactions.getContentPane().add(transactionPane);
		DbUtilities db = new MySQLUtilities();
		String [] cols = {"Transaction Type", "Data/Time", "Amount"};
		String sql = "SELECT Type, transactionDate, amount FROM transaction WHERE accountID = '"+accountID+"';";
		try{
			DefaultTableModel transactionList = db.getDataTable(sql, cols);
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.GRAY);
			transactionPane.getViewport().add(tblTransactions);		
		}
		catch(SQLException e){
			e.printStackTrace();
		}


	}
}
