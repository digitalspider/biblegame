package au.com.digitalspider.biblegame.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.UserService;

public class RootAction extends ActionBase {

	private ActionMain actionMain;

	@Autowired
	private BuyAction buyAction;
	@Autowired
	private GiveAction giveAction;
	@Autowired
	private KnockAction knockAction;
	@Autowired
	private StudyAction studyAction;
	@Autowired
	private HelpAction helpAction;

	@Autowired
	private LoggingService loggingService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActionService actionService;

	public RootAction() {
		setName("Root");
	}
	public RootAction(ActionMain actionMain) {
		this();
		this.actionMain = actionMain;
	}

	public RootAction(ActionMain actionMain, boolean success, String message) {
		this(actionMain);
		this.success = success;
		this.postMessage = message;
	}

	@Override
	public Action execute(User user, String input) {
		if (actionMain == null) {
			return this;
		}
		try {
			String message = "";
			ActionMain action = ActionMain.parse(input);
			switch (action) {
			case HELP:
				return helpAction.execute(user, input);
			case STUDY:
				return studyAction.execute(user, input);
			case WORK:
				return actionService.work(user);
			case PRAY:
				return actionService.pray(user);
			case BEG:
				return actionService.beg(user);
			case STEAL:
				return actionService.steal(user);
			case GIVE:
				return giveAction.execute(user, input);
			case READ:
				return actionService.read(user);
			case BUY:
				return buyAction.execute(user, input);
			case KNOCK:
				return knockAction.execute(user, input);
			case MESSAGE:
				return actionService.message(user);
			case CHAT:
				return actionService.chat(user);
			case FREE:
				return actionService.free(user);
			case LEADERBOARD:
				return actionService.leaderboard(user);
			case DONATE:
				return actionService.donate(user);
			case LOGOUT:
				return actionService.logout(user);
			case STATS:
				message = userService.getStats(user);
				loggingService.log(user, message);
				postMessage = message;
				return this;
			default:
				break;
			}
			setFailMessage("Action not yet implemented = " + action);
			return this;
		} catch (ActionException e) {
			loggingService.logError(user, e.getMessage());
			setFailMessage(e.getMessage());
			return this;
		}
	}

	@Override
	public List<Action> getActions() {
		if (actions.isEmpty()) {
			for (ActionMain actionItem : ActionMain.values()) {
				actions.add(new RootAction(actionItem));
			}
		}
		return actions;
	}

	@Override
	public void init(User user) {
		preMessage = "What would you like to do?";
	}
}
