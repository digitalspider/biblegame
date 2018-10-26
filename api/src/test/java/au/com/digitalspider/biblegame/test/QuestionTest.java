package au.com.digitalspider.biblegame.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.com.digitalspider.biblegame.model.Question;

public class QuestionTest {

	private Question question;
	
	@Before
	public void setup() {
		question = new Question();
	}

	@Test
	public void getQuestionText_start() {
		question.setAnswer("This");
		question.setName("This is a test");
		Assert.assertEquals("______ is a test", question.getQuestionText());
	}

	@Test
	public void getQuestionText_end() {
		question.setAnswer("This");
		question.setName("This is a test");
		Assert.assertEquals("______ is a test", question.getQuestionText());
	}

	@Test
	public void getQuestionText_partial() {
		question.setAnswer("is");
		question.setName("This is a test");
		Assert.assertEquals("This ______ a test", question.getQuestionText());
	}

	@Test
	public void getQuestionText_repeat() {
		question.setAnswer("this");
		question.setName("This is this test");
		Assert.assertEquals("______ is ______ test", question.getQuestionText());
	}

	@Test
	public void getQuestionText_real() {
		question.setAnswer("believe");
		question.setName(
				"whoever does not believe stands condemned already because they have not believed in the name of God’s one and only Son");
		Assert.assertEquals(
				"whoever does not ______ stands condemned already because they have not believed in the name of God’s one and only Son",
				question.getQuestionText());
	}
	
}
