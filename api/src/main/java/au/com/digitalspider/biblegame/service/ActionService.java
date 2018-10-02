package au.com.digitalspider.biblegame.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.action.ActionBase;
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
	private MessageService messageService;

	public ActionMain get(String value) {
		return ActionMain.parse(value);
	}

	public Action doAction(User user, String actionInput) {
		try {
			Action action = getNextAction(user);
			return action.execute(user, actionInput);
		} catch (Exception e) {
			ActionBase action = new RootAction();
			action.setFailMessage(e.getMessage());
			return action;
		}
	}

	public Action study(User user) {
		ActionMain action = ActionMain.STUDY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		message += user.getDisplayName() + " " + ActionMain.STUDY.getDescription() + " stamina=" + user.getStamina()
				+ ", knowledge=" + user.getKnowledge();
		user.decreaseStamina();
		user = userService.save(user);
		loggingService.log(user, message);
		return new StudyAction().execute(user, "");
	}

	public Action work(User user) {
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
		return new RootAction(action, true, message);
	}

	public Action steal(User user) {
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
		return new RootAction(action, true, message);
	}

	public Action give(User user) {
		ActionMain action = ActionMain.GIVE;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return new GiveAction().execute(user, null);
	}

	public Action beg(User user) {
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
		return new RootAction(action, true, message);
	}

	public Action pray(User user) {
		ActionMain action = ActionMain.PRAY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addFaith();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina()
				+ ", faith=" + user.getFaith();
		loggingService.log(user, message);
		return new RootAction(action, true, message);
	}

	public Action read(User user) {
		ActionMain action = ActionMain.READ;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new RootAction(action, true, message);
	}

	public Action buy(User user) {
		ActionMain action = ActionMain.BUY;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return new BuyAction().execute(user, null, 1);
	}

	public Action message(User user) {
		ActionMain action = ActionMain.MESSAGE;
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		ActionResponse response = messageService.doMessage(user, null);
		RootAction rootAction = new RootAction();
		rootAction.setPostMessage(response.getMessage());
		rootAction.setSuccess(response.isSuccess());
		return rootAction;
	}

	public Action chat(User user) {
		ActionMain action = ActionMain.CHAT;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new RootAction(action, true, message);
	}

	public Action leaderboard(User user) {
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
		return new RootAction(action, true, message);
	}

	public Action free(User user) {
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
		return new RootAction(action, true, message);
	}

	public Action knock(User user) {
		ActionMain action = ActionMain.KNOCK;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new KnockAction().execute(user, null);
	}

	public Action donate(User user) {
		ActionMain action = ActionMain.DONATE;
		// TODO: Implement
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new RootAction(action, true, message);
	}

	public Action logout(User user) {
		ActionMain action = ActionMain.LOGOUT;
		user.setToken(null);
		userService.save(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return new RootAction(action, true, message);
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
		Action action = new RootAction();
		switch (user.getState()) {
		case FREE:
			action = new RootAction();
			break;
		case SHOP:
			action = new BuyAction();
			break;
		case VISIT:
			action = new KnockAction();
			break;
		}
		return action;
	}

	public Action getNextAction(User user, Action executedAction) {
		if (executedAction == null || executedAction.isCompleted()) {
			return new RootAction();
		}
		return getNextAction(user);
	}
}
