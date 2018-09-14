package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Action {
	LOGOUT(Action.DESCRIPTION_LOGOUT,Action.ACTION_KEY_LOGOUT,null),
	STUDY(Action.DESCRIPTION_STUDY, Action.ACTION_KEY_STUDY, Location.LIBRARY),
	WORK(Action.DESCRIPTION_WORK,Action.ACTION_KEY_WORK,Location.FIELD),
	PRAY(Action.DESCRIPTION_PRAY,Action.ACTION_KEY_PRAY,Location.MOUNTAIN),
	BEG(Action.DESCRIPTION_BEG,Action.ACTION_KEY_BEG,Location.STREET),
	STEAL(Action.DESCRIPTION_STEAL,Action.ACTION_KEY_STEAL,Location.STREET),
	GIVE(Action.DESCRIPTION_GIVE,Action.ACTION_KEY_GIVE,Location.STREET),
	READ(Action.DESCRIPTION_READ, Action.ACTION_KEY_READ, Location.TEMPLE),
	BUY(Action.DESCRIPTION_BUY,Action.ACTION_KEY_BUY,Location.MARKET),
	KNOCK(Action.DESCRIPTION_KNOCK,Action.ACTION_KEY_KNOCK,Location.STREET),
	CHAT(Action.DESCRIPTION_CHAT,Action.ACTION_KEY_CHAT,Location.TOWNSQUARE),
	STATS(Action.DESCRIPTION_STATS, Action.ACTION_KEY_STATS, null),
	DONATE(Action.DESCRIPTION_DONATE,Action.ACTION_KEY_DONATE,null),
	HELP(Action.DESCRIPTION_HELP,Action.ACTION_KEY_HELP,null);

	public static final String ACTION_KEY_LOGOUT = "q";
	public static final String ACTION_KEY_STUDY = "s";
	public static final String ACTION_KEY_WORK = "w";
	public static final String ACTION_KEY_PRAY = "p";
	public static final String ACTION_KEY_BEG = "a";
	public static final String ACTION_KEY_STEAL = "x";
	public static final String ACTION_KEY_GIVE = "g";
	public static final String ACTION_KEY_READ = "r";
	public static final String ACTION_KEY_BUY = "b";
	public static final String ACTION_KEY_KNOCK = "k";
	public static final String ACTION_KEY_CHAT = "c";
	public static final String ACTION_KEY_STATS = "z";
	public static final String ACTION_KEY_DONATE = "d";
	public static final String ACTION_KEY_HELP = "?";
	
	public static final String DESCRIPTION_LOGOUT = "has logged out";
	public static final String DESCRIPTION_STUDY = "has decided to study";
	public static final String DESCRIPTION_WORK = "has decided to work";
	public static final String DESCRIPTION_PRAY = "has decided to pray";
	public static final String DESCRIPTION_BEG = "is begging";
	public static final String DESCRIPTION_STEAL = "has stole some riches";
	public static final String DESCRIPTION_GIVE = "has given graciously";
	public static final String DESCRIPTION_BUY = "has bought something";
	public static final String DESCRIPTION_READ = "has decided to read scrolls";
	public static final String DESCRIPTION_KNOCK = "knocked on the door";
	public static final String DESCRIPTION_CHAT = "started chatting";
	public static final String DESCRIPTION_STATS = "";
	public static final String DESCRIPTION_DONATE = "has donated kindly";
	public static final String DESCRIPTION_HELP = "";
	
	private String description;
	private String actionKey;
	private Location location;
	
	private Action(String description, String actionKey, Location location) {
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

	public static Action parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (Action action : Action.values()) {
				if (action.name().equals(value)) {
					return action;
				}
			}
		}
		throw new IllegalArgumentException("Action not valid: " + value);
	}

	public static Action parseByKey(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			for (Action action : Action.values()) {
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
		for (Action action : Action.values()) {
			help.append(action.name().toLowerCase() + " (" + action.getActionKey() + "),\n");
		}
		System.out.println(help.toString());
	}

}
