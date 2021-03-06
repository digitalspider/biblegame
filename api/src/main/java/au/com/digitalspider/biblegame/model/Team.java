package au.com.digitalspider.biblegame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
@Table(name = "team")
public class Team extends BaseLongNamedEntity<Team> {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	private String tag;
	@JsonIgnore
	private boolean enabled = true;
	@JsonIgnore
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<>();

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return super.toString() + "[owner=" + owner + ", tag=" + tag + "]";
	}
}
