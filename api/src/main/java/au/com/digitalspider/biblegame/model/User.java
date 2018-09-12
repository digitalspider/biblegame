package au.com.digitalspider.biblegame.model;

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

	private int level;
	private int stamina;
	private int love;
	private int knowledge;
	private int riches;
	private int character;

	private int slaves;
	private List<Scroll> scrolls;
	private List<Role> roles;

	private Location location;
	private Team team;
	private List<User> friends;
	private List<User> friendRequests;
	private List<Message> inboundMessages;
}
