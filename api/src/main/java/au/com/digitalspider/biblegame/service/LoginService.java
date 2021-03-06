package au.com.digitalspider.biblegame.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.User;

@Service
public class LoginService {

	@Autowired
	private TokenHelperService tokenHelperService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;

	private BCryptPasswordEncoder getPasswordEncoder() {
		return userService.getEncoder();
	}

	public User createUser(String email, String username, String password) {
		userService.validateEmail(email);
		userService.validateUsername(username);
		userService.validatePassword(password);
		User user = new User().withName(username);
		userService.initUser(user);
		user.setEmail(email);
		user.setPassword(getPasswordEncoder().encode(password));
		user.setLastLoginAt(new Date());
		user = userService.save(user);
		authenticate(user, password);
		userService.save(user);
		user.setPassword(null);
		return user;
	}

	public User login(String username, String password) {
		User user = userService.getByName(username);
		if (user == null) {
			throw new BadCredentialsException(username + " provided invalid credentials");
		}
		authenticate(user, password);
		userService.initUser(user);
		user.setLastLoginAt(new Date());
		userService.save(user);
		user.setPassword(null);
		return user;
	}

	private boolean authenticate(User user, String password) {
		if (user != null && getPasswordEncoder().matches(password, user.getPassword())) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user, password, user.getAuthorities());
			Authentication authResult = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			if (authResult.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authResult);
				user.setToken(tokenHelperService.getToken(user.getName(), password));
				return true;
			}
			return true;
		}
		throw new BadCredentialsException(user.getDisplayName() + " provided invalid credentials");
	}
}
