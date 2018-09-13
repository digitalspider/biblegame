package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Role {
	USER(Role.DESCRIPTION_USER), MODERATOR(Role.DESCRIPTION_MODERATOR), ADMIN(Role.DESCRIPTION_ADMIN);

	public static final String DESCRIPTION_USER = "User";
	public static final String DESCRIPTION_MODERATOR = "Moderator";
	public static final String DESCRIPTION_ADMIN = "Admin";

	private String description;

	private Role(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static Role parse(String value) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (Role role : Role.values()) {
				if (role.name().equals(value)) {
					return role;
				}
			}
		}
		throw new IllegalArgumentException("Role not valid: " + value);
	}

}
