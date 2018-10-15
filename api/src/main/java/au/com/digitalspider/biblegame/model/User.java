package au.com.digitalspider.biblegame.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.fasterxml.jackson.annotation.JsonProperty;

import au.com.digitalspider.biblegame.io.SimpleUser;
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
	@Column(name = "created_at", updatable = false, insertable = false)
	private Date createdAt;
	@Column(name = "last_login_at")
	private Date lastLoginAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserScroll> scrolls = new ArrayList<>();
	@Transient
	private List<Role> roles = new ArrayList<>();

	@Transient
	private Location location = Location.HOME;
	@Transient
	private State state = State.FREE;
	@Transient
	private boolean chatting;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Friends> friendList;
	@Transient
	private List<SimpleUser> friends = new ArrayList<>();
	@Transient
	private List<SimpleUser> friendRequests = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Message> messages = new ArrayList<>();
	@JsonProperty("messages")
	@Transient
	private List<Message> unreadMessages = new ArrayList<>();

	@Override
	public String toString() {
		return super.toString() + "[displayName=" + displayName + ", level=" + level + ", stamina=" + stamina
				+ ", love=" + love + ", knowledge=" + knowledge + ", riches=" + riches + ", faith=" + faith
				+ ", enabled=" + enabled + "]";
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

	public boolean hasFriend(long friendId) {
		for (SimpleUser friend : getFriends()) {
			if (friend.getId() == friendId) {
				return true;
			}
		}
		return false;
	}

	public void addStamina() {
		++stamina;
	}

	public void addStamina(int amount) {
		stamina += amount;
	}

	public void decreaseStamina() {
		if (stamina > 0) {
			--stamina;
		}
	}

	public void decreaseStamina(int amount) {
		stamina -= amount;
		stamina = Math.max(0, stamina);
	}

	public void addKnowledge() {
		++knowledge;
	}

	public void addKnowledge(int amount) {
		knowledge += amount;
	}

	public void decreaseKnowledge() {
		if (knowledge > 0) {
			--knowledge;
		}
	}

	public void decreaseKnowledge(int amount) {
		knowledge -= amount;
		knowledge = Math.max(0, knowledge);
	}

	public void addRiches() {
		++riches;
	}

	public void addRiches(int amount) {
		riches += amount;
	}

	public void decreaseRiches() {
		if (riches > 0) {
			--riches;
		}
	}

	public void decreaseRiches(int amount) {
		riches -= amount;
		riches = Math.max(0, riches);
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
		if (love > 0) {
			--love;
		}
	}

	public void decreaseLove(int amount) {
		love -= amount;
		love = Math.max(0, love);
	}

	public void addFaith() {
		++faith;
	}

	public void addFaith(int amount) {
		faith += amount;
	}

	public void decreaseFaith() {
		if (faith > 0) {
			--faith;
		}
	}

	public void decreaseFaith(int amount) {
		faith -= amount;
		faith = Math.max(0, faith);
	}

	public void decreaseLocks(int amount) {
		locks -= amount;
		locks = Math.max(0, locks);
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

	public Set<Friends> getFriendList() {
		return friendList;
	}

	public void setFriendList(Set<Friends> friendList) {
		this.friendList = friendList;
	}

	public List<SimpleUser> getFriends() {
		return friends;
	}

	public List<SimpleUser> getFriendRequests() {
		return friendRequests;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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

	public boolean isChatting() {
		return chatting;
	}

	public void setChatting(boolean chatting) {
		this.chatting = chatting;
	}

	public List<Message> getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(List<Message> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
