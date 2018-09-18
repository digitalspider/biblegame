package au.com.digitalspider.biblegame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;

@Service
public class BuyService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	public ActionResponse doBuy(User user, String item, int amount) {
		String reply = "What would you like to buy?";
		boolean success = true;
		if (amount < 1) {
			success = false;
			reply = "The merchants are not happy with you!";
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply);
		}
		if (item != null) {
			switch (item) {
			case "tools":
				if (user.getTools() > 0) {
					success = false;
					reply = "You already have tools. No need for more!";
				} else {
					reply = "You buy some tools.";
					user.setTools(1);
					user.decreaseRiches(6);
				}
				break;
			case "slave":
				if (user.getSlaves() > 0) {
					success = false;
					reply = "You already have slaves. No need for more!";
				} else {
					reply = "You buy a slave.";
					user.setTools(1);
					user.decreaseRiches(15);
				}
				break;
			case "scroll":
				success = false;
				reply = "Scrolls are currently not on sale!";
				break;
			case "q":
			case "n":
				reply = user.getDisplayName() + " finishes buying things";
				break;
			default:
				success = false;
				reply = "The merchants are not happy with you!";
			}
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply);
		}
		ActionResponse response = new ActionResponse(success, user, null, reply, "/buy/");
		loggingService.log(user, reply);
		return response;
	}
}
