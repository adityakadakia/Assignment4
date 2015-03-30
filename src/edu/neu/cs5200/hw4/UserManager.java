package edu.neu.cs5200.hw4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserManager {
	
	private static UserManager userinstance = null;
	
	UserManager() {
	}
	
	public static UserManager getInstance() {
		if (userinstance == null) {
			userinstance = new UserManager();
		}
		return userinstance;
	}
	
	public Connection getConnection() {
		Connection connection = null;
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/hw4");
			connection = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createUser(User newUser) {
		String sql = "insert into user (username, password, firstname, lastname, email, dateofbirth) values (?,?,?,?,?,?)";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newUser.getUsername());
			statement.setString(2, newUser.getPassword());
			statement.setString(3, newUser.getFirstName());
			statement.setString(4, newUser.getLastName());
			statement.setString(5, newUser.getEmail());
			statement.setString(6, newUser.getDateOfBirth());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<User> readAllUsers() {
		List<User> users = new ArrayList<User>();
		String sql = "select * from user";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String username = results.getString("username");
				String password = results.getString("password");
				String firstName = results.getString("firstname");
				String lastName = results.getString("lastname");
				String email = results.getString("email");
				String dob = results.getString("dateofbirth");
				User user = new User(username, password, firstName, lastName, email, dob);
				users.add(user);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return users;
	}
	
	public User readUser(String username) {
		User user = new User();
		String sql = "select * from user where username = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				String password = results.getString("password");
				String firstname = results.getString("firstname");
				String lastname = results.getString("lastname");
				String email = results.getString("email");
				String dob = results.getString("dateofbirth");
				user = new User(username, password, firstname, lastname, email, dob);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return user;
	}
	
	public void updateUser(String username, User user) {
		String sql = "update user set username=?, password=?, firstname=?, lastname=?, email=?, dateofbirth=? where username=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getDateOfBirth());
			statement.setString(7, username);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	void deleteUser(String username) {
		String sql = "delete from user where username = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
