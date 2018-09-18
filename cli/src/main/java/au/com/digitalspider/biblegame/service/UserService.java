package au.com.digitalspider.biblegame.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public void validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		if (!matcher.find()) {
			throw new RuntimeException("Email is invalid");
		}
	}

	public void validateUsername(String name) {
		if (name == null || name.length() <= 3) {
			throw new RuntimeException("Username is too short");
		}
	}

	public void validatePassword(String password) {
		if (password == null || password.length() <= 3) {
			throw new RuntimeException("Password is too short");
		}
	}
}
