package au.com.digitalspider.biblegame.model.base;

public interface IntNamedEntity<T extends IntNamedEntity<?>> {

	public int getId();
	public void setId(int id);
	public T withId(int id);
	
	public String getName();
	public void setName(String name);
	public T withName(String name);
}
