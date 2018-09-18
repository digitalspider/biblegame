package au.com.digitalspider.biblegame.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;

public class StudyService {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	private Map<User, Iterator<Question>> questionMap = new HashMap<>();

	public List<Question> getQuestions(User user) {
		return questionService.findRandomForUser(user);
	}

	public ActionResponse doStudy(User user) {
		return doStudy(user, 0, null);
	}

	public ActionResponse doStudy(User user, int questionId, String answer) {
		String reply = StringUtils.EMPTY;
		if (questionId > 0) {
			reply = checkAnswer(user, questionId, answer);
		}
		Question question = getNextQuestion(user);
		if (question != null) {
			ActionResponse response = new ActionResponse(true, user, reply, question.getName(),
					"/api/v1/study/" + question.getId() + "/");
			return response;
		}
		// else all done
		questionMap.remove(user);
		user.addKnowledge();
		userService.save(user);
		reply = user.getDisplayName() + " has completed his study. knowledge=" + user.getKnowledge();
		loggingService.log(user, reply);
		ActionResponse response = new ActionResponse(true, user, reply, null, null);
		return response;
	}

	private Question getNextQuestion(User user) {
		Iterator<Question> itr = questionMap.get(user);
		if (itr == null) {
			List<Question> questions = getQuestions(user);
			itr = questions.iterator();
			questionMap.put(user, itr);
		}
		if (itr.hasNext()) {
			return itr.next();
		}
		return null;
	}

	public String checkAnswer(User user, int questionId, String answer) {
		Question question = questionService.getNotNull(questionId);
		if (question.getAnswer().equalsIgnoreCase(answer.trim())) {
			question.setCorrect(question.getCorrect() + 1);
			questionService.save(question);
			return "Correct";
		}
		question.setWrong(question.getWrong() + 1);
		questionService.save(question);
		return "Wrong. Answer is " + question.getAnswer();
	}
}
