package au.com.digitalspider.biblegame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FriendId implements Serializable {
	private static final long serialVersionUID = -1244365794827960573L;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "friend_id")
	private Long friendId;

	public FriendId() {
	}

	public FriendId(Long userId, Long friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
}
