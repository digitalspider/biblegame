package au.com.digitalspider.biblegame.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * The "name" will be the username
 */
public class User {
	private long id;
	private String name;
	private String displayName;
	private String email;
	private String password;
	private String token;

	private int level;
	private int stamina;
	private int love;
	private int knowledge;
	private int riches;
	private int character;

	private int slaves;
	private int tools;
	private int locks;
	private boolean enabled = true;
	private Date createdAt;
	private Date lastLoginAt;

	private Location location = Location.HOME;

	private List<User> friends;
	private List<User> friendRequests;

	@Override
	public String toString() {
		return super.toString() + "[displayName=" + displayName + ", level=" + level + ", stamina=" + stamina
				+ ", love=" + love + ", knowledge=" + knowledge + ", riches=" + riches + ", character=" + character
				+ ", enabled=" + enabled + "]";
	}

	public String getStats() {
		String stats = "Level: " + level + "\n" + "stamina: " + stamina + "\n" + "knowledge: " + knowledge + "\n"
				+ "love: " + love + "\n" + "riches: " + riches + "\n" + "character: " + character + "\n" + "\n"
				+ "slaves: " + slaves + "\n" + "tools: " + tools + "\n" + "locks: "
				+ locks + "\n";
		return stats;
	}

	public boolean hasRiches() {
		return riches > 0;
	}

	public boolean hasLove() {
		return love > 0;
	}

	public boolean hasStamina() {
		return stamina > 0;
	}

	public boolean hasKnowledge() {
		return knowledge > 0;
	}

	public boolean hasCharacter() {
		return character > 0;
	}

	public void addStamina() {
		++stamina;
	}

	public void addStamina(int amount) {
		stamina += amount;
	}

	public void decreaseStamina() {
		--stamina;
	}

	public void decreaseStamina(int amount) {
		stamina -= amount;
	}

	public void addKnowledge() {
		++knowledge;
	}

	public void addKnowledge(int amount) {
		knowledge += amount;
	}

	public void decreaseKnowledge() {
		--knowledge;
	}

	public void decreaseKnowledge(int amount) {
		knowledge -= amount;
	}

	public void addRiches() {
		++riches;
	}

	public void addRiches(int amount) {
		riches += amount;
	}

	public void decreaseRiches() {
		--riches;
	}

	public void decreaseRiches(int amount) {
		riches -= amount;
	}

	public void stealRiches() {
		riches = 0;
	}

	public void addLove() {
		++love;
	}

	public void addLove(int amount) {
		love += amount;
	}

	public void decreaseLove() {
		--love;
	}

	public void decreaseLove(int amount) {
		love -= amount;
	}

	public void addCharacter() {
		++character;
	}

	public void addCharacter(int amount) {
		character += amount;
	}

	public void decreaseCharacter() {
		--character;
	}

	public void decreaseCharacter(int amount) {
		character -= amount;
	}

	public String getDisplayName() {
		return StringUtils.isNotBlank(displayName) ? displayName : getName();
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		this.knowledge = knowledge;
	}

	public int getRiches() {
		return riches;
	}

	public void setRiches(int riches) {
		this.riches = riches;
	}

	public int getCharacter() {
		return character;
	}

	public void setCharacter(int character) {
		this.character = character;
	}

	public int getSlaves() {
		return slaves;
	}

	public void setSlaves(int slaves) {
		this.slaves = slaves;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getTools() {
		return tools;
	}

	public void setTools(int tools) {
		this.tools = tools;
	}

	public int getLocks() {
		return locks;
	}

	public void setLocks(int locks) {
		this.locks = locks;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Date lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<User> getFriendRequests() {
		return friendRequests;
	}

	public void setFriendRequests(List<User> friendRequests) {
		this.friendRequests = friendRequests;
	}
}
