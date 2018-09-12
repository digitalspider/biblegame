package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ActionService {
	private static final Logger LOG = Logger.getLogger(ActionService.class);

	public Action get(String value) {
		return Action.valueOf(value);
	}

	public User doAction(User user, Action action) {
		try {
			return new User();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}
}
