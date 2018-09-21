package au.com.digitalspider.biblegame.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.io.LoginUser;
import au.com.digitalspider.biblegame.io.RegisterUser;
import au.com.digitalspider.biblegame.io.TokenResponse;
import au.com.digitalspider.biblegame.model.ActionLogin;
import au.com.digitalspider.biblegame.model.User;

@Service
public class GameService {
	private static final Logger LOG = Logger.getLogger(GameService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${oauth.resource:http://localhost:8080/api/v1}")
	private String baseUrl;
	@Value("${oauth.token:http://localhost:8080/oauth/token}")
	private String tokenUrl;

	private Scanner scan = new Scanner(System.in);

	private OAuth2RestTemplate restTemplate;

	public void start() {
		try {
			User user = handleLogin();
			System.out.println("Welcome " + user.getDisplayName());
			System.out.println("You are standing " + user.getLocation().getDescription());
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
					LoginUser loginUser = new LoginUser(username, password, grantType);
					String url = baseUrl + "/user/login";
					user = restTemplateBuilder.build().postForObject(url, loginUser, User.class);
					restTemplate = getRestTemplate(username, password);
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
					restTemplate = getRestTemplate(username, password);
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

	// TODO: No longer used as using below OAuth2RestTemplate
	public String getToken(String username, String password) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(tokenUrl);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("username", "test"));
			urlParameters.add(new BasicNameValuePair("password", "test"));
			urlParameters.add(new BasicNameValuePair("grant_type", "password"));
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(clientId, clientSecret);
			httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
			try (CloseableHttpResponse response = client.execute(httpPost)) {
				System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					System.out.println(result);
					Gson gson = new Gson();
					TokenResponse tokenResponse = gson.fromJson(result, TokenResponse.class);
					return tokenResponse.getAccessToken();
				} else {
					System.err.println("response=" + response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			return null;
		}
	}

	private OAuth2RestTemplate getRestTemplate(String username, String password) {
		System.out.println("getting OAuth2RestTemplate ...");

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setGrantType(grantType);
		resourceDetails.setAccessTokenUri(tokenUrl);

		// -- set the clients info
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);

		// -- set Resource Owner info
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password);

		return new OAuth2RestTemplate(resourceDetails);
	}

	public OAuth2RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
