package edu.pitt.bank;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.ui.LoginUI;
import edu.pitt.utilities.MySQLUtilities;
import edu.pitt.utilities.ErrorLogger;

public class Test {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
