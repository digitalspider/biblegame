package au.com.digitalspider.biblegame.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.ActionKnock;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.FriendService;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.MessageService;
import au.com.digitalspider.biblegame.service.UserService;

@Component
public class KnockAction extends ActionBase {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FriendService friendService;

	private Map<Long, Map<Integer, User>> knockUserCache = new HashMap<>();
	private static final int MAX_DOORS = 3;

	private ActionKnock actionKnock;
	private Map<Long, User> visitMap = new HashMap<>();


	public KnockAction() {
		setName("Knock");
	}

	public KnockAction(ActionKnock actionKnock) {
		this();
		this.actionKnock = actionKnock;
	}

	public KnockAction(User user, User player) {
		this();
		this.visitMap.put(user.getId(), player);
	}

	public KnockAction(User user, User player, String message, boolean completed) {
		this(user, player);
		this.setPostMessage(message);
		this.setCompleted(completed);
	}

	@Override
	public Action execute(User user, String input) {
		return execute(user, null, input, 0);
	}

	public ActionResponse getRandomPlayers(User user) {
		Iterable<User> users = userService.findRandomUsers(user, MAX_DOORS);
		String message = "Choose which door to knock on:\n";
		Map<Integer, User> doorPlayerMap = new HashMap<>();
		int i = 0;
		for (User player : users) {
			doorPlayerMap.put(i, player);
			message += (i++) + ": " + player.getName() + " : level=" + player.getLevel() + " knowledge="
					+ player.getKnowledge() + "\n";
		}
		knockUserCache.put(user.getId(), doorPlayerMap);
		return new ActionResponse(true, user, null, message, "/knock/");
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

			if (doorNumber < 0 || doorNumber >= doorPlayerMap.size()) {
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

	public Action execute(User user, User player, String actionName, Integer amount) {
		String nextUrl = "/knock/" + player.getName() + "/";
		success = true;
		String leaveMessage = "\nYou leave the house of " + player.getDisplayName();
		if (actionName != null) {
			ActionKnock action = ActionKnock.parse(actionName);
			String message = "";
			User sysUser = user; // TODO: This should be anonymous
			switch (action) {
			case STEAL:
				nextUrl += actionName + "/";
				if (amount == null) {
					if (player.getRiches() == 0) {
						message = player.getDisplayName() + " has no riches to take. You leave the house.";
						return new KnockAction(user, player, message, true);
					}
					message = player.getDisplayName() + " has " + player.getRiches()
							+ " riches. How much would you like to steal?";
					return new KnockAction(user, player, message, false);
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
				message += leaveMessage;
				loggingService.log(user, message);
				KnockAction response = new KnockAction(user, player, message, true);
				response.setSuccess(success);
				return response;
			case GIVE:
				nextUrl += actionName + "/";
				if (amount == null) {
					if (user.getRiches() == 0) {
						message = "You have no riches to give. You leave the house of player "
								+ player.getDisplayName();
						return new KnockAction(user, player, message, true);
					}
					message = "You have " + user.getRiches() + " riches. How much would you like to give?";
					return new KnockAction(user, player, message, false);
				}
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, user.getRiches()); // Cap the maximum
				if (amount != 0) {
					user.decreaseRiches(amount);
					user.addLove((int) (0.5 * amount));
					player.addRiches(amount);
					message += user.getDisplayName() + " gives " + amount + " riches to " + player.getDisplayName();
					messageService.sendMessage(sysUser, player, "Blessed", "You were given " + amount + " riches");
					userService.save(user);
					userService.save(player);
				}
				message += leaveMessage;
				loggingService.log(user, message);
				return new KnockAction(user, player, message, true);
			case FRIEND:
				friendService.addFriendRequest(user, player);
				message = "You leave " + player.getDisplayName() + " a letter asking to be their friend";
				message += leaveMessage;
				loggingService.log(user, message);
				return new KnockAction(user, player, message, true);
			case MESSAGE:
				messageService.sendMessage(user, player, "Private Message", user.getDisplayName() + " says hello.");
				message = "You leave " + player.getDisplayName() + " a message";
				message += leaveMessage;
				loggingService.log(user, message);
				return new KnockAction(user, player, message, true);
			case LEAVE:
			case QUIT:
				message = leaveMessage;
				loggingService.log(user, message);
				return new KnockAction(user, player, message, true);
			}
			message = "Invalid response.\nPlease choose a valid action: give(g), steal(s), friend(f) or leave(l)";
			loggingService.log(user, message);
			KnockAction response = new KnockAction(user, player, message, false);
			response.setActionUrl(nextUrl);
			response.setSuccess(false);
			return response;
		}
		String message = "You enter the house of player " + player.getDisplayName()
				+ "\nChoose an action: give(g), steal(s), friend(f) or leave(l)";
		loggingService.log(user, message);
		KnockAction response = new KnockAction(user, player, message, false);
		response.setActionUrl(nextUrl);
		response.setSuccess(true);
		return response;
	}

	private boolean calculateStealProtection(User player) {
		// 4% protection per lock up to max 80%
		double protection = 0.04 * player.getLocks();
		double random = Math.random();
		boolean safe = random < protection;
		return safe;
	}

	@Override
	public String getActionUrl() {
		return super.getActionUrl() + "/knock/";
	}

	@Override
	public void init(User user) {
		preMessage = "What would you like to do?";
	}

	@Override
	public List<Action> getActions() {
		if (actions.isEmpty()) {
			for (ActionKnock actionItem : ActionKnock.values()) {
				actions.add(new KnockAction(actionItem));
			}
		}
		return actions;
	}
}
