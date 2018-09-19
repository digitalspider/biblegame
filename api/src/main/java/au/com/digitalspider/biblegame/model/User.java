package au.com.digitalspider.biblegame.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

/**
 * The "name" will be the username
 */
@Entity
@Table(name = "user", schema = "biblegame")
public class User extends BaseLongNamedEntity<User> implements UserDetails {
	private static final long serialVersionUID = -7493369545010377163L;

	@Column(name = "display_name")
	private String displayName;
	private String email;
	@JsonIgnore
	private String password;
	private String token;

	private int level;
	private int stamina;
	private int love;
	private int knowledge;
	private int riches;
	private int faith;

	private int slaves;
	private int tools;
	private int locks;
	private int books;

	@JsonIgnore
	private boolean enabled = true;
	@JsonIgnore
	@Column(name = "created_at")
	private Date createdAt;
	@JsonIgnore
	@Column(name = "last_login_at")
	private Date lastLoginAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserScroll> scrolls = new ArrayList<>();
	@Transient
	private List<Role> roles = new ArrayList<>();

	@Transient
	private Location location = Location.HOME;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friends> friends = new ArrayList<>();
	@JsonIgnore
	@Transient
	private List<User> friendRequests = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Message> inboundMessages = new ArrayList<>();

	@Override
	public String toString() {
		return super.toString() + "[displayName=" + displayName + ", level=" + level + ", stamina=" + stamina
				+ ", love=" + love + ", knowledge=" + knowledge + ", riches=" + riches + ", faith=" + faith
				+ ", enabled=" + enabled + "]";
	}

	@JsonIgnore
	public String getStats() {
		String stats = "Level: " + level + "\n" + "xp: " + getXp() + "\n" + "stamina: " + stamina + "\n" + "knowledge: "
				+ knowledge + "\n"
				+ "love: " + love + "\n" + "riches: " + riches + "\n" + "faith: " + faith + "\n" + "\n"
				+ "scrolls: " + scrolls + "\n" + "slaves: " + slaves + "\n" + "tools: " + tools + "\n" + "locks: "
				+ locks + "\n" + "books: " + books + "\n";
		return stats;
	}

	public int getXp() {
		return faith + love + knowledge;
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

	public boolean hasFaith() {
		return faith > 0;
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

	public void emptyRiches() {
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

	public void addFaith() {
		++faith;
	}

	public void addFaith(int amount) {
		faith += amount;
	}

	public void decreaseFaith() {
		--faith;
	}

	public void decreaseFaith(int amount) {
		faith -= amount;
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

	@Override
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

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

	public int getSlaves() {
		return slaves;
	}

	public void setSlaves(int slaves) {
		this.slaves = slaves;
	}

	public List<UserScroll> getScrolls() {
		return scrolls;
	}

	public void setScrolls(List<UserScroll> scrolls) {
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

	public List<Friends> getFriends() {
		return friends;
	}

	public void setFriends(List<Friends> friends) {
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

	@Override
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

	@Override
	public String getUsername() {
		return getName();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(); // TODO: Link with roles
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getBooks() {
		return books;
	}

	public void setBooks(int books) {
		this.books = books;
	}

}
