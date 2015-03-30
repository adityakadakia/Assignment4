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

public class MovieManager {
	
	private static MovieManager movieinstance = null;
	
	MovieManager() {
	}
	
	public static MovieManager getInstance() {
		if (movieinstance == null) {
			movieinstance = new MovieManager();
		}
		return movieinstance;
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
	
	public void createMovie(Movie newMovie) {
		String sql = "insert into movie (id, title, poster, releasedate) values (?,?,?,?)";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newMovie.getId());
			statement.setString(2, newMovie.getTitle());
			statement.setString(3, newMovie.getPosterImage());
			statement.setString(4, newMovie.getReleaseDate());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	public List<Movie> readAllMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		String sql = "select * from movie";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String id = results.getString("id");
				String title = results.getString("title");
				String posterimage = results.getString("poster");
				String releasedate = results.getString("releasedate");
				Movie movie = new Movie(id, title, posterimage, releasedate);
				movies.add(movie);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return movies;
	}
	
	public Movie readMovie(String id) {
		Movie movie = new Movie();
		String sql = "select * from movie where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				String title = results.getString("title");
				String poster = results.getString("poster");
				String releasedate = results.getString("releasedate");
				movie = new Movie(id, title, poster, releasedate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return movie;
	}
	
	public void updateMovie(String id, Movie movie) {
		String sql = "update movie set id=?, title=?, poster=?, releasedate=? where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, movie.getId());
			statement.setString(2, movie.getTitle());
			statement.setString(3, movie.getPosterImage());
			statement.setString(4, movie.getReleaseDate());
			statement.setString(5, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	void deleteMovie(String id) {
		String sql = "delete from movie where id = ?";
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
