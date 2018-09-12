package au.com.digitalspider.biblegame.model.base;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseIntNamedCodedEntity<T extends IntNamedCodedEntity<?>> extends BaseIntNamedEntity<T> implements IntNamedCodedEntity<T> {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	public T withCode(String code) {
		this.code = code;
		return (T) this;
	}

}
