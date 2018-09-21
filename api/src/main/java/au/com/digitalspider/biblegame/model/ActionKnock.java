package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum ActionKnock {
	GIVE(ActionKnock.DESCRIPTION_GIVE,ActionKnock.ACTION_KEY_GIVE,null),
	STEAL(ActionKnock.DESCRIPTION_STEAL,ActionKnock.ACTION_KEY_STEAL,null),
	FRIEND(ActionKnock.DESCRIPTION_FRIEND,ActionKnock.ACTION_KEY_FRIEND,null),
	LEAVE(ActionKnock.DESCRIPTION_LEAVE, ActionKnock.ACTION_KEY_LEAVE, null),
	QUIT(ActionKnock.DESCRIPTION_QUIT,ActionKnock.ACTION_KEY_QUIT,null),
	HELP(ActionKnock.DESCRIPTION_HELP,ActionKnock.ACTION_KEY_HELP,null);

	public static final String ACTION_KEY_GIVE = "g";
	public static final String ACTION_KEY_STEAL = "s";
	public static final String ACTION_KEY_FRIEND = "f";
	public static final String ACTION_KEY_LEAVE = "l";
	public static final String ACTION_KEY_QUIT = "q";
	public static final String ACTION_KEY_HELP = "?";
	
	public static final String DESCRIPTION_GIVE = "Give the player some money";
	public static final String DESCRIPTION_STEAL = "Take money from the player";
	public static final String DESCRIPTION_FRIEND = "Request user to be your friend";
	public static final String DESCRIPTION_LEAVE = "Press 'l' or 'q' to leave their house";
	public static final String DESCRIPTION_QUIT = "Press 'l' or 'q' to leave their house";
	public static final String DESCRIPTION_HELP = "You are in someones house. You can: give(g), steal(s), friend(f) or leave(l)\n"
			+ DESCRIPTION_GIVE + "\n" + DESCRIPTION_STEAL + "\n" + DESCRIPTION_FRIEND + "\n"
			+ "\n"+DESCRIPTION_QUIT+"\n";
	
	private String description;
	private String actionKey;
	private Location location;
	
	private ActionKnock(String description, String actionKey, Location location) {
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

	public static ActionKnock parse(String actionName) {
		if (actionName == null) {
			throw new IllegalArgumentException("Action not valid: " + actionName);
		}
		ActionKnock action = null;
		if (actionName.trim().length() == 0) {
			action = ActionKnock.HELP;
		} else if (actionName.length() == 1) {
			action = ActionKnock.parseByKey(actionName.toLowerCase());
		} else {
			action = ActionKnock.parseByName(actionName);
		}
		if (action == null) {
			throw new IllegalArgumentException("Action not valid: " + actionName);
		}
		return action;
	}

	public static ActionKnock parseByName(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (ActionKnock action : ActionKnock.values()) {
				if (action.name().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static ActionKnock parseByKey(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			for (ActionKnock action : ActionKnock.values()) {
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
		for (ActionKnock actionLogin : ActionKnock.values()) {
			help.append(actionLogin.name().toLowerCase() + " (" + actionLogin.getActionKey() + "),\n");
		}
		System.out.println(help.toString());
	}

}
