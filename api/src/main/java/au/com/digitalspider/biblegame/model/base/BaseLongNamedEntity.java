package au.com.digitalspider.biblegame.model.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseLongNamedEntity<T extends LongNamedEntity<?>> implements LongNamedEntity<T> {

	private long id;
	private String name;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T withId(long id) {
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
