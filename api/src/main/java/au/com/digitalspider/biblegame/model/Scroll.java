package au.com.digitalspider.biblegame.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
@Table(name = "scroll")
public class Scroll extends BaseLongNamedEntity<Scroll> {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
