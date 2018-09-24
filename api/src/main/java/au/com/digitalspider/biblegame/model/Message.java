package au.com.digitalspider.biblegame.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import au.com.digitalspider.biblegame.io.SimpleUser;
import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
@Table(name = "message")
public class Message extends BaseLongNamedEntity<Message> {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "from_id")
	private User from;
	@JsonProperty("from")
	@Transient
	private SimpleUser fromUser;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User to;
	@JsonProperty("to")
	@Transient
	private SimpleUser toUser;
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	private String message;
	private boolean read;

	@Override
	public String toString() {
		return super.toString() + "[from=" + getFromUser() + ", to=" + getToUser() + ", team=" + team + ", message="
				+ message + "]";
	}

	public SimpleUser getFromUser() {
		return new SimpleUser(from);
	}

	public SimpleUser getToUser() {
		return new SimpleUser(to);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}
}
