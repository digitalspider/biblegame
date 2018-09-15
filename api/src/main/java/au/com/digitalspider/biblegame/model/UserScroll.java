package au.com.digitalspider.biblegame.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_scroll")
public class UserScroll {

	@EmbeddedId
	private UserScrollId id;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JsonIgnore
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("scrollId")
	@JsonIgnore
	private Scroll scroll;

	@Column(name = "completed_at")
	private Date completedAt;
	@Column(name = "created_at")
	private Date createdAt;
	private int attempts;

	public UserScroll() {
	}

	public UserScroll(User user, Scroll scroll) {
		this.user = user;
		this.scroll = scroll;
		this.id = new UserScrollId(user.getId(), scroll.getId());
	}

	@Override
	public String toString() {
		return "UserScrolls [user=" + user.getDisplayName() + ", scroll=" + scroll.getName() + ", completed="
				+ completedAt + ", attempts=" + attempts + "]";
	}

	public boolean isCompleted() {
		return completedAt != null;
	}

	public UserScrollId getId() {
		return id;
	}

	public void setId(UserScrollId id) {
		this.id = id;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Scroll getScroll() {
		return scroll;
	}

	public void setScroll(Scroll scroll) {
		this.scroll = scroll;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
