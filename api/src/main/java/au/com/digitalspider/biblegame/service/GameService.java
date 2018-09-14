package au.com.digitalspider.biblegame.service;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GameService {
	private static final Logger LOG = Logger.getLogger(GameService.class);

	@Autowired
	private UserService userService;

	public void start() {
		try {
			System.out.print("Login (l) or Register (r)?");
			Scanner scan = new Scanner(System.in);
			String username = "david";
			Action action = parseInput(scan.nextLine());
			if (action == null) {
				System.out.println("Invalid input. Type (?) for help");
			}
			User user = userService.getByName(username);
			System.out.println("user=" + user);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public Action parseInput(String input) {
		if (StringUtils.isBlank(input)) {
			System.out.println("Type (?) for help");
			return null;
		}
		if (input.length()>1) {
			input = input.toLowerCase().substring(0,1);
		}
		return Action.parseByKey(input);
	}

	public void printHelp() {
		StringBuffer help = new StringBuffer(); 
		help.append("List of actions:\n");
		for (Action action: Action.values()) {
			help.append(action.name().toLowerCase() + " (" + action.getActionKey() + "),\n");
		}
		System.out.println(help.toString());
	}
}
