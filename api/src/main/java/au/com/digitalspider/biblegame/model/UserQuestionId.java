package au.com.digitalspider.biblegame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserQuestionId implements Serializable {
	private static final long serialVersionUID = -1244365794827960573L;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "question_id")
	private Long questionId;

	public UserQuestionId() {
	}

	public UserQuestionId(Long userId, Long questionId) {
		this.userId = userId;
		this.questionId = questionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
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
		UserQuestionId other = (UserQuestionId) obj;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
