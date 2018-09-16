package au.com.digitalspider.biblegame.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import au.com.digitalspider.biblegame.io.LoginUser;
import au.com.digitalspider.biblegame.io.RegisterUser;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("")
	public ResponseEntity<?> getUser() {
		try {
			User user = userService.getByName("self");
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("")
	public ResponseEntity<?> saveUser(@RequestBody User userInput) {
		try {
			User user = userService.getByName(userInput.getName());
			// TODO: update notifications and settings
			return ResponseEntity.ok().body(userInput);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> findUser(@PathVariable String username) {
		try {
			User user = userService.getByName(username);
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated LoginUser loginUser) {
		try {
			User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Validated RegisterUser registerUser) {
		try {
			User user = userService.createUser(registerUser.getEmail(), registerUser.getUsername(),
					registerUser.getPassword());
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
