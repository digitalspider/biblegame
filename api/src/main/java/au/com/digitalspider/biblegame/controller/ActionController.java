package au.com.digitalspider.biblegame.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import au.com.digitalspider.biblegame.action.RootAction;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/action")
public class ActionController {
	private static final Logger LOG = Logger.getLogger(ActionController.class);

	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@Autowired
	private RootAction rootAction;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<Action> listActions(HttpServletRequest request) {
		User user = userService.getSessionUserNotNull();
		Action action = actionService.getNextAction(user);
		return ResponseEntity.ok(action);
	}

	@GetMapping("/{actionInput}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String actionInput) {
		try {
			User user = userService.getSessionUserNotNull();
			Action action = actionService.doAction(user, actionInput);
			Action nextAction = actionService.getNextAction(user, action);
			controllerHelperService.formatResponse(request, nextAction);
			return ResponseEntity.ok(nextAction);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rootAction);
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(rootAction);
		}
	}
}
