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

	public void setFailMessage(String postMessage) {
		this.success = false;
		this.postMessage = postMessage;
	}

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

	public void setPreMessage(String preMessage) {
		this.preMessage = preMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setPreviousAction(Action previousAction) {
		this.previousAction = previousAction;
	}
}
