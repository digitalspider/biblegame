package au.com.digitalspider.biblegame.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

}
