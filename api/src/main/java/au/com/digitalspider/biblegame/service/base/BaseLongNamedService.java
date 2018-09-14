package au.com.digitalspider.biblegame.service.base;

import javax.persistence.MappedSuperclass;

import au.com.digitalspider.biblegame.model.base.LongNamedEntity;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@MappedSuperclass
public abstract class BaseLongNamedService<T extends LongNamedEntity<?>> {

	private Class<T> classType;

	public BaseLongNamedService(Class<T> classType) {
		this.classType = classType;
	}

	public Class<T> getEntityClass() {
		return classType;
	}

	public abstract NamedCrudRepository<T, Long> getRepository();

	public T get(long id) throws InstantiationException, IllegalAccessException {
		return getRepository().findOne(id);
	}

	public T getNotNull(long id) throws Exception {
		T result = get(id);
		if (result == null) {
			throw new Exception("Could not find " + getEntityClass().getSimpleName() + " with id=" + id);
		}
		return result;
	}

	public T getByName(String name) throws InstantiationException, IllegalAccessException {
		return getRepository().findOneByName(name);
	}

	public T save(T valueToSave) throws Exception {
		T dbValue = get(valueToSave.getId());
		valueToSave = mergeForSave(dbValue, valueToSave);
		return valueToSave;
	}

	public T mergeForSave(T dbValue, T valueToSave) {
		dbValue.setName(valueToSave.getName());
		return valueToSave;
	}
}
