package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySQLUtilities;

/**
 * 
 * @author leileiliu
 *
 */
public class Bank {
	private ArrayList<Account> accountList = new ArrayList<Account>();
	private ArrayList<Customer> customerList = new ArrayList<Customer>();

	public Bank() {
		loadAccounts();
		setAccountOwners();
	}

	public Account findAccount(String accountID) {
		for (Account account : accountList) {
			if (account.getAccountID().equalsIgnoreCase(accountID)) {
				return account;
			}
		}
		return null;
	}


	public Customer findCustomer(String customerID) {
		for (Customer customer : customerList) {
			if (customer.getCustomerID().equalsIgnoreCase(customerID)) {
				return customer;
			} 
		}
		return null;
	}

	/**
	 * this method is to retrieve a list of all accounts and their corresponding customers from the database
	 */
	private void setAccountOwners() {
		String sql = "SELECT * FROM lel74_bank1017.customer_account;";
		DbUtilities db = new MySQLUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				String accountID = rs.getString("fk_accountID");
				String customerID = rs.getString("fk_customerID");
				Account ac =findAccount(accountID);
				if(findCustomer(customerID)==null){
					Customer c = new Customer(customerID);
					customerList.add(c);
					ac.addAccountOwner(c);
				}	
				else{
					ac.addAccountOwner(findCustomer(customerID));
				}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this method is to retrieve a list of all accounts from database
	 */
	private void loadAccounts() {
		String sql = "SELECT accountID FROM lel74_bank1017.account ";
		try {
			DbUtilities db = new MySQLUtilities();
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				String accountID = rs.getString("accountID");
				Account ac = new Account(accountID);
				accountList.add(ac);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

}
