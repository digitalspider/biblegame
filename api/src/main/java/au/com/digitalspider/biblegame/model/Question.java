package au.com.digitalspider.biblegame.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

@Entity
@Table(name = "question")
public class Question extends BaseLongNamedEntity<Question> {

	private String answer;
	private String category;
	private int sort = 99;
	private String reference;
	int level;
	int correct;
	int wrong;
	private boolean enabled;
	@Column(name = "created_at", updatable = false, insertable = false)
	private Date createdAt;

	@Override
	public String toString() {
		return super.toString() + "[answer=" + answer + ", category=" + category + ", level=" + level
				+ ", correct/wrong=(" + correct + "/" + wrong + "), reference=" + reference + "]";
	}

	public String getDisplayText() {
		return getQuestionText() + (StringUtils.isBlank(reference) ? ""
				: ". <span onclick='viewVerse(this.id)' id='bible-link' data-url='https://bible-api.com/" + reference
						+ "'>" + reference + "</span>");
	}

	public String getQuestionText() {
		return getName().replaceAll("(?i)\\b" + answer + "\\b", "______");
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
