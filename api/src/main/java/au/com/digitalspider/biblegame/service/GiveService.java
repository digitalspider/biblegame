package au.com.digitalspider.biblegame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GiveService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	public ActionResponse doGive(User user, Integer amount) {
		String reply = "You have " + user.getRiches() + " riches. How much would you like to give?";
		boolean success = true;
		if (amount != null) {
			if (amount < 0) {
				success = false;
				reply = "The beggars are not happy with you!";
			} else if (amount == 0) {
				reply = "You choose to ignore the beggers and keep all yuor riches!";
			} else if (amount >= user.getRiches()) {
				amount = Math.min(amount, user.getRiches());
				int loveAdded = (int) (amount * 0.75);
				user.addLove(loveAdded);
				user.emptyRiches();
				userService.save(user);
				reply = "You choose to give away all yuor riches! loveAdded=" + loveAdded + ", love=" + user.getLove()
						+ ", riches=" + user.getRiches();
			} else {
				int loveAdded = (int) (amount * 0.5);
				user.addLove(loveAdded);
				user.decreaseRiches(amount);
				userService.save(user);
				reply = "You choose to give away " + amount + " riches! loveAdded=" + loveAdded + ", love="
						+ user.getLove() + ", riches=" + user.getRiches();
			}
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply);
		}
		ActionResponse response = new ActionResponse(success, user, null, reply, "/give/");
		loggingService.log(user, reply);
		return response;
	}
}
