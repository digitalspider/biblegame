package au.com.digitalspider.biblegame.service;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.io.LoginUser;
import au.com.digitalspider.biblegame.io.RegisterUser;
import au.com.digitalspider.biblegame.io.SimpleUser;
import au.com.digitalspider.biblegame.model.ActionLogin;
import au.com.digitalspider.biblegame.model.Message;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GameService {
	private static final Logger LOG = Logger.getLogger(GameService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private TokenHelperService tokenHelperService;
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Value("${oauth.resource:http://localhost:8080/api/v1}")
	private String baseUrl;

	private Scanner scan = new Scanner(System.in);

	private OAuth2RestTemplate restTemplate;

	public void start() {
		try {
			User user = handleLogin();
			System.out.println("Welcome " + user.getDisplayName());
			System.out.println("You are standing " + user.getLocation().getDescription());
			handleFriendRequests(user);
			handleMessages(user);
			String action = "";
			while (!action.equals("q")) {
				System.out.print("What would you like to do? ");
				try {
					action = getUserReply();
					if (StringUtils.isEmpty(action)) {
						System.out.println("Invalid input. Type (?) for help");
						continue;
					}
					String url = baseUrl + "/action/" + action;
					ActionResponse response = restTemplate.getForObject(url, ActionResponse.class);
					// System.out.println("response=" + response);
					if (StringUtils.isNotBlank(response.getMessage())) {
						if (response.isSuccess()) {
							System.out.println(response.getMessage());
						} else {
							System.err.println(response.getMessage());
						}
					}
					while (StringUtils.isNotBlank(response.getNextActionMessage())) {
						System.out.println(response.getNextActionMessage());
						String reply = getUserReply();
						if (StringUtils.isEmpty(reply)) {
							System.out.println("Invalid input. Type (?) for help");
							continue;
						}
						url = baseUrl + response.getNextActionUrl() + reply;
						response = restTemplate.getForObject(url, ActionResponse.class);
						// System.out.println("response=" + response);
						if (StringUtils.isNotBlank(response.getMessage())) {
							if (response.isSuccess()) {
								System.out.println(response.getMessage());
							} else {
								System.err.println(response.getMessage());
							}
						}
					}
				} catch (HttpClientErrorException e) {
					System.err.println("ERROR: " + e.getRawStatusCode() + ": " + e.getResponseBodyAsString());
					Gson gson = new Gson();
					ActionResponse response = gson.fromJson(e.getResponseBodyAsString(), ActionResponse.class);
					System.err.println(response.getMessage());
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			if (scan != null) {
				scan.close();
			}
		}
	}

	private void handleFriendRequests(User user) {
		if (!user.getFriendRequests().isEmpty()) {
			System.out.println("You had a friend request from: ");
			for (SimpleUser friend : user.getFriendRequests()) {
				System.out.println("* Player=" + friend.getDisplayName() + ". Level=" + friend.getLevel());
				String response = null;
				while (true) {
					System.out.println("Accept y/n? or deal with it later(l)?");
					response = scan.nextLine();
					if (StringUtils.isBlank(response)) {
						continue;
					}
					response = response.substring(0, 1).toLowerCase();
					if (response.equals("y")) {
						System.out.println("Great!");
						break;
					} else if (response.equals("n")) {
						System.out.println("Too bad!");
						break;
					} else if (response.equals("l")) {
						break;
					} else {
						System.err.println("Not sure I understood that!");
					}
				}
			}
		}
	}

	private void handleMessages(User user) {
		if (!user.getMessages().isEmpty()) {
			System.out.println("You have unread messages: ");
			for (Message message : user.getMessages()) {
				System.out.println("* Message=" + message.getMessage());
			}
			readAllMessages(user);
		}
	}

	private void readAllMessages(User user) {
		try {
			restTemplate.postForEntity(baseUrl + "/user/readAll", null, Object.class);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private String getUserReply() {
		String reply = scan.nextLine();
		// System.out.println("reply=" + reply);
		reply = reply.toLowerCase().trim();
		if (reply.equals("?")) {
			reply = "help";
		}
		return reply;
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
					LoginUser loginUser = new LoginUser(username, password, null);
					String url = baseUrl + "/user/login";
					user = restTemplateBuilder.build().postForObject(url, loginUser, User.class);
					restTemplate = tokenHelperService.getRestTemplate(username, password);
					user = restTemplate.getForObject(baseUrl + "/user", User.class);
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
					String url = baseUrl + "/user/register";
					RestTemplate restTemplate = restTemplateBuilder.build();
					RegisterUser registerUser = new RegisterUser(username, password, email);
					user = restTemplate.postForObject(url, registerUser, User.class);
					restTemplate = tokenHelperService.getRestTemplate(username, password);
					user = restTemplate.getForObject(baseUrl + "/user", User.class);
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
