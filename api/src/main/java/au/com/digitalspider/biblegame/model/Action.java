package au.com.digitalspider.biblegame.model;

public enum Action {
	LOGIN2(Action.DESCRIPTION_HOME);

	public static final String LOGIN = "login";
	public static final String STUDY = "study";
	public static final String WORK = "market";
	public static final String PRAY = "library";
	public static final String BEG = "template";
	public static final String STEAL = "mountain";
	public static final String GIVE = "mountain";
	public static final String BUY = "mountain";
	public static final String KNOCK = "mountain";
	public static final String DONATE = "mountain";
	
	public static final String DESCRIPTION_HOME = "at Home";
	public static final String DESCRIPTION_STREET = "On the streets, with a lot of beggers crowding around";
	public static final String DESCRIPTION_MARKET = "at the Marketplace, where things can be bought";
	public static final String DESCRIPTION_LIBRARY = "at the Library, where you can study";
	public static final String DESCRIPTION_TEMPLE = "at the Temple, where you can study your scrolls";
	public static final String DESCRIPTION_MOUNTAIN = "on top of a high mountiain, where you can pray quietly";
	
	private String description;
	
	private Action(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public Action parse(String value) {
		for (Action action : Action.values()) {
			if (action.name().equalsIgnoreCase(value)) {
				return action;
			}
		}
		return Action.valueOf(value);
	}

}
