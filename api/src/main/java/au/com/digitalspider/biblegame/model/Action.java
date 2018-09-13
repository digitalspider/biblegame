package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Action {
	LOGIN(Action.DESCRIPTION_LOGIN),
	STUDY(Action.DESCRIPTION_STUDY),
	WORK(Action.DESCRIPTION_WORK),
	PRAY(Action.DESCRIPTION_PRAY),
	BEG(Action.DESCRIPTION_BEG),
	STEAL(Action.DESCRIPTION_STEAL),
	GIVE(Action.DESCRIPTION_GIVE),
	BUY(Action.DESCRIPTION_BUY),
	KNOCK(Action.DESCRIPTION_KNOCK),
	DONATE(Action.DESCRIPTION_DONATE);
	
	public static final String DESCRIPTION_LOGIN = "has logged in";
	public static final String DESCRIPTION_LOGOUT = "has logged out";
	public static final String DESCRIPTION_STUDY = "has decided to study";
	public static final String DESCRIPTION_WORK = "has decided to work";
	public static final String DESCRIPTION_PRAY = "has decided to pray";
	public static final String DESCRIPTION_BEG = "is begging";
	public static final String DESCRIPTION_STEAL = "has stole some riches";
	public static final String DESCRIPTION_GIVE = "has given graciously";
	public static final String DESCRIPTION_BUY = "has bought something";
	public static final String DESCRIPTION_KNOCK = "knocked on the door";
	public static final String DESCRIPTION_DONATE = "has donated kindly";
	
	private String description;
	
	private Action(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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

}
