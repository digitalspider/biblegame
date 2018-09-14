package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ActionService {
	private static final Logger LOG = Logger.getLogger(ActionService.class);

	@Autowired
	private UserService userService;

	public Action get(String value) {
		return Action.parse(value);
	}

	public User doAction(User user, Action action) {
		try {
			return new User();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	public void study(User user) {
		if (!user.hasStamina()) {
			System.err.println("You are to tired to study");
		} else {
			userService.updateLocation(user, Action.STUDY.getLocation());
			user.decreaseStamina();
			user.addKnowledge();
		}
	}

	public void work(User user) {
		if (!user.hasStamina()) {
			System.err.println("You are to tired to work");
		} else {
			userService.updateLocation(user, Action.WORK.getLocation());
			user.decreaseStamina();
			user.addRiches();
		}
	}
}
