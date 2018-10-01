package au.com.digitalspider.biblegame.action;

import java.util.ArrayList;
import java.util.List;

public abstract class ActionBase implements Action {

	protected String name = "";
	protected String tooltip = "";
	protected String preMessage = "";
	protected String postMessage = "";
	protected String helpMessage = "";
	protected String actionKey;
	protected String actionUrl;
	protected boolean completed;
	protected boolean success;
	protected boolean enabled = true;
	protected List<Action> actions = new ArrayList<>();
	protected Action previousAction;

	@Override
	public String getPreMessage() {
		return preMessage;
	}

	@Override
	public String getPostMessage() {
		return postMessage;
	}

	@Override
	public String getHelpMessage() {
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
	public List<Action> getActions() {
		return actions;
	}

	@Override
	public Action getPreviousAction() {
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

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
