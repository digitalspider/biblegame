package au.com.digitalspider.biblegame.service.base;

import javax.persistence.MappedSuperclass;

import au.com.digitalspider.biblegame.model.base.IntNamedEntity;

@MappedSuperclass
public class BaseIntNamedService<T extends IntNamedEntity<?>> {

	private Class<T> classType;

	public BaseIntNamedService(Class<T> classType) {
		this.classType = classType;
	}

	public Class<T> getEntityClass() {
		return classType;
	}

	@SuppressWarnings("unchecked")
	public T get(int id) throws Exception {
		return (T) classType.newInstance().withId(id);
	}

	public T getNotNull(int id) throws Exception {
		T result = get(id);
		if (result == null) {
			throw new Exception("Could not find " + getEntityClass().getSimpleName() + " with id=" + id);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public T getByName(String name) throws Exception {
		return (T) classType.newInstance().withName(name);
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
