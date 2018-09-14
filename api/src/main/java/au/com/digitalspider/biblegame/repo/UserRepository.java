package au.com.digitalspider.biblegame.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@Repository
public class UserRepository implements NamedCrudRepository<User, Long> {

	private Map<Long, User> users = new HashMap<>();

	private Map<String, User> usersByName = new HashMap<>();

	@Override
	public long count() {
		return users.size();
	}

	private void loadUsersByName() {
		usersByName.clear();
		for (User user : users.values()) {
			usersByName.put(user.getName(), user);
		}
	}

	@Override
	public void delete(Long userId) {
		users.remove(userId);
		loadUsersByName();
	}

	@Override
	public void delete(User user) {
		users.remove(user);
		loadUsersByName();
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
		loadUsersByName();
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
		User newUser = users.put(user.getId(), user);
		loadUsersByName();
		return (S) newUser;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> users) {
		for (S user : users) {
			save(user);
		}
		return users;
	}

	@Override
	public User findOneByName(String name) {
		return usersByName.get(name);
	}

	@Override
	public List<User> findByNameContainingIgnoreCase(String name) {
		List<User> results = new ArrayList<>();
		for (String username : usersByName.keySet()) {
			if (username.toLowerCase().contains(name.toLowerCase())) {
				results.add(usersByName.get(username));
			}
		}
		return results;
	}

}
