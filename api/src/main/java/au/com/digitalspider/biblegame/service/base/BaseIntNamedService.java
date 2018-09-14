package au.com.digitalspider.biblegame.service.base;

import javax.persistence.MappedSuperclass;

import au.com.digitalspider.biblegame.model.base.IntNamedEntity;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@MappedSuperclass
public abstract class BaseIntNamedService<T extends IntNamedEntity<?>> {

	private Class<T> classType;

	public BaseIntNamedService(Class<T> classType) {
		this.classType = classType;
	}

	public Class<T> getEntityClass() {
		return classType;
	}

	public abstract NamedCrudRepository<T, Integer> getRepository();

	public T get(int id) throws Exception {
		return getRepository().findOne(id);
	}

	public T getNotNull(int id) throws Exception {
		T result = get(id);
		if (result == null) {
			throw new Exception("Could not find " + getEntityClass().getSimpleName() + " with id=" + id);
		}
		return result;
	}

	public T getByName(String name) throws Exception {
		return getRepository().findOneByName(name);
	}

	public T save(T valueToSave) throws Exception {
		T dbValue = get(valueToSave.getId());
		valueToSave = mergeForSave(dbValue, valueToSave);
		return getRepository().save(valueToSave);
	}

	public T mergeForSave(T dbValue, T valueToSave) {
		dbValue.setName(valueToSave.getName());
		return valueToSave;
	}
}
