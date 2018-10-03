package au.com.digitalspider.biblegame.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.digitalspider.biblegame.model.ActionKnock;
import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.QuestionService;
import au.com.digitalspider.biblegame.service.UserService;

@Component
public class StudyAction extends ActionBase {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	private Map<Long, Iterator<Question>> questionMap = new HashMap<>();

	public StudyAction() {
		setName("Study");
	}

	public StudyAction(User user, boolean success, Iterator<Question> questionItr, String actionUrl) {
		this();
		this.success = success;
		this.actionUrl = actionUrl;
		this.questionMap.put(user.getId(), questionItr);
	}

	public Iterable<Question> getQuestions(User user) {
		return questionService.findRandomForUser(user);
	}

	@Override
	public Action execute(User user, String input) {
		return execute(user, 0, input);
	}

	public Action execute(User user, int questionId, String answer) {
		String reply = StringUtils.EMPTY;
		boolean correct = true;
		if (questionId > 0) {
			loggingService.log(user, answer);
			Question question = questionService.getNotNull(questionId);
			correct = checkAnswer(user, question, answer);
			if (correct) {
				reply = "Correct";
			} else {
				reply = "Wrong. Answer is " + question.getAnswer();
			}
			loggingService.log(user, reply);
		}
		Question question = getNextQuestion(user);
		if (question != null) {
			StudyAction response = new StudyAction(user, correct, questionMap.get(user.getId()),
					getActionUrl() + question.getId() + "/");
			loggingService.log(user, question.getName());
			return response;
		}
		// else all done
		questionMap.remove(user.getId());
		user.addKnowledge();
		user.addKnowledge(user.getBooks()); // Additional knowledge for having books
		userService.save(user);
		reply = user.getDisplayName() + " has completed his study. knowledge=" + user.getKnowledge();
		loggingService.log(user, reply);
		StudyAction response = new StudyAction(user, correct, null, getActionUrl());
		response.postMessage = reply;
		response.completed = true;
		return response;
	}

	private Question getNextQuestion(User user) {
		Iterator<Question> itr = questionMap.get(user.getId());
		if (itr == null) {
			Iterable<Question> questions = getQuestions(user);
			itr = questions.iterator();
			questionMap.put(user.getId(), itr);
		}
		if (itr.hasNext()) {
			return itr.next();
		}
		return null;
	}

	public boolean checkAnswer(User user, Question question, String answer) {
		if (question.getAnswer().equalsIgnoreCase(answer.trim())) {
			question.setCorrect(question.getCorrect() + 1);
			questionService.save(question);
			return true;
		}
		question.setWrong(question.getWrong() + 1);
		questionService.save(question);
		return false;
	}

	@Override
	public String getActionUrl() {
		return super.getActionUrl() + "/study/";
	}

	@Override
	public void init(User user) {
		preMessage = getNextQuestion(user).getName();
		actions.clear();
		for (ActionKnock actionItem : ActionKnock.values()) {
			actions.add(new KnockAction(actionItem));
		}
	}


	@Override
	public List<Action> getActions() {
		return actions;
	}

}
