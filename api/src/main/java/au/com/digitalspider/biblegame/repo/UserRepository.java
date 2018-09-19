package au.com.digitalspider.biblegame.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@Repository
public interface UserRepository extends NamedCrudRepository<User, Long> {

	User findOneByEmail(@Param("email") String email);

	@Query(value = "select * from biblegame.user u order by random() limit :limit", nativeQuery = true)
	List<User> findTopOrderByRandom(@Param("limit") int limit);
}
