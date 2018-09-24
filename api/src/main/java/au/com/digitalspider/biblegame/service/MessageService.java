package au.com.digitalspider.biblegame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Message;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private LoggingService loggingService;

	public List<Message> getMessagesFromUser(User user) {
		return messageRepository.findByFrom(user);
	}

	public List<Message> getMessagesToUser(User user) {
		return messageRepository.findByTo(user);
	}

	public List<Message> getMessagesToUserRead(User user) {
		return messageRepository.findByToAndReadTrue(user);
	}

	public List<Message> getMessagesToUserUnread(User user) {
		return messageRepository.findByToAndReadFalse(user);
	}

	public void addMessage(User from, User to, String title, String content) {
		Message message = new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setName(title);
		message.setMessage(content);
		messageRepository.save(message);
	}

	public void readMessage(Message message) {
		message.setRead(true);
		messageRepository.save(message);
	}
}