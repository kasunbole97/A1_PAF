package model;
import java.sql.*;
import java.sql.Date;

import common.DBConnection;


public class Payment {
	private Connection con = DBConnection.getConnection();
	public String insertpayment(String paymentcode, String paymentType, String researchercode, String buyerCode, String amount, String createdDate)
	{
		String output = "";
		try
		{
			
			if (con == null)
			{return "Error while connecting to the database for inserting."; }
			// create a prepared statement
			String query = " insert into payment(`paymentID`,`paymentCode`,`paymentType`, `researcherCode`,`buyerCode`,`amount` ,`paymentDate`)" + " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, paymentcode);
			preparedStmt.setString(3, paymentType);
			preparedStmt.setString(4, researchercode);
			preparedStmt.setString(5, buyerCode);
			preparedStmt.setDouble(6, Double.parseDouble(amount));

			java.util.Date date=new java.util.Date();
			
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
	
			preparedStmt.setDate(7,sqlDate);
			
			preparedStmt.execute();
			output = "Inserted successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String readpayments()
	{
		String output = "";
		try
		{
			if (con == null)
			{return "Error while connecting to the database for reading."; }
			// Prepare the html table to be displayed
			output = "<table border='1'><tr>" + 
					"<th>payment Code</th>"+ 
					"<th>payment Type</th>" +
					"<th>researcher Code</th>" +
					"<th>buyer Code</th>" +
					"<th>amount</th>" +
					"<th>date</th>" +
					"<th>Update</th>" + 
					"<th>Remove</th></tr>";

			String query = "select * from payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String paymentCode = rs.getString("paymentCode");
				String paymentType = rs.getString("paymentType");
				String researcherCode = rs.getString("researcherCode");
				String buyerCode = rs.getString("buyerCode");
				String amount = Double.toString(rs.getDouble("amount"));
				String paymentDate = rs.getDate("paymentDate").toString();
				// Add into the html table
				output += "<tr><td>" + paymentCode + "</td>";
				output += "<td>" + paymentType + "</td>";
				output += "<td>" + researcherCode + "</td>";
				output += "<td>" + buyerCode + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + paymentDate + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
								+ "<td><form method='post' action='payments.jsp'>"
								+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
										+ "<input name='paymentID' type='hidden' value='" + paymentID
										+ "'>" + "</form></td></tr>";
			}
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String updatepayment(String ID, String paymentcode, String paymentType, String researchercode, String buyerCode, String amount, String createdDate)

	{
		String output = "";
		try
		{
			if (con == null)
			{return "Error while connecting to the database for updating."; }
			// create a prepared statement
			String query = "UPDATE payments SET paymentCode=?, paymentType=?, researcherCode=?, buyerCode=?, amount=?, paymentDate=? WHERE paymentID=?";
							PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, paymentcode);
			preparedStmt.setString(2, paymentType);
			preparedStmt.setString(3, researchercode);
			preparedStmt.setString(4, buyerCode);
			preparedStmt.setDouble(5, Double.parseDouble(amount));
			Date date = Date.valueOf(createdDate);
			preparedStmt.setDate(6, date);
			preparedStmt.setInt(7, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String deletepayment(String paymentID)
	{
		String output = "";
		try
		{
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			// create a prepared statement
			String query = "delete from payments where paymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentID));
			// execute the statement
			preparedStmt.execute();
			// display output
			output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}