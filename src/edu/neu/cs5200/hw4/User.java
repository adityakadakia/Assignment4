package edu.neu.cs5200.hw4;

public class User {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String dateOfBirth;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String fistName) {
		this.firstName = fistName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public User(String username, String password, String fistName,
				String lastName, String email, String dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = fistName;
		this.lastName = lastName;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
	}
	public User() {
		super();
	}	
}
