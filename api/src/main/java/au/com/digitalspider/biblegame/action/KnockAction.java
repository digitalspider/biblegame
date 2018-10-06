package au.com.digitalspider.biblegame.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.com.digitalspider.biblegame.io.SimpleUser;
import au.com.digitalspider.biblegame.model.ActionKnock;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.State;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.FriendService;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.MessageService;
import au.com.digitalspider.biblegame.service.UserService;

public class KnockAction extends ActionBase {

	private UserService userService;
	private ActionService actionService;
	private LoggingService loggingService;
	private MessageService messageService;
	private FriendService friendService;

	private static Map<Long, Map<Integer, User>> knockUserCache = new HashMap<>();
	private static final int MAX_DOORS = 3;
	private static Map<Long, User> visitMap = new HashMap<>();
	private static Map<Long, ActionKnock> actionMap = new HashMap<>();


	public KnockAction(ActionService actionService) {
		super(ActionMain.KNOCK.name(), actionService);
		messageService = actionService.getMessageService();
		friendService = actionService.getFriendService();
	}

	public KnockAction(ActionService actionService, ActionKnock actionKnock) {
		this(actionService);
		this.name = actionKnock.name();
		this.actionKey = actionKnock.getActionKey();
		this.actionUrl = "/action/knock/" + actionKnock.name().toLowerCase();
		this.helpMessage = actionKnock.getDescription();
		this.tooltip = actionKnock.getDescription();
	}

	@Override
	public Action execute(User user, String input) {
		return execute(user, input, false);
	}

	public Action execute(User user, String input, boolean friendList) {
		User player = visitMap.get(user.getId());
		if (StringUtils.isNotBlank(input) && player == null) {
			player = retrievePlayer(user, input);
			if (player != null) {
				visitMap.put(user.getId(), player);
				input = null;
			}
		}
		ActionKnock action = actionMap.get(user.getId());
		if (action == null) {
			if (input != null && player != null) {
				action = ActionKnock.parse(input);
			}
		}
		return execute(user, player, action, input, friendList);
	}

	public Action execute(User user, User player, ActionKnock action, String input, boolean friendList) {
		init(user);
		setupActions(user, friendList);
		if (action != null && player != null) {
			String message = "";
			User sysUser = user; // TODO: This should be anonymous
			switch (action) {
			case STEAL:
				Integer amount = null;
				if (StringUtils.isNumeric(input)) {
					amount = Integer.parseInt(input);
					input = null;
				} else {
					success = false;
					postMessage = "Invalid amount try again?";
					return this;
				}
				if (amount == null) {
					if (player.getRiches() == 0) {
						message = player.getDisplayName() + " has no riches to take. You leave the house.";
						postMessage = message;
						completed = true;
						return this;
					}
					actionMap.put(user.getId(), ActionKnock.STEAL);
					message = player.getDisplayName() + " has " + player.getRiches()
							+ " riches. How much would you like to steal?";
					postMessage = message;
					preMessage = message;
					return this;
				}
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, player.getRiches()); // Cap the maximum
				if (amount != 0) {
					boolean safe = calculateStealProtection(player);
					if (safe) {
						player.setLocks(player.getLocks() - 1); // TODO: this should be more random
						success = false;
						message = player.getDisplayName() + " locks prevent you from taking riches";
					} else {
						player.decreaseRiches(amount);
						user.addRiches(amount);
						message = user.getDisplayName() + " took " + amount + " riches from " + player.getDisplayName();
						messageService.sendMessage(sysUser, player, "Robbed",
								"You were robbed of " + amount + " riches");
					}
					user.decreaseLove(amount);
					userService.save(user);
					userService.save(player);
				}
				return leaveHouse(user, player, message);
			case GIVE:
				amount = null;
				if (StringUtils.isNumeric(input)) {
					amount = Integer.parseInt(input);
					input = null;
				} else {
					success = false;
					postMessage = "Invalid amount try again?";
					return this;
				}
				if (amount == null) {
					if (user.getRiches() == 0) {
						message = "You have no riches to give. You leave the house of player "
								+ player.getDisplayName();
						postMessage = message;
						completed = true;
						return this;
					}
					actionMap.put(user.getId(), ActionKnock.GIVE);
					message = "You have " + user.getRiches() + " riches. How much would you like to give?";
					postMessage = message;
					preMessage = message;
					return this;
				}
				if (amount != 0) {
					amount = Math.max(amount, 1); // Cap the minimum
					amount = Math.min(amount, user.getRiches()); // Cap the maximum
					user.decreaseRiches(amount);
					user.addLove((int) (0.5 * amount));
					player.addRiches(amount);
					message += user.getDisplayName() + " gives " + amount + " riches to " + player.getDisplayName();
					messageService.sendMessage(sysUser, player, "Blessed", "You were given " + amount + " riches");
					userService.save(user);
					userService.save(player);
				}
				return leaveHouse(user, player, message);
			case FRIEND:
				friendService.addFriendRequest(user, player);
				message = "You leave " + player.getDisplayName() + " a letter asking to be their friend";
				return leaveHouse(user, player, message);
			case MESSAGE:
				if (input == null) {
					actionMap.put(user.getId(), ActionKnock.MESSAGE);
					message = "Please enter your message?";
					postMessage = message;
					preMessage = message;
					return this;
				} else {
					if (input.length() < 3) {
						success = false;
						postMessage = "Message too short";
						return this;
					}
					messageService.sendMessage(user, player, "Private Message", input);
					message = "You leave " + player.getDisplayName() + " a message";
					return leaveHouse(user, player, message);
				}
			case LEAVE:
			case QUIT:
				return leaveHouse(user, player, "");
			}
			setFailMessage("Invalid response.\nPlease choose a valid action: give(g), steal(s), friend(f) or leave(l)");
			loggingService.log(user, postMessage);
			return this;
		}
		if (player != null) {
			postMessage = "You enter the house of player " + player.getDisplayName()
					+ "\nChoose an action: give(g), steal(s), friend(f) or leave(l)";
			loggingService.log(user, postMessage);
		}
		success = true;
		return this;
	}

	private Map<Integer, User> getRandomPlayers(User user) {
		Iterable<User> users = userService.findRandomUsers(user, MAX_DOORS);
		Map<Integer, User> doorPlayerMap = new HashMap<>();
		int i = 0;
		for (User player : users) {
			doorPlayerMap.put(++i, player);
		}
		knockUserCache.put(user.getId(), doorPlayerMap);
		return doorPlayerMap;
	}

	private Map<Integer, User> getFriendPlayers(User user) {
		Iterable<SimpleUser> users = user.getFriends();
		Map<Integer, User> doorPlayerMap = new HashMap<>();
		int i = 0;
		for (SimpleUser friend : users) {
			doorPlayerMap.put(++i, userService.get(friend.getId()));
		}
		knockUserCache.put(user.getId(), doorPlayerMap);
		return doorPlayerMap;
	}

	public User retrievePlayer(User user, String input) {
		String errorMessage = "Invalid input. Please select the number of the door.";
		System.out.println("retrievePlayer() called. user=" + user + ", input=" + input);
		if (StringUtils.isBlank(input)) {
			throw new IllegalArgumentException(errorMessage);
		}
		Map<Integer, User> doorPlayerMap = knockUserCache.get(user.getId());
		if (StringUtils.isNumeric(input) && doorPlayerMap != null) {
			int doorNumber = new Integer(input);

			if (doorNumber < 0 || doorNumber > doorPlayerMap.size()) {
				throw new IllegalArgumentException(errorMessage);
			}
			User player = doorPlayerMap.get(doorNumber);
			return player;
		}
		User player = userService.getByName(input);
		if (player == null) {
			throw new IllegalArgumentException(errorMessage);
		}
		return player;
	}

	private Action leaveHouse(User user, User player, String message) {
		String leaveMessage = "\nYou leave the house of " + player.getDisplayName();
		message += leaveMessage;
		loggingService.log(user, message);
		postMessage = message;
		completed = true;
		knockUserCache.remove(user.getId());
		visitMap.remove(user.getId());
		actionMap.remove(user.getId());
		return this;
	}

	private boolean calculateStealProtection(User player) {
		// 4% protection per lock up to max 80%
		double protection = 0.04 * player.getLocks();
		double random = Math.random();
		boolean safe = random < protection;
		return safe;
	}

	@Override
	public void init(User user) {
		user.setState(State.VISIT);
		preMessage = "Choose which door to knock on?";
		actions.clear();
		success = true;
	}

	private void setupActions(User user, boolean friendList) {
		User player = visitMap.get(user.getId());
		if (player != null) {
			preMessage = "You are in the house of: " + player.getDisplayName() + ". What would you like to do?";
			for (ActionKnock actionItem : ActionKnock.values()) {
				if (!actionItem.equals(ActionKnock.QUIT) && !actionItem.equals(ActionKnock.HELP)) {
					KnockAction action = new KnockAction(actionService, actionItem);
					if (actionItem.equals(ActionKnock.MESSAGE)) {
						if (user.hasFriend(player.getId())) {
							actions.add(action);
						}
					} else if (actionItem.equals(ActionKnock.FRIEND)) {
						if (!user.hasFriend(player.getId())) {
							actions.add(action);
						}
					} else {
						actions.add(action);
					}
				}
			}
		} else {
			Map<Integer, User> doorPlayerMap = friendList ? getFriendPlayers(user) : getRandomPlayers(user);
			for (Integer doorNumber : doorPlayerMap.keySet()) {
				User doorPlayer = doorPlayerMap.get(doorNumber);
				KnockAction action = new KnockAction(actionService);
				action.setActionUrl(actionUrl + doorNumber);
				action.setActionKey(doorNumber.toString());
				action.setName(doorNumber.toString());
				action.setHelpMessage("Player=" + doorPlayer.getDisplayName() + "\nLevel=" + doorPlayer.getLevel());
				actions.add(action);
			}
		}
	}
}
