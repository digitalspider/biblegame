package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class UserService extends BaseLongNamedService<User> {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	@Autowired
	private ActionService actionService;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public UserService() {
		super(User.class);
	}

	public User createUser(String email, String username, String password) {
		User user = new User().withName(username);
		user.setEmail(email);
		user.setPassword(encoder.encode(password));
		return user;
	}

	public User login(String username, String password) {
		try {
			User user = getByName(username);
			authenticate(user, password);
			user.setLocation(Location.HOME);
			actionService.doAction(user, Action.LOGIN);
			return user;
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	private boolean authenticate(User user, String password) {
		if (encoder.encode(password).equals(user.getPassword())) {
			return true;
		}
		return false;
	}
}
