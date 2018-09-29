package au.com.digitalspider.biblegame.action;

import java.util.List;

import au.com.digitalspider.biblegame.model.User;

public interface Action {

	boolean isSuccess();

	boolean isCompleted();

	String getPreMessage(User user);

	String getPostMessage(User user);

	List<Action> getActions(User user);

	String getHelpMessage(User user);

	String getActionKey();

	String getActionUrl();

	Action execute(User user, String input);

	Action getPreviousAction(User user);
}
