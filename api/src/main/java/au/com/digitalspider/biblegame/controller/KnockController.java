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

import au.com.digitalspider.biblegame.action.Action;
import au.com.digitalspider.biblegame.action.KnockAction;
import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/knock")
public class KnockController {

	@Autowired
	private UserService userService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<Action> listPlayers(HttpServletRequest request) {
		KnockAction knockAction = new KnockAction(actionService);
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = new KnockAction(actionService).getRandomPlayers(user);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(knockAction);
		} catch (BadCredentialsException e) {
			knockAction.setFailMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(knockAction);
		} catch (Exception e) {
			knockAction.setFailMessage(e.getMessage());
			return ResponseEntity.badRequest().body(knockAction);
		}
	}

	@GetMapping("/{userName}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String userName) {
		return execAction(request, userName, null, null);
	}

	@GetMapping("/{userName}/{action}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String action) {
		return execAction(request, userName, action, null);
	}

	@GetMapping("/{userName}/{action}/{amount}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String action, @PathVariable Integer amount) {
		String nextUrl = "/knock/" + userName;
		if (StringUtils.isNotBlank(action)) {
			nextUrl += "/" + action;
		}
		User user;
		KnockAction knockAction = new KnockAction(actionService);
		try {
			user = userService.getSessionUserNotNull();
		} catch (BadCredentialsException e) {
			knockAction.setFailMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(knockAction);
		}
		try {
			User player = knockAction.retrievePlayer(user, userName);
			Action response = knockAction.execute(user, player, action, amount);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(knockAction);
		} catch (Exception e) {
			knockAction.setFailMessage(e.getMessage());
			return ResponseEntity.badRequest().body(knockAction);
		}
	}
}
