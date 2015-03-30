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

public class ActorManager {
	
	private static ActorManager actorinstance = null;
	
	ActorManager() {
	}
	
	public static ActorManager getInstance() {
		if (actorinstance == null) {
			actorinstance = new ActorManager();
		}
		return actorinstance;
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
	
	public void createActor(Actor newActor) {
		String sql = "insert into actor (id, firstname, lastname, dateofbirth) values (?,?,?,?)";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newActor.getId());
			statement.setString(2, newActor.getFirstName());
			statement.setString(3, newActor.getLastName());
			statement.setString(4, newActor.getDateOfBirth());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Actor> readAllActors() {
		List<Actor> actors = new ArrayList<Actor>();
		String sql = "select * from actor";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String id = results.getString("id");
				String firstname = results.getString("firstname");
				String lastname = results.getString("lastname");
				String dateofbirth = results.getString("dateofbirth");
				Actor actor = new Actor(id, firstname, lastname, dateofbirth);
				actors.add(actor);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return actors;
	}
	
	public Actor readActor(String id) {
		Actor actor = new Actor();
		String sql = "select * from actor where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				String firstname = results.getString("firstname");
				String lastname = results.getString("lastname");
				String dateofbirth = results.getString("dateofbirth");
				actor = new Actor(id, firstname, lastname, dateofbirth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return actor;
	}
	
	public void updateActor(String id, Actor actor) {
		String sql = "update actor set id=?, firstname=?, lastname=?, dateofbirth=? where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, actor.getId());
			statement.setString(2, actor.getFirstName());
			statement.setString(3, actor.getLastName());
			statement.setString(4, actor.getDateOfBirth());
			statement.setString(5, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	void deleteActor(String id) {
		String sql = "delete from actor where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
