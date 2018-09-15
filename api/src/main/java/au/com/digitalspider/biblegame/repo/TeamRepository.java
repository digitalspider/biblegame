package au.com.digitalspider.biblegame.repo;

import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Team;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@Repository
public interface TeamRepository extends NamedCrudRepository<Team, Long> {

}
