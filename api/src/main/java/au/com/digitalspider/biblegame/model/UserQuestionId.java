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
}
