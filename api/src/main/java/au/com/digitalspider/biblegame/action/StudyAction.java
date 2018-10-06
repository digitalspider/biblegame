package au.com.digitalspider.biblegame.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.QuestionService;

public class StudyAction extends ActionBase {

	private ActionService actionService;
	private QuestionService questionService;

	private static Map<Long, ListIterator<Question>> questionItrMap = new HashMap<>();

	public StudyAction(ActionService actionService) {
		super(ActionMain.STUDY.name(), actionService);
		this.type = "full";
		this.actionService = actionService;
		questionService = actionService.getQuestionService();
	}

	public StudyAction(User user, ActionService actionService, boolean enabled, String actionUrl) {
		this(actionService);
		this.enabled = enabled;
		this.actionUrl = actionUrl;
	}

	public LinkedList<Question> getQuestions(User user) {
		LinkedList<Question> questionList = new LinkedList<>();
		for (Question question : questionService.findRandomForUser(user)) {
			questionList.add(question);
		}
		return questionList;
	}

	@Override
	public Action execute(User user, String input) {
		Question question = null;
		ListIterator<Question> itr = questionItrMap.get(user.getId());
		if (itr != null) {
			question = itr.previous();
			itr.next();
		}
		return execute(user, question, input);
	}

	public Action execute(User user, Question question, String answer) {
		init(user);
		String reply = StringUtils.EMPTY;
		if (question != null && answer != null) {
			loggingService.log(user, answer);
			success = checkAnswer(user, question, answer);
			if (success) {
				postMessage = "Correct";
			} else {
				postMessage = "Correct answer is " + question.getAnswer();
			}
			loggingService.log(user, postMessage);
			StudyAction lastAction = new StudyAction(user, actionService, false, "");
			lastAction.setName("Question " + question.getId());
			String message = question.getName() + "\n" + postMessage;
			lastAction.setHelpMessage(message);
			actions.add(lastAction);
		}
		Question newQuestion = getNextQuestion(user);
		if (newQuestion != null) {
			preMessage = "Answer?";
			if (success) {
				postMessage += "\n" + newQuestion.getDisplayText();
			}
			String actionUrl = "/study/" + newQuestion.getId() + "/";
			StudyAction action = new StudyAction(user, actionService, false, actionUrl);
			action.setName("Question " + newQuestion.getId());
			action.setHelpMessage(newQuestion.getDisplayText());
			actions.add(0, action);
			loggingService.log(user, preMessage);
			return this;
		}
		// else all done
		questionItrMap.remove(user.getId());
		user.decreaseStamina();
		user.addKnowledge();
		user.addKnowledge(user.getBooks()); // Additional knowledge for having books
		saveUser(user);
		reply = user.getDisplayName() + " has completed his study. knowledge=" + user.getKnowledge();
		loggingService.log(user, reply);
		postMessage = reply;
		completed = true;
		success = true;
		return this;
	}

	private Question getNextQuestion(User user) {
		ListIterator<Question> itr = questionItrMap.get(user.getId());
		if (itr == null) {
			LinkedList<Question> questionList = getQuestions(user);
			itr = questionList.listIterator();
			questionItrMap.put(user.getId(), itr);
		}
		if (itr.hasNext()) {
			return itr.next();
		}
		return null;
	}

	public boolean checkAnswer(User user, Question question, String answer) {
		int dist = StringUtils.getLevenshteinDistance(question.getAnswer(), answer.trim());
		if (dist <= 1) {
			question.setCorrect(question.getCorrect() + 1);
			questionService.save(question);
			return true;
		}
		question.setWrong(question.getWrong() + 1);
		questionService.save(question);
		return false;
	}

	@Override
	public void init(User user) {
		actions.clear();
		success = true;
	}

}
