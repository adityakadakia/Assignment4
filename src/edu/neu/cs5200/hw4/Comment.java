package edu.neu.cs5200.hw4;

public class Comment {
	private String Comment;
	private String Date;
	private String id;
	private String comment2movie;
	private String comment2user;
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment2movie() {
		return comment2movie;
	}
	public void setComment2movie(String comment2movie) {
		this.comment2movie = comment2movie;
	}
	public String getComment2user() {
		return comment2user;
	}
	public void setComment2user(String comment2user) {
		this.comment2user = comment2user;
	}
	public Comment(String comment, String date, String id,
				   String comment2movie, String comment2user) {
		super();
		Comment = comment;
		Date = date;
		this.id = id;
		this.comment2movie = comment2movie;
		this.comment2user = comment2user;
	}
	public Comment() {
		super();
	}
	
}
