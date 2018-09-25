package au.com.digitalspider.biblegame.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.io.SimpleUser;
import au.com.digitalspider.biblegame.model.Friends;
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.Message;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.UserRepository;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class UserService extends BaseLongNamedService<User> implements UserDetailsService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static final int[] levelXpArray = new int[42];
	public static final int[] logArray = new int[200];

	@Autowired
	private MessageService messageService;
	@Autowired
	private HelperService helperService;

	static {
		levelXpArray[0] = 6; // TODO: change to fibonacchi
		levelXpArray[1] = 12;
		for (int i = 2; i < levelXpArray.length - 2; i++) {
			levelXpArray[i] = levelXpArray[i - 1] + levelXpArray[i - 2];
		}
		logArray[0] = 0;
		logArray[1] = 10;
		for (int i = 2; i < logArray.length - 2; i++) {
			logArray[i] = (int) (Math.log10(i) * 100);
		}
	}

	public void validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if (!matcher.find()) {
			throw new RuntimeException("Email is invalid");
		}
		if (getRepository().findOneByEmail(email) != null) {
			throw new RuntimeException("Email is already registered");
		}
	}

	public void validateUsername(String name) {
		if (name == null || name.length() <= 3) {
			throw new RuntimeException("Username is too short");
		}
		if (getByName(name) != null) {
			throw new RuntimeException("Username is already taken");
		}
	}

	public void validatePassword(String password) {
		if (password == null || password.length() <= 3) {
			throw new RuntimeException("Password is too short");
		}
	}

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public UserService() {
		super(User.class);
	}

	@Override
	public UserRepository getRepository() {
		return userRepository;
	}

	@Override
	public User save(User user) {
		calculateLevel(user);
		return super.save(user);
	}

	public void calculateLevel(User user) {
		int xp = user.getXp();
		int level = 0;
		int i = 0;
		while (xp > levelXpArray[i++]) {
			level++;
		}
		user.setLevel(level);
	}

	public void initUser(User user) {
		user.setLocation(Location.HOME);
		addLoginStamina(user);
	}

	public void addLoginStamina(User user) {
		// increase stamina every 1 hour
		if (user.getLastLoginAt() == null || new Date().getTime() - user.getLastLoginAt().getTime() >= 3600000) {
			int faithLevel = calculateLogLevel(user.getFaith());
			user.addStamina(6 + faithLevel);
		}
	}

	public int calculateLogLevel(int attribute) {
		int level = 0;
		int i = 0;
		while (attribute > logArray[i++]) {
			level++;
		}
		return level;
	}

	public void updateLocation(User user, Location location) {
		updateLocation(user, location, false);
	}

	/**
	 * Throw {@link ActionException} if user needs to travel but doesn't have enough
	 * riches to do so.
	 */
	public void updateLocation(User user, Location location, boolean isBegging) {
		if (user != null && location != null && user.getLocation() != location) {
			if (isBegging || user.hasRiches()) {
				System.out.println(user.getDisplayName() + " travels to " + location);
				user.setLocation(location);
			} else {
				String message = user.getDisplayName() + " is too poor to travel. You need to ask/beg for some riches.";
				throw new ActionException(message);
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return getByName(username);
	}

	public User getSessionUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			// LOG.info("principal=" + authentication.getPrincipal());
			// LOG.info("name=" + authentication.getName());
			// LOG.info("details=" + authentication.getDetails());
			// LOG.info("credentials=" + authentication.getCredentials());
			User user = getByName(authentication.getName());
			return user;
		}
		return null;
	}

	@Override
	public User getByName(String name) {
		User user = super.getByName(name);
		if (user != null) {
			List<Message> messages = messageService.getMessagesToUserUnread(user);
			user.setUnreadMessages(messages);
		}
		return user;
	}

	public User getSessionUserNotNull() {
		User user = getSessionUser();
		if (user == null) {
			throw new BadCredentialsException("Unauthenticated");
		}
		return user;
	}

	public Iterable<User> findRandomUsers(User user, int limit) {
		// TODO: Handle user level maybe?
		int level = user.getLevel();
		List<Long> excludeUserIds = Arrays.asList(user.getId());
		List<Long> userIds = getRepository().findValidWithExclude(excludeUserIds);
		Iterable<Long> randomValues = helperService.getRandomFromList(userIds, limit);
		return getRepository().findAll(randomValues);
	}

	public List<User> getUsersWithStamina() {
		return getRepository().findByStaminaGreaterThan(0);
	}

	public void processInactiveUsers() {
		List<User> users = getUsersWithStamina();
		for (User user : users) {
			long timeDiff = (new Date().getTime() - user.getLastLoginAt().getTime()) / 1000;
			if (timeDiff > 3600) {
				if (user.getSlaves() > 20) {
					user.setSlaves(0);
					user.setRiches(0);
					messageService.addMessage(user, user, "Slaves",
							"Your slaves riot! They overpower you and run away with your riches!");
				}
				if (user.getSlaves() > 10) {
					user.setSlaves(user.getSlaves() - (int) (Math.random() * 5));
					messageService.addMessage(user, user, "Slaves", "Some of your slaves ran away");
				}
				if (user.getLocks() > 10) {
					user.setLocks(user.getLocks() - (int) (Math.random() * 5));
					messageService.addMessage(user, user, "Locks", "Some of your locks rusted");
				}
				user.decreaseStamina();
				save(user);
			}
		}
	}

	public void addFriendRequest(User user, User newFriend) {
		for (SimpleUser friend : user.getFriendRequests()) {
			if (friend.getName().equals(newFriend.getName())) {
				throw new IllegalArgumentException(
						"You already requested " + newFriend.getDisplayName() + " to be your friend");
			}
		}
		for (SimpleUser friend : user.getFriends()) {
			if (friend.getName().equals(newFriend.getName())) {
				throw new IllegalArgumentException(newFriend.getDisplayName() + " is already your friend");
			}
		}
		newFriend.getFriendList().add(new Friends(newFriend, user));
		save(newFriend);
		user.getFriendList().add(new Friends(user, newFriend));
		save(user);
		messageService.addMessage(user, newFriend, "Friends", user.getDisplayName() + " would like to be your friend.");
	}

	public void acceptFriend(User user, User friend) {

	}

	public BCryptPasswordEncoder getEncoder() {
		return encoder;
	}
}
