package au.com.digitalspider.biblegame.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
public class Message extends BaseLongNamedEntity<Message> {

	@ManyToOne
	@JoinColumn(name = "from_id")
	private User from;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User to;
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	private String message;
	private boolean read;

	@Override
	public String toString() {
		return super.toString() + "[from=" + from + ", to=" + to + ", team=" + team + ", message=" + message + "]";
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
}
