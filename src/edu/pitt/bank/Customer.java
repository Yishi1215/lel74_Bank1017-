package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySQLUtilities;
/**
 * 
 * @author leileiliu
 *
 */
public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String loginName;
	private String pin;
	private ArrayList<String> accountIDList = new ArrayList<String>();

	public Customer(String customerID) {
		String sql= "SELECT*FROM lel74_bank1017.customer WHERE customerID ='"+ customerID +"';";

		DbUtilities db = new MySQLUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getString("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getString("ssn");

			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Customer(String lastName, String firstName, String ssn,
			String loginName, int pin, String streetAddress, String city,
			String state, int zip) {
		this.customerID = UUID.randomUUID().toString();
		this.lastName = lastName;
		this.firstName = firstName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = pin + "";
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip + "";

		String sql = "INSERT INTO lel74_bank1017.customer ";
		sql += "(customerID, lastName, ssn, loginName, pin, streetAddress, city, state, zip) ";
		sql += " VALUES ";
		sql += "('" + this.customerID + "', ";
		sql += "'" + this.lastName + "', ";
		sql += "'" + this.firstName + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.loginName + "', ";
		sql += "'" + this.pin + "', ";
		sql += "'" + this.streetAddress + "', ";
		sql += "'" + this.city + "', ";
		sql += "'" + this.state + "', ";
		sql += "'" + this.zip + "); ";		
		DbUtilities db = new MySQLUtilities();
		db.executeQuery(sql);
	}

	/**
	 * this method is to find all the accounts of a certain customer
	 */
	public void findPersonalAccount(){	
		String sql = "SELECT fk_accountID FROM lel74_bank1017.customer_account WHERE fk_customerID = '"+ customerID +"';";
		DbUtilities db = new MySQLUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				accountIDList.add(rs.getString("fk_accountID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList<String> getAccountIDList() {
		return accountIDList;
	}


	public String getCustomerID() {
		return customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPin() {
		return pin;
	}

}
