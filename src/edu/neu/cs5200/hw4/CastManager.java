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

public class CastManager {
	
	private static CastManager castinstance = null;
	
	CastManager() {
	}
	
	public static CastManager getInstance() {
		if (castinstance == null) {
			castinstance = new CastManager();
		}
		return castinstance;
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
	
	void createCast(Cast newCast) {
		String sql = "insert into cast (charactername, cast2actor, cast2movie, id) values (?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newCast.getCharacterName());
			statement.setString(2, newCast.getCast2actor());
			statement.setString(3, newCast.getCast2movie());
			statement.setString(4, newCast.getId());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	List<Cast> readAllCast() {
		Cast cast;
		List<Cast> c = new ArrayList<Cast>();
		String sql = "select * from cast";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String characterName = results.getString("charactername");
				String cast2actor = results.getString("cast2actor");
				String cast2movie = results.getString("cast2movie");
				String id = results.getString("id");
				cast = new Cast(characterName, cast2movie, cast2actor, id);
				c.add(cast);
				//System.out.println(cast.getCharacterName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return c;
	}
	
	List<Cast> readAllCastForActor(String actorId) {
		Cast cast;
		List<Cast> c = new ArrayList<Cast>();
		String sql = "select * from cast where cast2actor=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, actorId);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String characterName = results.getString("charactername");
				String cast2actor = results.getString("cast2actor");
				String cast2movie = results.getString("cast2movie");
				String id = results.getString("id");
				cast = new Cast(characterName, cast2movie, cast2actor, id);
				c.add(cast);
				//System.out.println(cast.getCharacterName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return c;
	}
	
	List<Cast> readAllCastForMovie(String movieId) {
		Cast cast;
		List<Cast> c = new ArrayList<Cast>();
		String sql = "select * from cast where cast2movie=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, movieId);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String characterName = results.getString("charactername");
				String cast2actor = results.getString("cast2actor");
				String cast2movie = results.getString("cast2movie");
				String id = results.getString("id");
				cast = new Cast(characterName, cast2movie, cast2actor, id);
				c.add(cast);
				//System.out.println(cast.getCharacterName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return c;
	}
	
	Cast readCastForId(String castId) {
		Cast cast = new Cast();
		String sql = "select * from cast where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, castId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				String characterName = results.getString("charactername");
				String cast2actor = results.getString("cast2actor");
				String cast2movie = results.getString("cast2movie");
				String id = results.getString("id");
				cast = new Cast(characterName, cast2movie, cast2actor, id);
				//System.out.println(cast.getCharacterName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return cast;
	}
	
	void updateCast(String castId, Cast newCast) {
		String sql = "update cast set charactername=?, cast2actor=?, cast2movie=?, id=? where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newCast.getCharacterName());
			statement.setString(2, newCast.getCast2actor());
			statement.setString(3, newCast.getCast2movie());
			statement.setString(4, newCast.getId());
			statement.setString(5, castId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	void deleteCast(String castId) {
		String sql = "delete from cast where id = ?";
		Connection connection = getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, castId);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
