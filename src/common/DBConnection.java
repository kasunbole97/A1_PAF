package common;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static Connection con = null;

	public static  Connection getConnection(){

		if(con==null){
			try
			{
				Class.forName("com.mysql.jdbc.Driver");

				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://LOCALHOST:3308/gadgetbadget", "root", "");
			}
			catch (Exception e)
			{e.printStackTrace();}
		}

		return con;
	}
}
