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
@Table(name = "friends")
public class Friends {

	@EmbeddedId
	private FriendId id;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JsonIgnore
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("friendId")
	@JsonIgnore
	private User friend;

	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "accepted_at")
	private Date acceptedAt;

	public Friends() {
	}

	public Friends(User user, User friend) {
		this.user = user;
		this.friend = friend;
		this.id = new FriendId(user.getId(), friend.getId());
	}

	@Override
	public String toString() {
		return "Friends [id=" + id + ", user=" + user + ", friend=" + friend + ", acceptedAt=" + acceptedAt + "]";
	}

	public boolean isAccepted() {
		return acceptedAt != null;
	}

	public FriendId getId() {
		return id;
	}

	public void setId(FriendId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getAcceptedAt() {
		return acceptedAt;
	}

	public void setAcceptedAt(Date acceptedAt) {
		this.acceptedAt = acceptedAt;
	}
}
