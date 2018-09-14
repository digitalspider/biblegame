package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Scroll {
	GENESIS(Scroll.DESCRIPTION_GENESIS),
	EXODUS(Scroll.DESCRIPTION_EXODUS), 
	GOSPELS(Scroll.DESCRIPTION_GOSPELS);

	public static final String DESCRIPTION_GENESIS = "Genesis, the first scroll";
	public static final String DESCRIPTION_EXODUS = "Exodus, the second scroll";
	public static final String DESCRIPTION_GOSPELS = "the gospels, in the new testament";

	private String description;

	private Scroll(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static Scroll parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (Scroll scroll : Scroll.values()) {
				if (scroll.name().equals(value)) {
					return scroll;
				}
			}
		}
		throw new IllegalArgumentException("Scroll not valid: " + value);
	}

}
