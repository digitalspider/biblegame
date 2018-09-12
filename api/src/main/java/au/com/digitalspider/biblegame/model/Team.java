package au.com.digitalspider.biblegame.model;

import java.util.List;

import javax.persistence.Entity;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
public class Team extends BaseLongNamedEntity<Team> {

	private List<User> users;
}
