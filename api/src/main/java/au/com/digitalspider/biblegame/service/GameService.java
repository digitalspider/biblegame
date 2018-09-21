package au.com.digitalspider.biblegame.service;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.ActionLogin;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GameService {
	private static final Logger LOG = Logger.getLogger(GameService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ActionService actionService;

	private Scanner scan = new Scanner(System.in);

	public void start() {
		try {
			User user = handleLogin();
			System.out.println("Welcome " + user.getDisplayName());
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
					user = loginService.login(username, password);
				} catch (BadCredentialsException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					System.err.println("Invalid login attempt for user: " + username);
					LOG.error(e, e);
				}
				break;
			case REGISTER:
				try {
					System.out.print("Email: ");
					String email = scan.nextLine();
					userService.validateEmail(email);
					System.out.print("Username: ");
					username = scan.nextLine();
					userService.validateUsername(username);
					System.out.print("Password: ");
					password = scan.nextLine();
					userService.validatePassword(password);
					user = loginService.createUser(email, username, password);
				} catch (Exception e) {
					System.err.println("Invalid: " + e.getMessage());
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
