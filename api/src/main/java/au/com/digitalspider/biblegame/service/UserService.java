package au.com.digitalspider.biblegame.service;

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
		return user;
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
		user.addStamina(6);
	}

	public void updateLocation(User user, Location location) {
		if (user != null && location != null && user.getLocation() != location) {
			System.out.println(user + " travels to " + location);
			user.setLocation(location);
		}
	}

	private boolean authenticate(User user, String password) {
		if (user != null && encoder.encode(password).equals(user.getPassword())) {
			return true;
		}
		throw new BadCredentialsException(user + " provided invalid credentials");
	}
}
