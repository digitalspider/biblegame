package au.com.digitalspider.biblegame.model.base;

public interface StringNamedEntity<T extends StringNamedEntity<?>> {

	public String getId();
	public void setId(String id);
	public T withId(String id);
	
	public String getName();
	public void setName(String name);
	public T withName(String name);

}
