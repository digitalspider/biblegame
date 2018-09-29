package au.com.digitalspider.biblegame.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.State;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;

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
	private ActionService actionService;

	public RootAction(ActionMain actionMain) {
		this.actionMain = actionMain;
	}

	@Override
	public Action execute(User user, String input) {
		if (actionMain == null) {
			return this;
		}
		switch (actionMain) {
		case WORK:
			actionService.work(user);
			user.addRiches();
			user.decreaseStamina();
		case GIVE:
			return giveAction;
		case STUDY:
			user.setState(State.STUDY);
			return studyAction;
		case BUY:
			user.setState(State.SHOP);
			return buyAction;
		case KNOCK:
			user.setState(State.VISIT);
			return knockAction;
		}
		return this;
	}

	@Override
	public List<Action> getActions(User user) {
		List<Action> actions = new ArrayList<>();
		for (ActionMain actionItem : ActionMain.values()) {
			actions.add(new RootAction(actionItem));
		}
		return actions;
	}

	@Override
	public String getPreMessage(User user) {
		return "What would you like to do?";
	}
}
