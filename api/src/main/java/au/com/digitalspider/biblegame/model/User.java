package au.com.digitalspider.biblegame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

/**
 * The "name" will be the username
 */
@Entity
public class User extends BaseLongNamedEntity<User> {
	private String displayName;
	private String email;
	private String password;

	private int level;
	private int stamina;
	private int love;
	private int knowledge;
	private int riches;
	private int character;

	private int slaves;
	private List<Scroll> scrolls = new ArrayList<>();
	private List<Role> roles = new ArrayList<>();

	private Location location = Location.HOME;
	private Team team;
	private List<User> friends = new ArrayList<>();
	private List<User> friendRequests = new ArrayList<>();
	private List<Message> inboundMessages = new ArrayList<>();

	public String getDisplayName() {
		return displayName;
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

	public List<Scroll> getScrolls() {
		return scrolls;
	}

	public void setScrolls(List<Scroll> scrolls) {
		this.scrolls = scrolls;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	public List<Message> getInboundMessages() {
		return inboundMessages;
	}

	public void setInboundMessages(List<Message> inboundMessages) {
		this.inboundMessages = inboundMessages;
	}
}
