package au.com.digitalspider.biblegame.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;

@Service
public class KnockService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	private Map<User, Map<Integer, User>> knockUserCache = new HashMap<>();
	private static final int MAX_DOORS = 3;
	private static final String STEAL = "steal";
	private static final String GIVE = "give";
	private static final String LEAVE = "leave";

	public ActionResponse getRandomPlayers(User user) {
		List<User> users = userService.getRepository().findTopOrderByRandom(MAX_DOORS);
		String message = "Choose which door to knock on:";
		Map<Integer, User> doorPlayerMap = new HashMap<>();
		int i = 1;
		for (User player : users) {
			doorPlayerMap.put(i, player);
			message += i + ": " + player.getName() + " : level=" + player.getLevel() + " knowledge="
					+ player.getKnowledge();
		}
		knockUserCache.put(user, doorPlayerMap);
		return new ActionResponse(true, user, null, message, "/knock/");
	}

	public User retrievePlayer(User user, String input) {
		String errorMessage = "invalid input. Please select 1,2 or 3";
		if (StringUtils.isBlank(input)) {
			throw new IllegalArgumentException(errorMessage);
		}
		Map<Integer, User> doorPlayerMap = knockUserCache.get(user);
		if (StringUtils.isNumeric(input) && doorPlayerMap != null) {
			int doorNumber = new Integer(input);

			if (doorNumber > doorPlayerMap.size()) {
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

	public ActionResponse doKnock(User user, User player, String action, Integer amount) {
		String nextUrl = "/knock/" + player.getName();
		boolean success = true;
		if (action != null) {
			if (amount == null) {
				String message = player.getDisplayName()+" has "+player.getRiches()+" riches. How much would you like to steal?";
				if ("give".equals(action)) {
					message = "You have " + user.getRiches() + " riches. How much would you like to give?";
				}
				return new ActionResponse(success, user, null, message, nextUrl + "/" + action + "/");
			}
			if (STEAL.equals(action)) {
				if (player.getLocks() > 0) {
					// TODO: Implement
				}
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, player.getRiches()); // Cap the maximum
				player.decreaseRiches(amount);
				user.addRiches(amount);
				user.decreaseLove(amount);
				String message = user.getDisplayName() + " took " + amount + " riches from " + player.getDisplayName();
				message += "\nYou leave the house of " + player.getDisplayName();
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			} else if (GIVE.equals(action)) {
				amount = Math.max(amount, 1); // Cap the minimum
				amount = Math.min(amount, user.getRiches()); // Cap the maximum
				user.decreaseRiches(amount);
				user.addLove((int) (0.5 * amount));
				player.addRiches(amount);
				String message = user.getDisplayName() + " gives " + amount + " riches to " + player.getDisplayName();
				message += "\nYou leave the house of " + player.getDisplayName();
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			} else if (LEAVE.equals(action) || "l".equals(action) || "q".equals(action)) {
				String message = "You leave the house of " + player.getDisplayName();
				loggingService.log(user, message);
				return new ActionResponse(success, user, message);
			}
			String message = "Invalid response.\nPlease choose a valid action: give(g) or steal(s) or leave(q)";
			loggingService.log(user, message);
			return new ActionResponse(false, user, message, null, nextUrl);
		}
		String message = "You enter the house of player " + player.getDisplayName()
				+ "\nChoose an action: give(g) or steal(s) or leave(q)";
		loggingService.log(user, message);
		return new ActionResponse(success, user, message, null, nextUrl + "/");
	}
}
