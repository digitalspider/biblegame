package au.com.digitalspider.biblegame.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Message;
import au.com.digitalspider.biblegame.model.User;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

	public List<Message> findByFrom(User user);

	public List<Message> findByTo(User user);

	public List<Message> findByToAndViewedTrue(User user);

	public List<Message> findByToAndViewedFalse(User user);
}
