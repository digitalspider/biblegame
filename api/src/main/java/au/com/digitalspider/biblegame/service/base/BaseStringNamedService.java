package au.com.digitalspider.biblegame.service.base;

import javax.persistence.MappedSuperclass;

import au.com.digitalspider.biblegame.model.base.StringNamedEntity;
import au.com.digitalspider.biblegame.repo.base.NamedCrudRepository;

@MappedSuperclass
public abstract class BaseStringNamedService<T extends StringNamedEntity<?>> {

	private Class<T> classType;

	public BaseStringNamedService(Class<T> classType) {
		this.classType = classType;
	}

	public Class<T> getEntityClass() {
		return classType;
	}

	public abstract NamedCrudRepository<T, String> getRepository();

	public T get(String id) {
		return getRepository().findOne(id);
	}

	public T getNotNull(String id) throws Exception {
		T result = get(id);
		if (result == null) {
			throw new Exception("Could not find " + getEntityClass().getSimpleName() + " with id=" + id);
		}
		return result;
	}

	public T getByName(String name) {
		return getRepository().findOneByName(name);
	}

	public T save(T valueToSave) {
		T dbValue = get(valueToSave.getId());
		valueToSave = mergeForSave(dbValue, valueToSave);
		return getRepository().save(valueToSave);
	}

	public T mergeForSave(T dbValue, T valueToSave) {
		return valueToSave;
	}
}
