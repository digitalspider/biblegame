package au.com.digitalspider.biblegame.model.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseIntNamedEntity<T extends IntNamedEntity<?>> implements IntNamedEntity<T> {

	private int id;
	private String name;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T withId(int id) {
		this.id = id;
		return (T) this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T withName(String name) {
		this.name = name;
		return (T) this;
	}

}
