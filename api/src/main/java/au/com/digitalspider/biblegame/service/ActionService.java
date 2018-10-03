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
	private RootAction rootAction;
	@Autowired
	private BuyAction buyAction;
	@Autowired
	private KnockAction knockAction;

	public ActionMain get(String value) {
		return ActionMain.parse(value);
	}

	public Action doAction(User user, String actionName) {
		return doAction(user, actionName, null);
	}

	public Action doAction(User user, String actionName, String actionInput) {
		try {
			Action action = getNextAction(user);
			return action.execute(user, actionName);
		} catch (Exception e) {
			ActionBase action = rootAction;
			action.setFailMessage(e.getMessage());
			return action;
		}
	}



	public Action getNextAction(User user) {
		Action action = null;
		switch (user.getState()) {
		case FREE:
			action = rootAction;
			action.init(user);
			break;
		case SHOP:
			action = buyAction;
			action.init(user);
			break;
		case VISIT:
			action = knockAction;
			action.init(user);
			break;
		default:
			action = rootAction;
			action.init(user);
			break;
		}
		return action;
	}

	public Action getNextAction(User user, Action executedAction) {
		if (executedAction == null || executedAction.isCompleted()) {
			user.setState(State.FREE);
			return rootAction;
		}
		return getNextAction(user);
	}
}
