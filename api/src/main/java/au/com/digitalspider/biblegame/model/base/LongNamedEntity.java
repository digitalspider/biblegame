package au.com.digitalspider.biblegame.model.base;

public interface LongNamedEntity<T extends LongNamedEntity<?>> {

	public long getId();
	public void setId(long id);
	public T withId(long id);
	
	public String getName();
	public void setName(String name);
	public T withName(String name);
}
