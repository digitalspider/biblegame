package au.com.digitalspider.biblegame.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.UserService;

@Component
public class GiveAction extends ActionBase {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	public GiveAction() {
		setName("Give");
	}

	@Override
	public Action execute(User user, String input) {
		if (StringUtils.isBlank(input)) {
			init(user);
			return this;
		}
		if (!StringUtils.isNumeric(input)) {
			init(user);
			success = false;
			postMessage = "The beggars are not happy with you!";
			return this;
		}
		Integer amount = Integer.parseInt(input);
		success = true;
		if (amount == 0) {
			postMessage = "You choose to ignore the beggers and keep all your riches!";
		} else if (amount >= user.getRiches()) {
			amount = Math.min(amount, user.getRiches());
			int loveAdded = (int) (amount * 0.75);
			user.addLove(loveAdded);
			user.emptyRiches();
			userService.save(user);
			postMessage = "You choose to give away all your riches! loveAdded=" + loveAdded + ", love=" + user.getLove()
					+ ", riches=" + user.getRiches();
		} else {
			int loveAdded = (int) (amount * 0.5);
			user.addLove(loveAdded);
			user.decreaseRiches(amount);
			userService.save(user);
			postMessage = "You choose to give away " + amount + " riches! loveAdded=" + loveAdded + ", love="
					+ user.getLove() + ", riches=" + user.getRiches();
		}
		loggingService.log(user, postMessage);
		completed = true;
		return this;
	}

	@Override
	public String getActionUrl() {
		return "/give/";
	}

	@Override
	public void init(User user) {
		preMessage = "You have " + user.getRiches() + " riches. How much would you like to give?";
		actionUrl = "/give/";
		success = true;
		completed = false;
	}
}
