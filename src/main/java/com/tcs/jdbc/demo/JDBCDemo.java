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

import  com.tcs.jdbc.demo.JDBCDemo;

public class JDBCDemo {
	private static Logger logger = LoggerFactory.getLogger(JDBCDemo.class);
	public static void main(String[] args) {
		String DB_URL = "jdbc:mysql://localhost/practice";
		String DB_USER = "root";
		String DB_PASSWORD = "Nuvelabs123$";

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement();) {
			// create(statement);//create operation
			//update(statement);
			retrieve(statement);
			// delete(statement);
			List<String> reg = retrieveWithCondittion(statement, "A");
			logger.debug(reg.toString());
			sort(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
	}

	private static void sort(Statement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * FROM REGIONS ORDER BY REGION_NAME");
		while (resultSet.next()) {
			logger.debug(resultSet.getInt(1) + " "+ resultSet.getNString("REGION_NAME"));
		}
	}

	private static List<String> retrieveWithCondittion(Statement statement, String str) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * FROM REGIONS WHERE REGION_NAME LIKE '" + str + "%'");
		List<String> ls = new ArrayList<>();
		while (resultSet.next()) {
			ls.add(resultSet.getNString("REGION_NAME"));
		}
		System.out.println("");
		return ls;
	}

	private static void delete(Statement statement) throws SQLException {
		statement.execute("DELETE FROM REGIONS WHERE REGION_ID=10");
	}

	private static void update(Statement statement) throws SQLException {
		statement.execute("UPDATE REGIONS SET REGION_NAME='India' WHERE REGION_ID=5");
		statement.execute("UPDATE REGIONS SET REGION_NAME='Africa' WHERE REGION_ID=1");
		statement.execute("UPDATE REGIONS SET REGION_NAME='America' WHERE REGION_ID=2");
	}

	private static void retrieve(Statement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT * FROM REGIONS");

		while (resultSet.next()) {
			logger.debug(resultSet.getInt(1) + " "+ resultSet.getNString("REGION_NAME"));
		}
		System.out.println("");
	}

	private static void create(Statement statement) throws SQLException {
		statement.execute("INSERT INTO REGIONS VALUES(5,'andaman')");
	}
}