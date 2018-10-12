package au.com.digitalspider.biblegame.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.action.MessageAction;
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

	public Action doMessage(User user, String whoAndWhat) {
		String message = "";
		boolean success = true;
		MessageAction action = new MessageAction(); // TODO: How do I fix this?
		if (whoAndWhat != null) {
			if (StringUtils.isBlank(whoAndWhat)) {
				success = false;
				message = "The postal workers are not happy with you!";
				action.setCompleted(true);
			} else if (!whoAndWhat.contains(":")) {
				success = false;
				message = "Invalid input. No message sent";
				action.setCompleted(true);
			} else {
				String keyString = whoAndWhat.split(":")[0];
				String content = whoAndWhat.split(":")[1];
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
					message = "Invalid input. No message sent";
				} else {
					content.trim();
					if (content.length() < 3) {
						success = false;
						message = "Message is too short. No message sent";
					} else if (content.length() > 256) {
						success = false;
						message = "Message is too long. No message sent";
					} else {
						String regex = "^[a-zA-Z0-9\\., ]+$";
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(content);
						if (!matcher.matches()) {
							success = false;
							message = "Message is invalid, only letters and numbers allowed";
						} else {
							sendMessage(user, friend, "Friend", content);
							message = "Message sent to " + friend.getDisplayName();
							action.setCompleted(true);
						}
					}
				}
			}
			loggingService.log(user, message);
			action.setPostMessage(message);
			action.setUser(user);
			action.setSuccess(success);
			return action;
		}
		if (!user.getFriends().isEmpty()) {
			message = "Which friend would you like to message? Type [number]:[message]\n";
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
			action.setPostMessage(message);
			action.setUser(user);
			action.setSuccess(success);
			return action;
		}
		message = user.getDisplayName() + " has no friends to message";
		loggingService.log(user, message);
		action.setPostMessage(message);
		action.setUser(user);
		action.setSuccess(false);
		action.setCompleted(true);
		return action;
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