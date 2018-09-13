package au.com.digitalspider.biblegame.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Message;

@Repository
public class MessageRepository implements CrudRepository<Message, Long> {

	private Map<Long, Message> messages = new HashMap<>();

	@Override
	public long count() {
		return messages.size();
	}

	@Override
	public void delete(Long messageId) {
		messages.remove(messageId);
	}

	@Override
	public void delete(Message message) {
		messages.remove(message);
	}

	@Override
	public void delete(Iterable<? extends Message> messageList) {
		for (Message message : messageList) {
			delete(message);
		}
	}

	@Override
	public void deleteAll() {
		messages.clear();
	}

	@Override
	public boolean exists(Long messageId) {
		return messages.containsKey(messageId);
	}

	@Override
	public Iterable<Message> findAll() {
		return messages.values();
	}

	@Override
	public Iterable<Message> findAll(Iterable<Long> messagesToFind) {
		List<Message> foundMessages = new ArrayList<>();
		for (Long messageId : messagesToFind) {
			Message foundMessage = findOne(messageId);
			if (foundMessage != null) {
				foundMessages.add(foundMessage);
			}
		}
		return foundMessages;
	}

	@Override
	public Message findOne(Long messageId) {
		return messages.get(messageId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Message> S save(S message) {
		return (S) messages.put(message.getId(), message);
	}

	@Override
	public <S extends Message> Iterable<S> save(Iterable<S> messages) {
		for (S message : messages) {
			save(message);
		}
		return messages;
	}

}
