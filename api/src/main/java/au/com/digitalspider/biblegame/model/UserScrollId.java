package au.com.digitalspider.biblegame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserScrollId implements Serializable {
	private static final long serialVersionUID = -1244365794827960573L;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "scroll_id")
	private Long scrollId;

	public UserScrollId() {
	}

	public UserScrollId(Long userId, Long scrollId) {
		this.userId = userId;
		this.scrollId = scrollId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getScrollId() {
		return scrollId;
	}

	public void setScrollId(Long scrollId) {
		this.scrollId = scrollId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scrollId == null) ? 0 : scrollId.hashCode());
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
		UserScrollId other = (UserScrollId) obj;
		if (scrollId == null) {
			if (other.scrollId != null)
				return false;
		} else if (!scrollId.equals(other.scrollId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
