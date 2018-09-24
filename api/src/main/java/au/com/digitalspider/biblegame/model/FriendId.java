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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friendId == null) ? 0 : friendId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendId other = (FriendId) obj;
		if (friendId == null) {
			if (other.friendId != null)
				return false;
		} else if (!friendId.equals(other.friendId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
