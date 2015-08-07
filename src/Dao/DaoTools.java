package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DaoTools {
	final static String driverString = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	final static String conString = "jdbc:sqlserver://127.0.0.1:1433;databasename=ZhiHuUser";

	public static String getOneUserCatch() throws Exception {
		String id = "";
		Connection connection = null;
		Statement smStatement = null;
		ResultSet rsResultSet = null;
		try {
			Class.forName(driverString);
			connection = DriverManager.getConnection(conString, "sa", "123456");
			smStatement = connection.createStatement();
			rsResultSet = smStatement.executeQuery("select top 1 * from UserCache");
			if (rsResultSet.next()) {
				id=rsResultSet.getString("id");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			try {
				if (rsResultSet == null) {
					rsResultSet.close();
				}
				if (smStatement == null) {
					smStatement.close();
				}
				if (connection == null) {
					connection.close();
				}

			} catch (Exception e2) {

			}
		}
		return id;
	}
	public static void main(String[] args) {
		
	}

}
