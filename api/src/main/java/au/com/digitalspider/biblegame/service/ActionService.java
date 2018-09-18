package au.com.digitalspider.biblegame.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.exception.ActionException.ActionExceptionType;
import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ActionService {
	private static final Logger LOG = Logger.getLogger(ActionService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private StudyService studyService;

	public Action get(String value) {
		return Action.parse(value);
	}

	public ActionResponse doAction(User user, Action action) {
		try {
			String message = "";
			switch (action) {
			case HELP:
				message = Action.getHelpMessage();
				loggingService.log(user, message);
				return new ActionResponse(true, user, message);
			case STUDY:
				return study(user);
			case WORK:
				return work(user);
			case PRAY:
				return pray(user);
			case BEG:
				return beg(user);
			case STEAL:
				return steal(user);
			case GIVE:
				return give(user);
			case READ:
				return read(user);
			case BUY:
				return buy(user);
			case KNOCK:
				return knock(user);
			case CHAT:
				return chat(user);
			case DONATE:
				return donate(user);
			case STATS:
				message = user.getStats();
				loggingService.log(user, message);
				return new ActionResponse(true, user, message);
			default:
				break;
			}
			return new ActionResponse(false, user, "Action not yet implemented = " + action);
		} catch (ActionException e) {
			loggingService.logError(user, e.getMessage());
			return new ActionResponse(false, user, e.getMessage());
		}
	}

	public ActionResponse study(User user) {
		Action action = Action.STUDY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		message += user.getDisplayName() + " " + Action.STUDY.getDescription() + " stamina=" + user.getStamina()
				+ ", knowledge=" + user.getKnowledge();
		user.decreaseStamina();
		user = userService.save(user);
		loggingService.log(user, message);
		return studyService.doStudy(user);
	}

	public ActionResponse work(User user) {
		Action action = Action.WORK;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addRiches();
		user.addRiches(user.getTools()); // Additional riches for having tools
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina() + ", riches="
				+ user.getRiches();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse steal(User user) {
		Action action = Action.STEAL;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.decreaseLove(2);
		user.addRiches();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina() + ", riches="
				+ user.getRiches();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse give(User user) {
		Action action = Action.GIVE;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseRiches();
		user.addLove();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " love=" + user.getLove() + ", riches="
				+ user.getRiches();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse beg(User user) {
		Action action = Action.BEG;
		String message = handleUserLocation(user, action, true);
		user.addRiches();
		message += user.getDisplayName() + " " + action.getDescription() + "\n";
		// try {
		// int waitTime = (int) (Math.random() * 5); // between 0 and 5 seconds
		// Thread.sleep(waitTime * 1000);
		// if (waitTime <= 2) {
		// user.decreaseCharacter(1);
		// }
		// } catch (InterruptedException e) {
		// LOG.error(e, e);
		// }
		userService.save(user);
		message += "After much begging " + user.getDisplayName() + " recieves riches. riches=" + user.getRiches();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse pray(User user) {
		Action action = Action.PRAY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addCharacter();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina()
				+ ", character=" + user.getCharacter();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse read(User user) {
		Action action = Action.READ;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse buy(User user) {
		Action action = Action.BUY;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse chat(User user) {
		Action action = Action.CHAT;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse knock(User user) {
		Action action = Action.KNOCK;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse donate(User user) {
		Action action = Action.DONATE;
		// TODO: Implement
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	private void validateStamina(User user, Action action) {
		if (!user.hasStamina()) {
			throw new ActionException(action, ActionExceptionType.TIRED);
		}
	}

	private void validateRiches(User user, Action action) {
		if (!user.hasRiches()) {
			throw new ActionException(action, ActionExceptionType.POOR);
		}
	}

	private String handleUserLocation(User user, Action action) {
		return handleUserLocation(user, action, false);
	}

	private String handleUserLocation(User user, Action action, boolean isBegging) {
		String message = StringUtils.EMPTY;
		Location previousUserLocation = user.getLocation();
		userService.updateLocation(user, action.getLocation(), isBegging);
		if (user.getLocation() != previousUserLocation) {
			message += user.getDisplayName() + " travels to " + action.getLocation() + "\n";
		}
		return message;
	}
}
