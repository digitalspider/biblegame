package au.com.digitalspider.biblegame.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import au.com.digitalspider.biblegame.exception.ActionException;
import au.com.digitalspider.biblegame.exception.ActionException.ActionExceptionType;
import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.UserService;

public abstract class ActionBase implements Action {

	private User user;
	protected String name = "";
	protected String type = "";
	protected String styleClass = "";
	protected String tooltip = "";
	protected String preMessage = "";
	protected String postMessage = "";
	protected String helpMessage = "";
	protected String actionKey;
	protected String actionUrl;
	protected boolean completed;
	protected boolean success = true;
	protected boolean enabled = true;
	protected List<Action> actions = new ArrayList<>();
	protected Action previousAction;

	protected ActionService actionService;
	protected UserService userService;
	protected LoggingService loggingService;

	public ActionBase(String name, ActionService actionService) {
		this.actionService = actionService;
		this.userService = actionService.getUserService();
		this.loggingService = actionService.getLoggingService();
		this.name = name;
		this.actionUrl = "/action/" + (StringUtils.isBlank(name) ? "" : name.toLowerCase() + "/");
	}

	protected void saveUser(User user) {
		setUser(user);
		userService.save(user);
	}

	public void setFailMessage(String postMessage) {
		this.success = false;
		this.postMessage = postMessage;
	}

	public void validateStamina(User user, ActionMain action) {
		if (!user.hasStamina()) {
			throw new ActionException(action, ActionExceptionType.TIRED);
		}
	}

	public void validateRiches(User user, ActionMain action) {
		if (!user.hasRiches()) {
			throw new ActionException(action, ActionExceptionType.POOR);
		}
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

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
