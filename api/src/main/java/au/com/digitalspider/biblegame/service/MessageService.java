package au.com.digitalspider.biblegame.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Friends;
import au.com.digitalspider.biblegame.model.Message;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.MessageRepository;
import au.com.digitalspider.biblegame.repo.UserRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private FriendService friendService;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private UserRepository userRepository;

	private Map<Long, Map<Integer, User>> userMessageCache = new HashMap<>();

	public List<Message> getMessagesFromUser(User user) {
		return messageRepository.findByFrom(user);
	}

	public List<Message> getMessagesToUser(User user) {
		return messageRepository.findByTo(user);
	}

	public List<Message> getMessagesToUserRead(User user) {
		return messageRepository.findByToAndViewedTrue(user);
	}

	public List<Message> getMessagesToUserUnread(User user) {
		return messageRepository.findByToAndViewedFalse(user);
	}

	public ActionResponse doMessage(User user, String whoAndWhat) {
		String reply = "You have " + user.getRiches() + " riches. How much would you like to give?";
		boolean success = true;
		if (whoAndWhat != null) {
			if (StringUtils.isBlank(whoAndWhat)) {
				success = false;
				reply = "The postal workers are not happy with you!";
			} else if (!whoAndWhat.contains(":")) {
				success = false;
				reply = "Invalid input. No message sent";
			} else {
				String keyString = whoAndWhat.split(":")[0];
				String message = whoAndWhat.split(":")[1];
				User friend = null;
				if (!StringUtils.isNumeric(keyString)) {
					friend = userRepository.findOneByName(keyString);
					friendService.validateFriend(user, friend);
				} else {
					Integer key = Integer.parseInt(keyString);
					friend = userMessageCache.get(user.getId()).get(key);
				}
				if (friend == null) {
					success = false;
					reply = "Invalid input. No message sent";
				} else {
					message.trim();
					if (message.length() < 3) {
						success = false;
						reply = "Message is too short. No message sent";
					} else if (message.length() > 256) {
						success = false;
						reply = "Message is too long. No message sent";
					} else {
						sendMessage(user, friend, "Friend", message);
						reply = "Message sent to " + friend.getDisplayName();
					}
				}
			}
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply);
		}
		if (!user.getFriends().isEmpty()) {
			String message = "Which friend would you like to message? Type [number]:[message]\n";
			int i = 0;
			Map<Integer,User> friendCache = new HashMap<>();
			for (Friends friends : user.getFriendList()) {
				if (friends.isAccepted()) {
					User friend = friends.getFriend();
					message += "" + (++i) + ": " + friend.getDisplayName() + "\n";
					friendCache.put(i,friend);
				}
			}
			userMessageCache.put(user.getId(), friendCache);
			return new ActionResponse(success, user, null, message, "/message/send/");
		}
		String message = user.getDisplayName() + " has no friends to message";
		loggingService.log(user, message);
		return new ActionResponse(false, user, message);
	}

	public void sendMessage(User from, User to, String title, String content) {
		Message message = new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setName(title);
		message.setMessage(content);
		messageRepository.save(message);
	}

	public void readMessage(User user, Long messageId) {
		Message message = messageRepository.findOne(messageId);
		if (message != null && message.getTo().getId() == user.getId()) {
			message.setViewed(true);
			messageRepository.save(message);
		}
	}


	public void deleteMessage(User user, long messageId) {
		Message message = messageRepository.findOne(messageId);
		if (message != null && message.getTo().getId() == user.getId()) {
			messageRepository.delete(message);
		}
	}

	public void readAllMessages(User user) {
		List<Message> messages = getMessagesToUserUnread(user);
		for (Message message : messages) {
			message.setViewed(true);
		}
		messageRepository.save(messages);
	}
}