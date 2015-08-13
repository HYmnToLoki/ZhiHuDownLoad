package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Been.User;

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
	public static boolean InsertUserInfo(User user){
		PreparedStatement preparedStatement=null;
		Connection connection=null;
		Statement smStatement=null;
		ResultSet rsResultSet = null;
		try {
			Class.forName(driverString);
			connection=DriverManager.getConnection(conString,"sa","123456");
			preparedStatement=connection.prepareStatement("insert into User_info (hash_id,id,avatar,name,jianjie,jieshao,location,bussiness,gender,employment,position,education,education_extra,followees,followers)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, user.getHash_id());
			preparedStatement.setString(2, user.getId());
			preparedStatement.setString(3, user.getAvatar());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getJianjie());
			preparedStatement.setString(6, user.getJieshao());
			preparedStatement.setString(7, user.getLocation());
			preparedStatement.setString(8, user.getBussiness());
			preparedStatement.setString(9, user.getGender());
			preparedStatement.setString(10, user.getEmployment());
			preparedStatement.setString(11, user.getPosition());
			preparedStatement.setString(12, user.getEducation());
			preparedStatement.setString(13, user.getEducation_extra());
			preparedStatement.setInt(14, user.getFollowees());
			preparedStatement.setInt(15, user.getFollowers());
			
			if(preparedStatement.executeUpdate()>0)
			{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsResultSet==null)
				{
					rsResultSet.close();
				}
				if(smStatement==null)
				{
					smStatement.close();
				}
				if(connection==null)
				{
					connection.close();
				}
				
			} catch (Exception e2) {

			}
		}
		return false;
	}
	public static void main(String[] args) {
		
	}

}
