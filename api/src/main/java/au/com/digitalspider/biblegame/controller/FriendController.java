package au.com.digitalspider.biblegame.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.FriendService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/friend")
public class FriendController {

	private static final Logger LOG = Logger.getLogger(FriendController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;

	@GetMapping("/add/{friendId}")
	public ResponseEntity<ActionResponse> addFriend(HttpServletRequest request, @PathVariable long friendId) {
		try {
			User user = userService.getSessionUser();
			User friend = userService.get(friendId);
			friendService.addFriendRequest(user, friend);
			return ResponseEntity.ok().body(new ActionResponse(true, user, "friend request sent"));
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(new ActionResponse(false, null, e.getMessage()));
		}
	}

	@GetMapping("/accept/{friendId}")
	public ResponseEntity<ActionResponse> acceptFriend(HttpServletRequest request, @PathVariable long friendId) {
		try {
			User user = userService.getSessionUser();
			User friend = userService.get(friendId);
			friendService.acceptFriend(user, friend);
			return ResponseEntity.ok().body(new ActionResponse(true, user, "friend request accepted"));
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(new ActionResponse(false, null, e.getMessage()));
		}
	}


	@GetMapping("/remove/{friendId}")
	public ResponseEntity<ActionResponse> removeFriend(HttpServletRequest request, @PathVariable long friendId) {
		try {
			User user = userService.getSessionUser();
			User friend = userService.get(friendId);
			friendService.removeFriend(user, friend);
			return ResponseEntity.ok().body(new ActionResponse(true, user, "friend was removed"));
		} catch (Exception e) {
			LOG.error(e, e);
			return ResponseEntity.badRequest().body(new ActionResponse(false, null, e.getMessage()));
		}
	}
}
