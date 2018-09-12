package au.com.digitalspider.biblegame.model.base;

public interface IntNamedCodedEntity<T extends IntNamedCodedEntity<?>> extends IntNamedEntity<T> {

	public String getCode();
	public void setCode(String code);
	public T withCode(String code);
}
