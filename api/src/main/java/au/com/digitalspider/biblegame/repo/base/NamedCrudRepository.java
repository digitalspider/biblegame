package au.com.digitalspider.biblegame.repo.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface NamedCrudRepository<ENTITY, ID extends Serializable> extends CrudRepository<ENTITY, ID> {

	ENTITY findOneByName(@Param("name") String name);

	ENTITY findOneByNameIgnoreCase(@Param("name") String name);

	List<ENTITY> findByNameContainingIgnoreCase(@Param("name") String name);
}
