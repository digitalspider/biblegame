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

	@Query(value = "select new java.lang.Long(u.id) from User u where u.enabled = true and u.id not in (:excludeUserIds)")
	List<Long> findValidWithExclude(@Param("excludeUserIds") List<Long> excludeUserIds);

	@Query(value = "select * from biblegame.user u where u.enabled = true and u.id not in (:excludeUserIds) order by random() limit :limit", nativeQuery = true)
	List<User> findTopByEnabledTrueOrderByRandom(@Param("limit") int limit,
			@Param("excludeUserIds") List<Long> excludeUserIds);

	List<User> findByEnabledTrueAndStaminaGreaterThan(int stamina);

	List<User> findByEnabledTrueAndSlavesGreaterThan(int stamina);

	List<User> findTop10ByEnabledTrueOrderByLevelDescKnowledgeDesc();

}
