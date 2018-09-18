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
import au.com.digitalspider.biblegame.service.BuyService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/buy")
public class BuyController {

	@Autowired
	private BuyService buyService;
	@Autowired
	private UserService userService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Action.getHelpMessageAsJson());
	}

	@GetMapping("/{item}")
	public ResponseEntity<?> execAction(HttpServletRequest request, @PathVariable String item) {
		return execAction(request, item, 1);
	}

	@GetMapping("/{item}/{action}")
	public ResponseEntity<?> execAction(HttpServletRequest request, @PathVariable String item,
			@PathVariable int amount) {
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = buyService.doBuy(user, item, amount);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
