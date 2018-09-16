package au.com.digitalspider.biblegame.service;

import java.util.Date;
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
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.UserRepository;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class UserService extends BaseLongNamedService<User> implements UserDetailsService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

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

	public User createUser(String email, String username, String password) {
		validateEmail(email);
		validateUsername(username);
		validatePassword(password);
		User user = new User().withName(username);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		initUser(user);
		userRepository.save(user);
		return userRepository.findOne(user.getId()); // TODO: Remove this line once DB is fixed
	}

	public User login(String username, String password) {
		User user = getByName(username);
		if (user == null) {
			throw new BadCredentialsException(username + " provided invalid credentials");
		}
		authenticate(user, password);
		initUser(user);
		user.setLastLoginAt(new Date());
		save(user);
		return user;
	}

	public void initUser(User user) {
		user.setLocation(Location.HOME);
		addLoginStamina(user);
		user.setToken(encoder.encode(user.getName() + new Date().getTime()));
	}

	public void addLoginStamina(User user) {
		// increase stamina every 1 hour
		if (user.getLastLoginAt() == null || new Date().getTime() - user.getLastLoginAt().getTime() >= 3600000) {
			user.addStamina(6);
		}
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

	private boolean authenticate(User user, String password) {
		if (user != null && encoder.matches(password, user.getPassword())) {
			return true;
		}
		throw new BadCredentialsException(user.getDisplayName() + " provided invalid credentials");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return getByName(username);
	}

	public User getSessionUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			LOG.info("prin=" + authentication.getPrincipal());
			LOG.info("name=" + authentication.getName());
			LOG.info("details=" + authentication.getDetails());
			LOG.info("cred=" + authentication.getCredentials());
			User user = getByName(authentication.getName());
			return user;
		}
		return null;
	}

	public User getSessionUserNotNull() {
		User user = getSessionUser();
		if (user == null) {
			throw new BadCredentialsException("Unauthenticated");
		}
		return user;
	}

	public BCryptPasswordEncoder getEncoder() {
		return encoder;
	}
}
