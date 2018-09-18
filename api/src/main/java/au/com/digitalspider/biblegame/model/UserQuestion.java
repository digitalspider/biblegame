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
@Table(name = "user_question")
public class UserQuestion {

	@EmbeddedId
	private UserQuestionId id;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JsonIgnore
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("questionId")
	@JsonIgnore
	private Question question;

	@Column(name = "answered_at")
	private Date answeredAt;
	private int correct;
	private int wrong;

	public UserQuestion() {
	}

	public UserQuestion(User user, Question question) {
		this.user = user;
		this.question = question;
		this.id = new UserQuestionId(user.getId(), question.getId());
	}

	@Override
	public String toString() {
		return "UserQuestion [user=" + user.getDisplayName() + ", question=" + question.getId() + ", answered="
				+ answeredAt + ", correct/wrong=(" + correct + "/" + wrong + ")]";
	}

	public UserQuestionId getId() {
		return id;
	}

	public void setId(UserQuestionId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Date getAnsweredAt() {
		return answeredAt;
	}

	public void setAnsweredAt(Date answeredAt) {
		this.answeredAt = answeredAt;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getWrong() {
		return wrong;
	}

	public void setWrong(int wrong) {
		this.wrong = wrong;
	}

}
