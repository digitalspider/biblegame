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

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/action")
public class ActionController {

	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Action.getHelpMessageAsJson());
	}

	@GetMapping("/{actionName}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String actionName) {
		try {
			Action action = Action.parse(actionName);
			User user = userService.getSessionUserNotNull();
			ActionResponse response = actionService.doAction(user, action);
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
}
