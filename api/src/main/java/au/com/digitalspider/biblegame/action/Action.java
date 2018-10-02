package au.com.digitalspider.biblegame.action;

import java.util.List;

import au.com.digitalspider.biblegame.model.User;

public interface Action {

	boolean isSuccess();

	boolean isEnabled();

	boolean isCompleted();

	String getName();

	String getTooltip();

	String getPreMessage();

	String getPostMessage();

	List<Action> getActions();

	String getHelpMessage();

	String getActionKey();

	String getActionUrl();

	void init(User user);

	Action execute(User user, String input);

	Action getPreviousAction();
}