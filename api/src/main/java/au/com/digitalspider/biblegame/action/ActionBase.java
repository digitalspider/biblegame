package au.com.digitalspider.biblegame.action;

import java.util.ArrayList;
import java.util.List;

import au.com.digitalspider.biblegame.model.User;

public abstract class ActionBase implements Action {

	protected String preMessage = "";
	protected String postMessage = "";
	protected String helpMessage = "";
	protected String actionKey;
	protected String actionUrl;
	protected boolean completed;
	protected boolean success;
	protected List<Action> actions = new ArrayList<>();
	protected Action previousAction;

	@Override
	public String getPreMessage(User user) {
		return preMessage;
	}

	@Override
	public String getPostMessage(User user) {
		return postMessage;
	}

	@Override
	public String getHelpMessage(User user) {
		return helpMessage;
	}

	@Override
	public String getActionKey() {
		return actionKey;
	}

	@Override
	public String getActionUrl() {
		return actionUrl;
	}

	@Override
	public List<Action> getActions(User user) {
		return actions;
	}

	@Override
	public Action getPreviousAction(User user) {
		return previousAction;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
}
