package au.com.digitalspider.biblegame.model;

import java.util.List;

import javax.persistence.Entity;

import au.com.digitalspider.biblegame.model.base.BaseStringNamedEntity;

@Entity
public class Role extends BaseStringNamedEntity<Role> {

	private List<User> users;

}
