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
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.QuestionService;
import au.com.digitalspider.biblegame.service.UserService;

public class StudyAction extends ActionBase {

	private ActionService actionService;
	private QuestionService questionService;
	private UserService userService;
	private LoggingService loggingService;

	private static Map<Long, ListIterator<Question>> questionItrMap = new HashMap<>();

	public StudyAction(ActionService actionService) {
		super(ActionMain.STUDY.name());
		this.actionService = actionService;
		questionService = actionService.getQuestionService();
		userService = actionService.getUserService();
		loggingService = actionService.getLoggingService();
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
				postMessage = "Wrong. Answer is " + question.getAnswer();
			}
			loggingService.log(user, postMessage);
			StudyAction lastAction = new StudyAction(user, actionService, false, "");
			lastAction.setName("Question " + question.getId());
			lastAction.setHelpMessage(question.getName() + ". " + question.getReference() + "\n" + postMessage);
			actions.add(lastAction);
		}
		Question newQuestion = getNextQuestion(user);
		if (newQuestion != null) {
			preMessage = newQuestion.getName();
			postMessage += "\n" + preMessage;
			String actionUrl = "/study/" + newQuestion.getId() + "/";
			StudyAction action = new StudyAction(user, actionService, false, actionUrl);
			action.setName("Question " + newQuestion.getId());
			action.setHelpMessage(newQuestion.getName() + ". " + newQuestion.getReference());
			actions.add(0, action);
			loggingService.log(user, preMessage);
			return this;
		}
		// else all done
		questionItrMap.remove(user.getId());
		user.addKnowledge();
		user.addKnowledge(user.getBooks()); // Additional knowledge for having books
		userService.save(user);
		reply = user.getDisplayName() + " has completed his study. knowledge=" + user.getKnowledge();
		loggingService.log(user, reply);
		actionUrl = getActionUrl();
		postMessage = reply;
		completed = true;
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
	public void init(User user) {
		actions.clear();
		success = true;
	}

}
