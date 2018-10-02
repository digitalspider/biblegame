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
import au.com.digitalspider.biblegame.action.BuyAction;
import au.com.digitalspider.biblegame.model.Item;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/buy")
public class BuyController {

	@Autowired
	private BuyAction buyAction;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Item.getHelpMessageAsJson());
	}

	@GetMapping("/{item}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String item) {
		return execAction(request, item, 1);
	}

	@GetMapping("/{item}/{amount}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable String item,
			@PathVariable int amount) {
		try {
			User user = userService.getSessionUserNotNull();
			Action response = buyAction.execute(user, item, amount);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			buyAction.setFailMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buyAction);
		} catch (Exception e) {
			buyAction.setFailMessage(e.getMessage());
			return ResponseEntity.badRequest().body(buyAction);
		}
	}
}
