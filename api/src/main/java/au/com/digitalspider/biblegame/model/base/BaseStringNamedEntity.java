package au.com.digitalspider.biblegame.model.base;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseStringNamedEntity<T extends StringNamedEntity<?>> implements StringNamedEntity<T> {

	@Id
	private String id;
	private String name;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T withId(String id) {
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

	@Override
	@SuppressWarnings("unchecked")
	public T withName(String name) {
		this.name = name;
		return (T) this;
	}

}
