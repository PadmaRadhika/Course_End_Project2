package com.example.zumbaproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.zumbaproject.model.Batch;
import com.example.zumbaproject.model.Participant;

public class Database  {
	private Connection connection;
	private static Database db = new Database();

	private Database() {
		// Private constructor to enforce singleton pattern
	}

	public static Database getInstance() {
		return db;
	}

	
	public Connection getConnection() {
		if (connection == null || isClosed(connection)) {
			// Re-establish the connection if it's null or closed
			connect();
		}
		return connection;
	}

	
	public void closeConnection() {
		if(connection != null && !isClosed(connection)) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	
	public int executeUpdate(PreparedStatement preparedStatement) {
		int result = 0;
		try {
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {			
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		return result;
	}

	
	public ResultSet executeQuery(PreparedStatement preparedStatement) {
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO: handle
			throw new RuntimeException(e);
		}
		return resultSet;
	}

	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "radhika";
			String password = "password1";
			String url = "jdbc:mysql://localhost/funfit";
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isClosed(Connection connection) {
		try {
			return connection == null || connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
}
