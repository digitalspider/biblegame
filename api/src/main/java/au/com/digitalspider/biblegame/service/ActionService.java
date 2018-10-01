package au.com.digitalspider.biblegame.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.action.BuyAction;
import au.com.digitalspider.biblegame.action.GiveAction;
import au.com.digitalspider.biblegame.action.KnockAction;
import au.com.digitalspider.biblegame.action.RootAction;
import au.com.digitalspider.biblegame.action.StudyAction;
import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.exception.ActionException.ActionExceptionType;
import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.ActionMain;
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
	private StudyAction studyAction;
	@Autowired
	private GiveAction giveAction;
	@Autowired
	private BuyAction buyAction;
	@Autowired
	private KnockAction knockAction;
	@Autowired
	private RootAction rootAction;
	@Autowired
	private MessageService messageService;

	public ActionMain get(String value) {
		return ActionMain.parse(value);
	}

	public ActionResponse doAction(User user, String actionInput) {
		try {
			String message = "";
			ActionMain action = ActionMain.parse(actionInput);
			switch (action) {
			case HELP:
				message = ActionMain.getHelpMessage();
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
			case MESSAGE:
				return message(user);
			case CHAT:
				return chat(user);
			case FREE:
				return free(user);
			case LEADERBOARD:
				return leaderboard(user);
			case DONATE:
				return donate(user);
			case LOGOUT:
				return logout(user);
			case STATS:
				message = userService.getStats(user);
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
		ActionMain action = ActionMain.STUDY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		message += user.getDisplayName() + " " + ActionMain.STUDY.getDescription() + " stamina=" + user.getStamina()
				+ ", knowledge=" + user.getKnowledge();
		user.decreaseStamina();
		user = userService.save(user);
		loggingService.log(user, message);
		return studyAction.execute(user, "");
	}

	public ActionResponse work(User user) {
		ActionMain action = ActionMain.WORK;
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
		ActionMain action = ActionMain.STEAL;
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
		ActionMain action = ActionMain.GIVE;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return giveAction.doGive(user, null);
	}

	public ActionResponse beg(User user) {
		ActionMain action = ActionMain.BEG;
		validateStamina(user, action);
		String message = handleUserLocation(user, action, true);
		user.addRiches();
		message += user.getDisplayName() + " " + action.getDescription() + "\n";
		// try {
		// int waitTime = (int) (Math.random() * 5); // between 0 and 5 seconds
		// Thread.sleep(waitTime * 1000);
		// if (waitTime <= 2) {
		// user.decreaseFaith(1);
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
		ActionMain action = ActionMain.PRAY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addFaith();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina()
				+ ", faith=" + user.getFaith();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse read(User user) {
		ActionMain action = ActionMain.READ;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse buy(User user) {
		ActionMain action = ActionMain.BUY;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return buyAction.doBuy(user, null, 1);
	}

	public ActionResponse message(User user) {
		ActionMain action = ActionMain.MESSAGE;
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return messageService.doMessage(user, null);
	}

	public ActionResponse chat(User user) {
		ActionMain action = ActionMain.CHAT;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse leaderboard(User user) {
		ActionMain action = ActionMain.LEADERBOARD;
		// TODO: Implement
		String message = user.getDisplayName() + " " + action.getDescription() + "\n";
		List<User> topPlayers = userService.getTopPlayers();
		int i = 0;
		for (User player : topPlayers) {
			message += "Rank " + (++i) + ": Level=" + player.getLevel() + ": Player=" + player.getDisplayName()
					+ ": xp=" + player.getXp() + "\n";
		}
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse free(User user) {
		ActionMain action = ActionMain.FREE;
		validateRiches(user, action);
		int amount = 10 * user.getSlaves();
		if (user.getRiches() < amount) {
			throw new RuntimeException("User requires " + amount + " riches to be able to free slaves!");
		}
		user.setSlaves(0);
		user.decreaseRiches(amount);
		userService.save(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse knock(User user) {
		ActionMain action = ActionMain.KNOCK;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		ActionResponse response = knockAction.getRandomPlayers(user);
		response.setMessage(message);
		return response;
	}

	public ActionResponse donate(User user) {
		ActionMain action = ActionMain.DONATE;
		// TODO: Implement
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	public ActionResponse logout(User user) {
		ActionMain action = ActionMain.LOGOUT;
		user.setToken(null);
		userService.save(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new ActionResponse(true, user, message);
	}

	private void validateStamina(User user, ActionMain action) {
		if (!user.hasStamina()) {
			throw new ActionException(action, ActionExceptionType.TIRED);
		}
	}

	private void validateRiches(User user, ActionMain action) {
		if (!user.hasRiches()) {
			throw new ActionException(action, ActionExceptionType.POOR);
		}
	}

	private String handleUserLocation(User user, ActionMain action) {
		return handleUserLocation(user, action, false);
	}

	private String handleUserLocation(User user, ActionMain action, boolean isBegging) {
		String message = StringUtils.EMPTY;
		Location previousUserLocation = user.getLocation();
		userService.updateLocation(user, action.getLocation(), isBegging);
		if (user.getLocation() != previousUserLocation) {
			message += user.getDisplayName() + " travels to " + action.getLocation() + "\n";
		}
		return message;
	}

	public Action getNextAction(User user) {
		Action action = rootAction;
		switch (user.getState()) {
		case FREE:
			action = rootAction;
			break;
		case SHOP:
			action = buyAction;
			break;
		case VISIT:
			action = knockAction;
			break;
		}
		return action;
	}
}
