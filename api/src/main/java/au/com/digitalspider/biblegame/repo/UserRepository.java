package au.com.digitalspider.biblegame.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.User;

@Repository
public class UserRepository implements CrudRepository<User, Long> {

	private Map<Long, User> users = new HashMap<>();

	@Override
	public long count() {
		return users.size();
	}

	@Override
	public void delete(Long userId) {
		users.remove(userId);
	}

	@Override
	public void delete(User user) {
		users.remove(user);
	}

	@Override
	public void delete(Iterable<? extends User> userList) {
		for (User user : userList) {
			delete(user);
		}
	}

	@Override
	public void deleteAll() {
		users.clear();
	}

	@Override
	public boolean exists(Long userId) {
		return users.containsKey(userId);
	}

	@Override
	public Iterable<User> findAll() {
		return users.values();
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> usersToFind) {
		List<User> foundUsers = new ArrayList<>();
		for (Long userId : usersToFind) {
			User foundUser = findOne(userId);
			if (foundUser != null) {
				foundUsers.add(foundUser);
			}
		}
		return foundUsers;
	}

	@Override
	public User findOne(Long userId) {
		return users.get(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends User> S save(S user) {
		return (S) users.put(user.getId(), user);
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> users) {
		for (S user : users) {
			save(user);
		}
		return users;
	}

}
