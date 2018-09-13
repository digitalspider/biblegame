package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Location {
	HOME(Location.DESCRIPTION_HOME),
	STREET(Location.DESCRIPTION_STREET),
	MARKET(Location.DESCRIPTION_MARKET),
	LIBRARY(Location.DESCRIPTION_LIBRARY),
	TEMPLE(Location.DESCRIPTION_TEMPLE),
	MOUNTAIN(Location.DESCRIPTION_MOUNTAIN);
	
	private static final String DESCRIPTION_HOME = "at Home";
	private static final String DESCRIPTION_STREET = "On the streets, with a lot of beggers crowding around";
	private static final String DESCRIPTION_MARKET = "at the Marketplace, where things can be bought";
	private static final String DESCRIPTION_LIBRARY = "at the Library, where you can study";
	private static final String DESCRIPTION_TEMPLE = "at the Temple, where you can study your scrolls";
	private static final String DESCRIPTION_MOUNTAIN = "on top of a high mountiain, where you can pray quietly";

	private String description;
	
	private Location(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public static Location parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (Location location : Location.values()) {
				if (location.name().equals(value)) {
					return location;
				}
			}
		}
		throw new IllegalArgumentException("Location not valid: " + value);
	}

}
