package au.com.digitalspider.biblegame.exception;

import au.com.digitalspider.biblegame.model.ActionMain;

public class ActionException extends RuntimeException {
	private static final long serialVersionUID = 8817352678366094492L;

	public enum ActionExceptionType {
		POOR, TIRED
	}

	public ActionException(ActionMain action, ActionExceptionType type) {
		super("You are too " + type.name().toLowerCase() + " to " + action.name().toLowerCase());
	}

	public ActionException(String message) {
		super(message);
	}

}
