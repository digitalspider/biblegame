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
import au.com.digitalspider.biblegame.model.Item;
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
		return ResponseEntity.ok(Item.getHelpMessageAsJson());
	}

	@GetMapping("/{item}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String item) {
		return execAction(request, item, 1);
	}

	@GetMapping("/{item}/{action}")
	public ResponseEntity<ActionResponse> execAction(HttpServletRequest request, @PathVariable String item,
			@PathVariable int amount) {
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = buyService.doBuy(user, item, amount);
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
