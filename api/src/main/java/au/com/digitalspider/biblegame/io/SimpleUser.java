package au.com.digitalspider.biblegame.io;

import au.com.digitalspider.biblegame.model.User;

public class SimpleUser {

	private long id;
	private String name;
	private String displayName;
	private int level;

	public SimpleUser() {

	}

	public SimpleUser(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.displayName = user.getDisplayName();
		this.level = user.getLevel();
	}

	public SimpleUser(long id, String name, String displayName, int level) {
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.level = level;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
