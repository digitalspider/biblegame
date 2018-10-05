package au.com.digitalspider.biblegame.action;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import au.com.digitalspider.biblegame.model.User;

public interface Action {

	boolean isSuccess();

	boolean isEnabled();

	boolean isCompleted();

	User getUser();

	String getName();

	String getType();

	String getStyleClass();

	String getTooltip();

	String getPreMessage();

	String getPostMessage();

	List<Action> getActions();

	String getHelpMessage();

	String getActionKey();

	String getActionUrl();

	void init(User user);

	Action execute(User user, String input);

	@JsonIgnore
	Action getPreviousAction();
}
