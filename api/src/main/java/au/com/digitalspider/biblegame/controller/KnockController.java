package au.com.digitalspider.biblegame.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import au.com.digitalspider.biblegame.action.KnockAction;
import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/knock")
public class KnockController {

	@Autowired
	private KnockAction knockService;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<ActionResponse> listPlayers(HttpServletRequest request) {
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = knockService.getRandomPlayers(user);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/{userName}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String userName) {
		return execAction(request, userName, null, null);
	}

	@GetMapping("/{userName}/{action}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String action) {
		return execAction(request, userName, action, null);
	}

	@GetMapping("/{userName}/{action}/{amount}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String action, @PathVariable Integer amount) {
		String nextUrl = "/knock/" + userName;
		if (StringUtils.isNotBlank(action)) {
			nextUrl += "/" + action;
		}
		User user;
		try {
			user = userService.getSessionUserNotNull();
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		try {
			User player = knockService.retrievePlayer(user, userName);
			ActionResponse response = knockService.doKnock(user, player, action, amount);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, user, e.getMessage(), null, nextUrl);
			return ResponseEntity.badRequest().body(response);
		}
	}
}
