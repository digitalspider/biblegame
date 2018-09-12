package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.base.BaseLongNamedService;

@Service
public class UserService extends BaseLongNamedService<User> {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	public UserService() {
		super(User.class);
	}

	public User login(String username, String password) {
		try {
			return new User();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}
}
