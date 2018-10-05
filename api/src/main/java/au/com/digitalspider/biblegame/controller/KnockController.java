package au.com.digitalspider.biblegame.controller;

import javax.servlet.http.HttpServletRequest;

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
import au.com.digitalspider.biblegame.model.ActionKnock;
import au.com.digitalspider.biblegame.model.State;
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
			user.setState(State.VISIT);
			Action response = actionService.getNextAction(user, null);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
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

	@GetMapping("/{userName}/{actionName}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String actionName) {
		return execAction(request, userName, actionName, null);
	}

	@GetMapping("/{userName}/{actionName}/{amount}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String userName,
			@PathVariable String actionName, @PathVariable Integer amount) {
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
			ActionKnock action = ActionKnock.parse(actionName);
			Action response = knockAction.execute(user, player, action, amount);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(knockAction);
		} catch (Exception e) {
			knockAction.setFailMessage(e.getMessage());
			return ResponseEntity.badRequest().body(knockAction);
		}
	}
}
