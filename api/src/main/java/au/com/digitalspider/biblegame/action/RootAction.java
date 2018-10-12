package au.com.digitalspider.biblegame.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.Location;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.MessageService;

public class RootAction extends ActionBase {

	private BuyAction buyAction;
	private GiveAction giveAction;
	private KnockAction knockAction;
	private StudyAction studyAction;
	private HelpAction helpAction;

	private MessageService messageService;

	public RootAction(ActionService actionService) {
		super("", actionService);
		this.messageService = actionService.getMessageService();
		helpAction = new HelpAction(actionService);
		buyAction = new BuyAction(actionService);
		giveAction = new GiveAction(actionService);
		knockAction = new KnockAction(actionService);
		studyAction = new StudyAction(actionService);
	}

	public RootAction(ActionService actionService, ActionMain actionMain) {
		this(actionService);
		this.name = actionMain.name();
		this.actionKey = actionMain.getActionKey();
		this.actionUrl = "/action/" + actionMain.name().toLowerCase();
		this.helpMessage = actionMain.getHelp();
	}

	public RootAction(ActionService actionService, ActionMain actionMain, boolean success, String message) {
		this(actionService, actionMain);
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
				return study(user, actionInput);
			case WORK:
				return work(user);
			case PRAY:
				return pray(user);
			case BEG:
				return beg(user);
			case STEAL:
				return steal(user);
			case GIVE:
				return give(user, actionInput);
			case READ:
				return read(user);
			case BUY:
				return buy(user, actionInput);
			case KNOCK:
				return knock(user, actionInput);
			case FRIEND:
				return friend(user, actionInput);
			case MESSAGE:
				return message(user);
			case CHAT:
				return chat(user);
			case FREE:
				return free(user);
			case LEADERS:
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
		setUser(user);
		preMessage = "What would you like to do?";
		for (ActionMain actionItem : ActionMain.values()) {
			RootAction action = new RootAction(actionService, actionItem);
			if (actionItem.equals(ActionMain.WORK) || actionItem.equals(ActionMain.STUDY)
					|| actionItem.equals(ActionMain.PRAY) || actionItem.equals(ActionMain.BEG)
					|| actionItem.equals(ActionMain.STEAL) || actionItem.equals(ActionMain.READ)) {
				action.setEnabled(user.hasStamina());
				action.setTooltip("This action requires stamina");
			} else if (actionItem.equals(ActionMain.GIVE) || actionItem.equals(ActionMain.BUY)
					|| actionItem.equals(ActionMain.FREE)) {
				action.setEnabled(user.hasRiches());
				action.setTooltip("This action requires riches");
			}
			if (!actionItem.equals(ActionMain.LOGOUT) && !actionItem.equals(ActionMain.HELP)
					&& !actionItem.equals(ActionMain.STATS) && !actionItem.equals(ActionMain.CHAT)
					&& !actionItem.equals(ActionMain.MESSAGE) && !actionItem.equals(ActionMain.FRIEND)) {
				actions.add(action);
			}
		}
	}

	public String handleUserLocation(User user, ActionMain action) {
		return handleUserLocation(user, action, true);
	}

	public String handleUserLocation(User user, ActionMain action, boolean checkRiches) {
		String message = StringUtils.EMPTY;
		Location previousUserLocation = user.getLocation();
		userService.updateLocation(user, action.getLocation(), checkRiches);
		if (user.getLocation() != previousUserLocation) {
			message += user.getDisplayName() + " travels to " + action.getLocation() + "\n";
		}
		return message;
	}

	public Action study(User user, String actionInput) {
		ActionMain action = ActionMain.STUDY;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		message += user.getDisplayName() + " " + ActionMain.STUDY.getDescription() + " stamina=" + user.getStamina()
				+ ", knowledge=" + user.getKnowledge();
		loggingService.log(user, message);
		return studyAction.execute(user, actionInput);
	}

	public Action work(User user) {
		ActionMain action = ActionMain.WORK;
		validateStamina(user, action);
		String message = handleUserLocation(user, action);
		user.decreaseStamina();
		user.addRiches();
		user.addRiches(user.getTools()); // Additional riches for having tools
		saveUser(user);
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
		saveUser(user);
		message += user.getDisplayName() + " " + action.getDescription() + " stamina=" + user.getStamina() + ", riches="
				+ user.getRiches();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

	public Action give(User user, String input) {
		ActionMain action = ActionMain.GIVE;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return giveAction.execute(user, input);
	}

	public Action beg(User user) {
		ActionMain action = ActionMain.BEG;
		validateStamina(user, action);
		String message = handleUserLocation(user, action, false);
		user.addRiches();
		message += user.getDisplayName() + " " + action.getDescription() + "\n";
		saveUser(user);
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
		saveUser(user);
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

	public Action buy(User user, String input) {
		ActionMain action = ActionMain.BUY;
		validateRiches(user, action);
		String message = handleUserLocation(user, action);
		return buyAction.execute(user, input, 1);
	}

	public Action message(User user) {
		ActionMain action = ActionMain.MESSAGE;
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return messageService.doMessage(user, null);
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
		ActionMain action = ActionMain.LEADERS;
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
		saveUser(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

	public Action knock(User user, String input) {
		ActionMain action = ActionMain.KNOCK;
		String message = handleUserLocation(user, action, false);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return knockAction.execute(user, input);
	}

	public Action friend(User user, String input) {
		ActionMain action = ActionMain.FRIEND;
		String message = handleUserLocation(user, action, false);
		// TODO: Implement
		message += user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		return knockAction.execute(user, input, true);
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
		saveUser(user);
		String message = user.getDisplayName() + " " + action.getDescription();
		loggingService.log(user, message);
		postMessage = message;
		return this;
	}

}
