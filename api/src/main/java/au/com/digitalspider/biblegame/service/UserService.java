package au.com.digitalspider.biblegame.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.UserRepository;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class UserService extends BaseLongNamedService<User> {

	private static final Logger LOG = Logger.getLogger(UserService.class);

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
	public NamedCrudRepository<User, Long> getRepository() {
		return userRepository;
	}

	public User createUser(String email, String username, String password) {
		User user = new User().withName(username);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		initUser(user);
		userRepository.save(user);
		return userRepository.findOne(user.getId()); // TODO: Remove this line once DB is fixed
	}

	public User login(String username, String password) {
		try {
			User user = getByName(username);
			if (user == null) {
				System.err.println(username + " provided invalid credentials");
				return null;
			}
			authenticate(user, password);
			initUser(user);
			return user;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public void initUser(User user) {
		user.setLocation(Location.HOME);
		addLoginStamina(user);
	}

	public void addLoginStamina(User user) {
		// increase stamina every 1 hour
		if (user.getLastLoginAt() == null || new Date().getTime() - user.getLastLoginAt().getTime() >= 3600000) {
			user.addStamina(6);
		}
	}

	public boolean updateLocation(User user, Location location) {
		return updateLocation(user, location, false);
	}

	/**
	 * Return false if user needs to travel but doesn't have enough riches to do so.
	 */
	public boolean updateLocation(User user, Location location, boolean isBegging) {
		if (user != null && location != null && user.getLocation() != location) {
			if (isBegging || user.hasRiches()) {
				System.out.println(user.getDisplayName() + " travels to " + location);
				user.setLocation(location);
			} else {
				System.out.println(user.getDisplayName() + " is too poor to travel. You need to ask/beg for some riches.");
				return false;
			}
		}
		return true;
	}

	private boolean authenticate(User user, String password) {
		if (user != null && encoder.matches(password, user.getPassword())) {
			return true;
		}
		throw new BadCredentialsException(user.getDisplayName() + " provided invalid credentials");
	}
}
