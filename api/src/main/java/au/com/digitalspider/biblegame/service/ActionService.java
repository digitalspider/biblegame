package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.action.ActionBase;
import au.com.digitalspider.biblegame.action.BuyAction;
import au.com.digitalspider.biblegame.action.KnockAction;
import au.com.digitalspider.biblegame.action.RootAction;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.State;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ActionService {
	private static final Logger LOG = Logger.getLogger(ActionService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FriendService friendService;
	@Autowired
	private QuestionService questionService;

	public ActionMain get(String value) {
		return ActionMain.parse(value);
	}

	public Action doAction(User user, String actionName) {
		return doAction(user, actionName, null);
	}

	public Action doAction(User user, String actionName, String actionInput) {
		try {
			Action action = getActionByUserState(user);
			if (action instanceof RootAction) {
				return ((RootAction) action).execute(user, actionName, actionInput);
			}
			return action.execute(user, actionInput);
		} catch (Exception e) {
			ActionBase action = new RootAction(this);
			action.setFailMessage(e.getMessage());
			return action;
		}
	}

	public Action getActionByUserState(User user) {
		Action action = null;
		switch (user.getState()) {
		case FREE:
			action = new RootAction(this);
			break;
		case SHOP:
			action = new BuyAction(this);
			break;
		case VISIT:
			action = new KnockAction(this);
			break;
		default:
			action = new RootAction(this);
			break;
		}
		return action;
	}

	public Action getNextAction(User user, Action executedAction) {
		if (executedAction != null && !executedAction.isCompleted()) {
			return executedAction;
		}
		user.setState(State.FREE);
		RootAction rootAction = new RootAction(this);
		rootAction.init(user);
		if (executedAction != null && executedAction.isCompleted()) {
			rootAction.setSuccess(executedAction.isSuccess());
			rootAction.setPostMessage(executedAction.getPostMessage());
		}
		return rootAction;
	}

	public UserService getUserService() {
		return userService;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public FriendService getFriendService() {
		return friendService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}
}
