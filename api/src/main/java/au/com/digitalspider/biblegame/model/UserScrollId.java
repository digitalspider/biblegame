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
}
