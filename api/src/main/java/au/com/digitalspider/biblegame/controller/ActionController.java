package au.com.digitalspider.biblegame.controller;

import java.util.ArrayList;
import java.util.List;

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
	private static final Logger LOG = Logger.getLogger(ActionController.class);

	@Autowired
	private ActionService actionService;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<?> listActions(HttpServletRequest request) {
		User user = userService.getSessionUserNotNull();
		List<Action> actions = new ArrayList<>();
		ActionResponse actionResponse = new ActionResponse();
		actionResponse.setActions(actions);
		switch (user.getState()) {
		case FREE:
			actionResponse.setMessage(
					"You are " + user.getLocation().getDescription() + "\n. What would you like to do next?");
			if (user.hasStamina()) {
				actions.add(Action.WORK);
				actions.add(Action.STUDY);
				actions.add(Action.PRAY);
			}
			actions.add(Action.BEG);
			if (user.hasRiches()) {
				actions.add(Action.BUY);
				actions.add(Action.GIVE);
				actions.add(Action.FREE);
			}
			actions.add(Action.STEAL);
			actions.add(Action.KNOCK);
			actions.add(Action.MESSAGE);
			actions.add(Action.CHAT);
			actions.add(Action.LEADERBOARD);
			actions.add(Action.DONATE);
			actions.add(Action.STATS);
			actions.add(Action.HELP);
			actions.add(Action.LOGOUT);
			break;
		case SHOP:
			actionResponse.setMessage("What would you like to buy?");
			// actions.add(Action.BUY_TOOL);
			// actions.add(Action.BUY_BOOK);
			// actions.add(Action.BUY_LOCK);
			// actions.add(Action.BUY_SLAVE);
			// actions.add(Action.BUY_SCROLL);
			actions.add(Action.HELP);
			actions.add(Action.LEAVE);
			break;
		case VISIT:
			actionResponse.setMessage("You are in someones house. You can: give(g), steal(s), friend(f) or leave(l)?");
			actions.add(Action.GIVE);
			actions.add(Action.STEAL);
			actions.add(Action.FRIEND);
			actions.add(Action.MESSAGE);
			actions.add(Action.HELP);
			actions.add(Action.LEAVE);
			break;
		}

		return ResponseEntity.ok(actionResponse);
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
			LOG.error(e, e);
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
}
