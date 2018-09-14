package au.com.digitalspider.biblegame.model;

import java.util.ArrayList;
import java.util.List;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

public class Team extends BaseLongNamedEntity<Team> {

	private List<User> users = new ArrayList<>();
}
