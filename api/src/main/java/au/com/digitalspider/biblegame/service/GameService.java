package au.com.digitalspider.biblegame.service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.ActionLogin;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GameService {
	private static final Logger LOG = Logger.getLogger(GameService.class);

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	public boolean validateUsername(String name) {
		if (name == null || name.length() <= 3) {
			System.err.println("Username is too short");
			return false;
		}
		return true;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private ActionService actionService;

	private Scanner scan = new Scanner(System.in);

	public void start() {
		try {
			User user = handleLogin();
			System.out.println("Welcome " + user);
			System.out.println("You are standing " + user.getLocation().getDescription());
			Action action = null;
			while (action != Action.LOGOUT) {
				System.out.print("What would you like to do? ");
				action = parseInput(scan.nextLine());
				// System.out.println("action=" + action);
				if (action == null) {
					System.out.println("Invalid input. Type (?) for help");
					continue;
				}
				actionService.doAction(user, action);
			}
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
	}

	public Action parseInput(String input) {
		if (StringUtils.isBlank(input)) {
			return null;
		}
		if (input.length()>1) {
			input = input.toLowerCase().substring(0,1);
		}
		return Action.parseByKey(input);
	}

	public User handleLogin() {
		String username = null;
		String password = null;
		User user = null;
		ActionLogin actionLogin = null;
		while (user == null) {
			System.out.print("Login (l) or Register (r)? ");
			scan = new Scanner(System.in);
			actionLogin = ActionLogin.parseByKey(scan.nextLine());
			// System.out.println("actionLogin=" + actionLogin);
			if (actionLogin == null) {
				System.out.println("Invalid input. Type (?) for help");
				continue;
			}
			switch (actionLogin) {
			case HELP:
				ActionLogin.printHelp();
				break;
			case LOGIN:
				try {
					System.out.print("Username: ");
					username = scan.nextLine();
					System.out.print("Password: ");
					password = scan.nextLine();
					user = userService.login(username, password);
				} catch (Exception e) {
					System.out.println("Invalid login attempt for user: " + username);
				}
				break;
			case REGISTER:
				try {
					System.out.print("Email: ");
					String email = scan.nextLine();
					if (!validateEmail(email)) {
						System.err.println("Email is invalid");
						break;
					}
					System.out.print("Username: ");
					username = scan.nextLine();
					if (!validateUsername(username)) {
						break;
					}
					System.out.print("Password: ");
					password = scan.nextLine();
					if (password == null || password.length() <= 3) {
						System.err.println("Password is too short");
						break;
					}
					user = userService.createUser(email, username, password);
				} catch (Exception e) {
					System.out.println("Invalid register attempt for user: " + username);
				}
				break;
			case QUIT:
				scan.close();
				System.exit(0);
			}
		}
		return user;
	}
}
