package edu.neu.cs5200.hw4;

public class Cast {
	private String characterName;
	private String cast2movie;
	private String cast2actor;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCharacterName() {
		return characterName;
	}
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	public String getCast2movie() {
		return cast2movie;
	}
	public void setCast2movie(String cast2movie) {
		this.cast2movie = cast2movie;
	}
	public String getCast2actor() {
		return cast2actor;
	}
	public void setCast2actor(String cast2actor) {
		this.cast2actor = cast2actor;
	}
	
	public Cast(String characterName, String cast2movie, String cast2actor,
				String id) {
		super();
		this.characterName = characterName;
		this.cast2movie = cast2movie;
		this.cast2actor = cast2actor;
		this.id = id;
	}
	
	public Cast() {
		super();
	}	
}
