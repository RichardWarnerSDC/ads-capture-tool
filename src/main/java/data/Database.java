package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * USE JSON TO SAVE TIME
 * @author richard
 *
 */
public class Database {
	
	public static void main(String[] args) {
		Database db = new Database();
//		db.createDB();
		String[] keys = {"id", "name", "country_code"};
		String[] values = {"1", "United Kindom", "GB"};
		
		db.InsertIntoTable("countries", keys, values);
		
//		try {
//			db.connect();
//			db.close();
//		} finally {
//			db.closeConnection();
//		}
	}

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String SQL_VENDOR = "mysql";
	private static final String HOSTNAME = "127.0.0.1:3316";
	private static final String USER = "root";
	private static final String PASSWORD = "rootpwd";
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ps = null;
	
	public Database() {

	}
	
	public void connect() {
		try{
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection("jdbc:" + SQL_VENDOR + "://" + HOSTNAME + "/," + USER + ","  + "rootpwd");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createDatabase() {
		try {
			conn = DriverManager.getConnection("jdbc:" + SQL_VENDOR + "://" + HOSTNAME + "?user=" + USER + "&password=" + PASSWORD); 
			ps = conn.prepareStatement("CREATE DATABASE DAPACT");
			System.out.println("Database returned status: " + ps.executeUpdate());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	public void createCountriesTable() {
		try {
			ps = conn.prepareStatement("CREATE TABLE countries (id INT NOT NULL UNIQUE, english_short_name VARCHAR(128), alpha2_country_code VARCHAR(2))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void InsertIntoTable(String tableName, String[] keys, String[] values) {
		StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO " + tableName.toLowerCase() +
				"(");

		for (int i = 0; i <= keys.length - 1; i++) {
			String[] keyTokens = keys[i].split("[A-Z]");
			for (String token : keyTokens) {
				token = token.toLowerCase();
			}
			StringBuilder dbField = new StringBuilder();
			for (int j = 0; j <= keyTokens.length - 1; j++) {
				if (j == keyTokens.length - 1) {
					dbField.append(keyTokens[j]);
				} else {
					dbField.append(keyTokens[j] + "_");
				}
			}
			if (i == keys.length - 1) {
				statement.append(dbField.toString() + ") "); 
			} else {
				statement.append(dbField.toString() + ", ");
			}
		}
		
		statement.append("VALUES (");
		for (int i = 0; i < values.length; i++) {
			if (i == values.length - 1) {
				statement.append(values[i] + ") ");
			} else {
				statement.append(values[i] + ", ");
			}
		}
	
		prepareStatement(statement.toString());
		execute();
	}
	
	public void insertIntoTableCountries(String name, String countryCode) {
		prepareStatement("INSERT INTO countries (name, country_code) VALUES ('" + name +"','" + countryCode + "')");
		execute();
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public void prepareStatement(String statement) {
		try {
			ps = conn.prepareStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int execute() {
		int result = 0;
		try {
			result =  ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void closeStatement() {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closePreparedStatement() {
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			stmt.close();
			ps.close();
			conn.close();
			System.out.println("Closed connection successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
