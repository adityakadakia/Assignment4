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

public class CommentManager {
	
	private static CommentManager commentinstance = null;
	
	CommentManager() {
	}
	
	public static CommentManager getInstance() {
		if (commentinstance == null) {
			commentinstance = new CommentManager();
		}
		return commentinstance;
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
	
	public void createComment(Comment newComment) {
		String sql = "insert into comment (comment, date, comment2user, comment2movie, id) values (?,?,?,?,?)";
		Connection connection = getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newComment.getComment());
			statement.setString(2, newComment.getDate());
			statement.setString(3, newComment.getComment2user());
			statement.setString(4, newComment.getComment2movie());
			statement.setString(5, newComment.getId());
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
	}
	
	public List<Comment> readAllComments() {
		Comment comment;
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "select * from comment";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String id = results.getString("id");
				String com = results.getString("comment");
				String date = results.getString("date");
				String comment2movie = results.getString("comment2movie");
				String comment2user = results.getString("comment2user");
				comment = new Comment(com, date, id, comment2movie, comment2user);
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return comments;
	}
	
	List<Comment> readAllCommentsForUsername(String username) {
		Comment comment;
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "select * from comment where comment2user = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String id = results.getString("id");
				String com = results.getString("comment");
				String date = results.getString("date");
				String comment2movie = results.getString("comment2movie");
				String comment2user = results.getString("comment2user");
				comment = new Comment(com, date, id, comment2movie, comment2user);
				comments.add(comment);
				//System.out.println(comment.getComment());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return comments;
	}
	
	List<Comment> readAllCommentsForMovie(String movieId) {
		Comment comment;
		List<Comment> comments = new ArrayList<Comment>();
		String sql = "select * from comment where comment2movie = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, movieId);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String id = results.getString("id");
				String com = results.getString("comment");
				String date = results.getString("date");
				String comment2movie = results.getString("comment2movie");
				String comment2user = results.getString("comment2user");
				comment = new Comment(com, date, id, comment2movie, comment2user);
				comments.add(comment);
				System.out.println(comment.getComment());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return comments;
	}
	
	Comment readCommentForId(String commentId) {
		Comment comment = new Comment();
		String sql = "select * from comment where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, commentId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				String id = results.getString("id");
				String com = results.getString("comment");
				String date = results.getString("date");
				String comment2movie = results.getString("comment2movie");
				String comment2user = results.getString("comment2user");
				comment = new Comment(com, date, id, comment2movie, comment2user);
				//System.out.println(comment.getComment());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return comment;
	}
	
	void updateComment(String commentId, String newComment) {
		String sql = "update comment set comment=? where id=?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, newComment);
			statement.setString(2, commentId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	void deleteComment(String commentId) {
		String sql = "delete from comment where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, commentId);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
