package au.com.digitalspider.biblegame.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/action")
public class ActionController {

	@Autowired
	private ActionService actionService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Action.getHelpMessageAsJson());
	}

	@GetMapping("/{actionName}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String actionName) {
		Action action = Action.parse(actionName);
		if (action == null) {
			action = Action.parseByKey(actionName.toLowerCase().substring(0, 1));
		}
		User user = null;
		ActionResponse response = actionService.doAction(user, action);
		if (response.isSuccess()) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}
}
