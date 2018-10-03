package au.com.digitalspider.biblegame.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.MessageService;
import au.com.digitalspider.biblegame.service.UserService;

@Component
// @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype") //
// Don't make this a singleton - adds extra JSON information! :(
public class RootAction extends ActionBase {

	private ActionMain actionMain;

	@Autowired
	private BuyAction buyAction;
	@Autowired
	private GiveAction giveAction;
	@Autowired
	private KnockAction knockAction;
	@Autowired
	private StudyAction studyAction;
	@Autowired
	private HelpAction helpAction;

	@Autowired
	private LoggingService loggingService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	public RootAction() {
		setName("ROOT");
	}

	public RootAction(ActionMain actionMain) {
		this();
		this.actionMain = actionMain;
		this.name = actionMain.name();
		this.actionKey = actionMain.getActionKey();
		this.actionUrl = "/action/" + actionMain.name().toLowerCase();
		this.helpMessage = actionMain.getHelp();
		this.tooltip = actionMain.getHelp();
	}

	public RootAction(ActionMain actionMain, boolean success, String message) {
		this(actionMain);
		this.success = success;
		this.postMessage = message;
	}

	@Override
	public Action execute(User user, String actionName) {
		return execute(user, actionName, null);
	}

	public Action execute(User user, String actionName, String actionInput) {
		try {
			String message = "";
			ActionMain action = ActionMain.parse(actionName);
			switch (action) {
			case HELP:
				return helpAction.execute(user, actionInput);
			case STUDY:
				return studyAction.execute(user, actionInput);
			case WORK:
				return work(user);
			case PRAY:
				return pray(user);
			case BEG:
				return beg(user);
			case STEAL:
				return steal(user);
			case GIVE:
				return giveAction.execute(user, actionInput);
			case READ:
				return read(user);
			case BUY:
				return buyAction.execute(user, actionInput);
			case KNOCK:
				return knockAction.execute(user, actionInput);
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
				postMessage = message;
				return this;
			default:
				break;
			}
			setFailMessage("Action not yet implemented = " + action);
			return this;
		} catch (ActionException e) {
			loggingService.logError(user, e.getMessage());
			setFailMessage(e.getMessage());
			return this;
		}
	}

	@Override
	public List<Action> getActions() {
		return actions;
	}

	@Override
	public void init(User user) {
		preMessage = "What would you like to do?";
		actions.clear();
		for (ActionMain actionItem : ActionMain.values()) {
			actions.add(new RootAction(actionItem));
		}
	}

	public String handleUserLocation(User user, ActionMain action) {
		return handleUserLocation(user, action, false);
	}

	public String handleUserLocation(User user, ActionMain action, boolean isBegging) {
		String message = StringUtils.EMPTY;
		Location previousUserLocation = user.getLocation();
		userService.updateLocation(user, action.getLocation(), isBegging);
		if (user.getLocation() != previousUserLocation) {
			message += user.getDisplayName() + " travels to " + action.getLocation() + "\n";
		}
		return message;
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
		return studyAction;
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
		postMessage = message;
		return this;
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
		postMessage = message;
		return this;
	}

	public Action give(User user) {
		ActionMain action = ActionMain.GIVE;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return giveAction;
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
		postMessage = message;
		return this;
	}

	public Action pray(User user) {
		ActionMain action = ActionMain.PRAY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addFaith();
		userService.save(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina() + ", faith="
				+ user.getFaith();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

	public Action read(User user) {
		ActionMain action = ActionMain.READ;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

	public Action buy(User user) {
		ActionMain action = ActionMain.BUY;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return buyAction.execute(user, null, 1);
	}

	public Action message(User user) {
		ActionMain action = ActionMain.MESSAGE;
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		ActionResponse response = messageService.doMessage(user, null);
		postMessage = response.getMessage();
		success = response.isSuccess();
		return this;
	}

	public Action chat(User user) {
		ActionMain action = ActionMain.CHAT;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
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
		postMessage = message;
		return this;
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
		postMessage = message;
		return this;
	}

	public Action knock(User user) {
		ActionMain action = ActionMain.KNOCK;
		String message = handleUserLocation(user, action);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return knockAction;
	}

	public Action donate(User user) {
		ActionMain action = ActionMain.DONATE;
		// TODO: Implement
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

	public Action logout(User user) {
		ActionMain action = ActionMain.LOGOUT;
		user.setToken(null);
		userService.save(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}
}
