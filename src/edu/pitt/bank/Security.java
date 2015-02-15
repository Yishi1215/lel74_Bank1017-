package edu.pitt.bank;

import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.MySQLUtilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import edu.pitt.utilities.MySQLUtilities;
/**
 * 
 * @author leileiliu
 *
 */
public class Security {
	private String userID;
	ArrayList<String> userGroups = new ArrayList<String>();

	/**
	 * This method is to retrieve a record from customer table to see whether the user's input is correct 
	 * @param loginName the user's login name in the bank system
	 * @param pin  the user's login name in the bank system
	 * @return
	 */
	public Customer validateLogin(String loginName, int pin) {
		String sql = "SELECT * FROM lel74_bank1017.customer WHERE loginName ='"
				+ loginName + "' AND pin = '" + pin + "';";
		Customer cust = null;
		DbUtilities db = new MySQLUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if (rs.next()) {
				cust = new Customer(rs.getString("customerID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cust;
	}

	/**
	 * return an arrayList which containing a list of all groups the logged in user belongs to.
	 * @param userID
	 * @return
	 */
	public ArrayList<String> listUserGroups(String userID) {
		String sql = "SELECT groupName FROM lel74_bank1017.groups as a "
				+ "JOIN lel74_bank1017.user_permissions as b ON a.groupID = b.groupID WHERE groupOrUserID = '"
				+ userID + "';";
		DbUtilities db = new MySQLUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			if (rs.next()) {
				String group = rs.getString("groupName");
				userGroups.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userGroups;
	}

}
