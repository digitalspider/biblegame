package au.com.digitalspider.biblegame.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.ActionKnock;
import au.com.digitalspider.biblegame.model.User;

@Service
public class KnockService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private MessageService messageService;

	private Map<Long, Map<Integer, User>> knockUserCache = new HashMap<>();
	private static final int MAX_DOORS = 3;

	public ActionResponse getRandomPlayers(User user) {
		List<User> users = userService.findRandomUsers(user, MAX_DOORS);
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

	public ActionResponse doKnock(User user, User player, String actionName, Integer amount) {
		String nextUrl = "/knock/" + player.getName() + "/";
		boolean success = true;
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
						return new ActionResponse(success, user, message);
					}
					message = player.getDisplayName() + " has " + player.getRiches()
							+ " riches. How much would you like to steal?";
					return new ActionResponse(success, user, null, message, nextUrl);
				}
				if (player.getLocks() > 0) {
					// TODO: Implement
				}
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, player.getRiches()); // Cap the maximum
				if (amount != 0) {
					player.decreaseRiches(amount);
					user.addRiches(amount);
					user.decreaseLove(amount);
					message = user.getDisplayName() + " took " + amount + " riches from " + player.getDisplayName();
					messageService.addMessage(sysUser, player, "Robbed", "You were robbed of " + amount + " riches");
				}
				message += leaveMessage;
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			case GIVE:
				nextUrl += actionName + "/";
				if (amount == null) {
					if (user.getRiches() == 0) {
						message = "You have no riches to give. You leave the house of player "
								+ player.getDisplayName();
						return new ActionResponse(success, user, message);
					}
					message = "You have " + user.getRiches() + " riches. How much would you like to give?";
					return new ActionResponse(success, user, null, message, nextUrl);
				}
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, user.getRiches()); // Cap the maximum
				if (amount != 0) {
					user.decreaseRiches(amount);
					user.addLove((int) (0.5 * amount));
					player.addRiches(amount);
					message += user.getDisplayName() + " gives " + amount + " riches to " + player.getDisplayName();
					messageService.addMessage(sysUser, player, "Blessed", "You were given " + amount + " riches");
				}
				message += leaveMessage;
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			case FRIEND:
				userService.addFriendRequest(user, player);
				message = "You leave " + player.getDisplayName() + " a letter asking to be their friend";
				message += leaveMessage;
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			case LEAVE:
			case QUIT:
				message = leaveMessage;
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			}
			message = "Invalid response.\nPlease choose a valid action: give(g), steal(s), friend(f) or leave(l)";
			loggingService.log(user, message);
			return new ActionResponse(false, user, null, message, nextUrl);
		}
		String message = "You enter the house of player " + player.getDisplayName()
				+ "\nChoose an action: give(g), steal(s), friend(f) or leave(l)";
		loggingService.log(user, message);
		return new ActionResponse(success, user, null, message, nextUrl);
	}
}
