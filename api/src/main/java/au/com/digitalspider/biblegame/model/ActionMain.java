package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum ActionMain {
	LOGOUT(ActionMain.HELP_LOGOUT, ActionMain.DESCRIPTION_LOGOUT,ActionMain.ACTION_KEY_LOGOUT,null),
	STUDY(ActionMain.HELP_STUDY, ActionMain.DESCRIPTION_STUDY, ActionMain.ACTION_KEY_STUDY, Location.LIBRARY),
	WORK(ActionMain.HELP_WORK, ActionMain.DESCRIPTION_WORK,ActionMain.ACTION_KEY_WORK,Location.FIELD),
	PRAY(ActionMain.HELP_PRAY, ActionMain.DESCRIPTION_PRAY,ActionMain.ACTION_KEY_PRAY,Location.MOUNTAIN),
	BEG(ActionMain.HELP_BEG, ActionMain.DESCRIPTION_BEG,ActionMain.ACTION_KEY_BEG,Location.STREET),
	GIVE(ActionMain.HELP_GIVE, ActionMain.DESCRIPTION_GIVE,ActionMain.ACTION_KEY_GIVE,Location.STREET),
	STEAL(ActionMain.HELP_STEAL, ActionMain.DESCRIPTION_STEAL, ActionMain.ACTION_KEY_STEAL, Location.STREET),
	READ(ActionMain.HELP_READ, ActionMain.DESCRIPTION_READ, ActionMain.ACTION_KEY_READ, Location.TEMPLE),
	BUY(ActionMain.HELP_BUY, ActionMain.DESCRIPTION_BUY,ActionMain.ACTION_KEY_BUY,Location.MARKET),
	KNOCK(ActionMain.HELP_KNOCK, ActionMain.DESCRIPTION_KNOCK,ActionMain.ACTION_KEY_KNOCK,Location.STREET),
	FRIEND(ActionMain.HELP_FRIEND, ActionMain.DESCRIPTION_FRIEND, ActionMain.ACTION_KEY_FRIEND, null),
	MESSAGE(ActionMain.HELP_MESSAGE, ActionMain.DESCRIPTION_MESSAGE, ActionMain.ACTION_KEY_MESSAGE, Location.STREET),
	CHAT(ActionMain.HELP_CHAT, ActionMain.DESCRIPTION_CHAT,ActionMain.ACTION_KEY_CHAT,Location.TOWNSQUARE),
	FREE(ActionMain.HELP_FREE, ActionMain.DESCRIPTION_FREE, ActionMain.ACTION_KEY_FREE, null),
	LEADERBOARD(ActionMain.HELP_LEADERBOARD, ActionMain.DESCRIPTION_LEADERBOARD, ActionMain.ACTION_KEY_LEADERBOARD, null),
	STATS(ActionMain.HELP_STATS, ActionMain.DESCRIPTION_STATS, ActionMain.ACTION_KEY_STATS, null),
	DONATE(ActionMain.HELP_DONATE, ActionMain.DESCRIPTION_DONATE,ActionMain.ACTION_KEY_DONATE,null),
	HELP(ActionMain.HELP_HELP, ActionMain.DESCRIPTION_HELP,ActionMain.ACTION_KEY_HELP,null);

	public static final String ACTION_KEY_LOGOUT = "q";
	public static final String ACTION_KEY_STUDY = "s";
	public static final String ACTION_KEY_WORK = "w";
	public static final String ACTION_KEY_PRAY = "p";
	public static final String ACTION_KEY_BEG = "a";
	public static final String ACTION_KEY_GIVE = "g";
	public static final String ACTION_KEY_STEAL = "x";
	public static final String ACTION_KEY_READ = "r";
	public static final String ACTION_KEY_BUY = "b";
	public static final String ACTION_KEY_KNOCK = "k";
	public static final String ACTION_KEY_FRIEND = "j";
	public static final String ACTION_KEY_MESSAGE = "m";
	public static final String ACTION_KEY_CHAT = "c";
	public static final String ACTION_KEY_FREE = "f";
	public static final String ACTION_KEY_LEADERBOARD = "l";
	public static final String ACTION_KEY_STATS = "z";
	public static final String ACTION_KEY_DONATE = "d";
	public static final String ACTION_KEY_HELP = "?";
	
	public static final String DESCRIPTION_LOGOUT = "has logged out";
	public static final String DESCRIPTION_STUDY = "has decided to study";
	public static final String DESCRIPTION_WORK = "has decided to work";
	public static final String DESCRIPTION_PRAY = "has decided to pray";
	public static final String DESCRIPTION_BEG = "is begging";
	public static final String DESCRIPTION_GIVE = "has given graciously to beggars";
	public static final String DESCRIPTION_STEAL = "has stolen terribly from beggars";
	public static final String DESCRIPTION_BUY = "has bought something";
	public static final String DESCRIPTION_READ = "has decided to read scrolls";
	public static final String DESCRIPTION_KNOCK = "has decided to knock on someones door";
	public static final String DESCRIPTION_FRIEND = "has asked someone to be a friend";
	public static final String DESCRIPTION_MESSAGE = "wants to send a message";
	public static final String DESCRIPTION_CHAT = "started chatting";
	public static final String DESCRIPTION_FREE = "frees all slaves";
	public static final String DESCRIPTION_LEADERBOARD = "looks at the leaderboard";
	public static final String DESCRIPTION_STATS = "";
	public static final String DESCRIPTION_DONATE = "has donated kindly";
	public static final String DESCRIPTION_HELP = "";
	
	public static final String HELP_LOGOUT = "Press (q) to quit the game.";
	public static final String HELP_STUDY = "Studying will increase your knowledge. Books will help";
	public static final String HELP_WORK = "Working will increase your riches. Tools will help";
	public static final String HELP_PRAY = "Praying will increase your faith.";
	public static final String HELP_BEG = "Begging will give you some riches, but may cost some love";
	public static final String HELP_GIVE = "Giving money to beggars will increase your love";
	public static final String HELP_STEAL = "Stealing money from beggars will decrease your love";
	public static final String HELP_BUY = "Buy things like tools, books, slaves, and scrolls";
	public static final String HELP_READ = "Reading of scrolls is full of adventures";
	public static final String HELP_KNOCK = "Knocking allows you to visit and friend other players";
	public static final String HELP_FRIEND = "Make a friend request";
	public static final String HELP_MESSAGE = "Send message to friend";
	public static final String HELP_CHAT = "Enter chat mode";
	public static final String HELP_FREE = "Frees all slaves";
	public static final String HELP_STATS = "Show your current attributes/statistics";
	public static final String HELP_LEADERBOARD = "Show the leaderboard comparing players stats";
	public static final String HELP_DONATE = "Give real money to the developer of the game! THANK YOU!";
	public static final String HELP_HELP = "Press '?' for help";
	
	private String help;
	private String description;
	private String actionKey;
	private Location location;
	
	private ActionMain(String help, String description, String actionKey, Location location) {
		this.help = help;
		this.description = description;
		this.actionKey = actionKey;
		this.location = location;
	}

	public static ActionMain parse(String actionName) {
		if (actionName == null) {
			throw new IllegalArgumentException("Action not valid: " + actionName);
		}
		ActionMain action = null;
		if (actionName.trim().length() == 0) {
			action = ActionMain.HELP;
		} else if (actionName.length() == 1) {
			action = ActionMain.parseByKey(actionName.toLowerCase());
		} else {
			action = ActionMain.parseByName(actionName);
		}
		if (action == null) {
			throw new IllegalArgumentException("Action not valid: " + actionName);
		}
		return action;
	}

	public static ActionMain parseByName(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (ActionMain action : ActionMain.values()) {
				if (action.name().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static ActionMain parseByKey(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (ActionMain action : ActionMain.values()) {
				if (action.getActionKey().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static String getHelpMessage() {
		StringBuffer result = new StringBuffer();
		result.append("List of actions:\n");
		for (ActionMain action : ActionMain.values()) {
			result.append(action.name().toLowerCase() + " (" + action.getActionKey() + "), " + action.help + "\n");
		}
		return result.toString();
	}

	public static String getHelpMessageAsJson() {
		StringBuffer result = new StringBuffer();
		result.append("{[\n");
		for (ActionMain action : ActionMain.values()) {
			result.append("{name:'" + action.name().toLowerCase() + "', key: '" + action.getActionKey() 
					+ "', desc: '" + action.getDescription() 
					+ "', location:'" + (action.getLocation() != null ? action.getLocation().name() : "") 
					+ "', help: '" + action.getHelp()
					+ "'},\n");
		}
		result.append("]}\n");
		return result.toString();
	}

	public String getHelp() {
		return help;
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

}
