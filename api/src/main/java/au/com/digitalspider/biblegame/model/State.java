package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum State {
	FREE(State.DESCRIPTION_FREE),
	SHOP(State.DESCRIPTION_SHOP),
	STUDY(State.DESCRIPTION_STUDY),
	VISIT(State.DESCRIPTION_VISIT);
	
	private static final String DESCRIPTION_FREE = "";
	private static final String DESCRIPTION_SHOP = "is buying at the shops";
	private static final String DESCRIPTION_STUDY = "is studing at the library";
	private static final String DESCRIPTION_VISIT = "is visiting someone";

	private String description;
	
	private State(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public static State parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (State state : State.values()) {
				if (state.name().equals(value)) {
					return state;
				}
			}
		}
		throw new IllegalArgumentException("State not valid: " + value);
	}

}
