package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum ActionLogin {
	LOGIN(ActionLogin.DESCRIPTION_LOGIN,ActionLogin.ACTION_KEY_LOGIN,null),
	REGISTER(ActionLogin.DESCRIPTION_REGISTER,ActionLogin.ACTION_KEY_REGISTER,null),
	QUIT(ActionLogin.DESCRIPTION_QUIT,ActionLogin.ACTION_KEY_QUIT,null),
	HELP(ActionLogin.DESCRIPTION_HELP,ActionLogin.ACTION_KEY_HELP,null);

	public static final String ACTION_KEY_LOGIN = "l";
	public static final String ACTION_KEY_REGISTER = "r";
	public static final String ACTION_KEY_QUIT = "q";
	public static final String ACTION_KEY_HELP = "?";
	
	public static final String DESCRIPTION_LOGIN = "has logged in";
	public static final String DESCRIPTION_REGISTER = "register new player";
	public static final String DESCRIPTION_QUIT = "quit the game";
	public static final String DESCRIPTION_HELP = "";
	
	private String description;
	private String actionKey;
	private Location location;
	
	private ActionLogin(String description, String actionKey, Location location) {
		this.description = description;
		this.actionKey = actionKey;
		this.location = location;
	}
	
	public String getDescription() {
		return description;
	}
	public String getActionKey() {
		return actionKey;
	}

	public Location getLocation() {
		return location;
	}

	public static ActionLogin parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (ActionLogin action : ActionLogin.values()) {
				if (action.name().equals(value)) {
					return action;
				}
			}
		}
		throw new IllegalArgumentException("Action not valid: " + value);
	}

	public static ActionLogin parseByKey(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			for (ActionLogin action : ActionLogin.values()) {
				if (action.getActionKey().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static void printHelp() {
		StringBuffer help = new StringBuffer();
		help.append("List of actions:\n");
		for (ActionLogin actionLogin : ActionLogin.values()) {
			help.append(actionLogin.name().toLowerCase() + " (" + actionLogin.getActionKey() + "),\n");
		}
		System.out.println(help.toString());
	}

}
