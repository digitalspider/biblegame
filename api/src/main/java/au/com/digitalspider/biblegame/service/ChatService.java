package au.com.digitalspider.biblegame.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ChatService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	public ActionResponse doChat(User user, String message) {
		String userName = user.getDisplayName();
		String nextUrl = "/chat/" + user.getName() + "/";
		boolean success = true;
		if (!user.isChatting()) {
			String reply = userName + " enters chat";
			ActionResponse response = new ActionResponse(success, user, null, reply, nextUrl);
			loggingService.log(user, reply);
			return response;
		}
		if (StringUtils.isBlank(message)) {
			success = false;
			String reply = "You message is too short!";
			loggingService.log(user, reply);
			return new ActionResponse(success, user, null, reply, nextUrl);
		}
		message = message.trim();
		if (message.equals("q") || message.equals("quit") || message.equals("stop") || message.equals("exit")
				|| message.equals("l") || message.equals("leave")) {
			String reply = userName + " leaves chat";
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply);
		} else if (message.length() < 2) {
			success = false;
			String reply = "Your message is too short!";
			loggingService.log(user, reply);
			return new ActionResponse(success, user, null, reply, nextUrl);
		}
		String reply = userName + ": " + message;
		loggingService.log(user, reply);
		// TODO: Add to chat queue
		return new ActionResponse(success, user, null, reply, nextUrl);
	}
}
