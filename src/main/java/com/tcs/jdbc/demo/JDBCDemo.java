package com.tcs.jdbc.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JDBCDemo {
	private static Logger logger = LoggerFactory.getLogger(JDBCDemo.class);
	public static void main(String[] args) {
		String DB_URL = "jdbc:mysql://localhost/practice";
		String DB_USER = "root";
		String DB_PASSWORD = "Nuvelabs123$";

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();) {
//			create(statement); //create
//			retrieve(statement);
//			update(statement);
//			delete(statement);
			List<String> regions = retrieveWithCondition(statement, "A");
			logger.debug(regions.toString());
			sort(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void sort(Statement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * FROM REGIONS ORDER BY REGION_NAME");
		while (resultSet.next()) {
			logger.debug("id ={} name={}",resultSet.getInt(1) , resultSet.getNString("REGION_NAME"));//paramterized logging 
		}
	}
	private static void retrieve(Statement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * from regions");
		while (resultSet.next()) {
			System.out.println(resultSet.getInt(1));
			System.out.println(resultSet.getNString("REGION_NAME"));
		}
	}

	private static void create(Statement statement) throws SQLException {
		statement.execute("INSERT INTO REGIONS VALUES(7,'Antartica');");
	}

	private static void update(Statement statement) throws SQLException {
		statement.executeUpdate("Update regions set region_id = 7 where region_name = 'Antartica'");
	}

	private static void delete(Statement statement) throws SQLException {
		statement.execute("DELETE FROM REGIONS WHERE REGION_NAME = 'Antartica'");
	}

	private static List<String> retrieveWithCondition(Statement statement, String str) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * FROM REGIONS WHERE REGION_NAME LIKE '"+str+"%'");
	
		List<String> ls = new ArrayList<>();
		while(resultSet.next()) {
			System.out.print(resultSet.getInt(1)+" ");
			System.out.println(resultSet.getNString("REGION_NAME"));
			ls.add(resultSet.getNString("REGION_NAME"));
		}
		System.out.println("");
		return ls;
	}
}