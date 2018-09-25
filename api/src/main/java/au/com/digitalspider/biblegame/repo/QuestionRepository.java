package au.com.digitalspider.biblegame.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@Repository
public interface QuestionRepository extends NamedCrudRepository<Question, Long> {

	List<Question> findByNameContainingIgnoreCaseOrderBySort(@Param("name") String name);

	List<Question> findByCategoryOrderBySort(@Param("category") String category);

	List<Question> findByReferenceOrderBySort(@Param("reference") String reference);

	List<Question> findByLevelOrderBySort(@Param("level") int level);

	@Query(value = "select new java.lang.Long(q.id) from Question q where q.level <= :level")
	List<Long> findValidIdsByLevelLessThanEqual(@Param("level") int level);

	@Query(value = "select * from biblegame.question q where q.level <= :level order by random() limit :limit", nativeQuery = true)
	List<Question> findTopByLevelLessThanEqualOrderByRandom(@Param("level") int level, @Param("limit") int limit);
}
