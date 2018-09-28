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
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.FriendService;
import au.com.digitalspider.biblegame.service.MessageService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;
	@Autowired
	private FriendService friendService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Action.getHelpMessageAsJson());
	}

	@GetMapping("/readAll")
	public ResponseEntity<ActionResponse> readAll(HttpServletRequest request) {
		try {
			User user = userService.getSessionUserNotNull();
			messageService.readAllMessages(user);
			return ResponseEntity.ok(new ActionResponse(true, user, "Messages read"));
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/read/{messageId}")
	public ResponseEntity<ActionResponse> readMessage(HttpServletRequest request, @PathVariable Long messageId) {
		try {
			User user = userService.getSessionUserNotNull();
			messageService.readMessage(user, messageId);
			return ResponseEntity.ok(new ActionResponse(true, user, "Message read"));
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/delete/{messageId}")
	public ResponseEntity<ActionResponse> deleteMessage(HttpServletRequest request, @PathVariable Long messageId) {
		try {
			User user = userService.getSessionUserNotNull();
			messageService.deleteMessage(user, messageId);
			return ResponseEntity.ok(new ActionResponse(true, user, "Message deleted"));
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/send/{whoAndWhat}")
	public ResponseEntity<ActionResponse> sendMessage(HttpServletRequest request, @PathVariable String whoAndWhat) {
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = messageService.doMessage(user, whoAndWhat);
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

	@GetMapping("/send/{friendId}/{message}")
	public ResponseEntity<ActionResponse> sendMessage(HttpServletRequest request, @PathVariable long friendId,
			@PathVariable String message) {
		try {
			User user = userService.getSessionUserNotNull();
			User friend = userService.get(friendId);
			friendService.validateFriend(user, friend);
			messageService.sendMessage(user, friend, "Friend", message);
			return ResponseEntity.ok(new ActionResponse(true, user, "Message sent to " + friend.getDisplayName()));
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/send/name/{friendName}/{message}")
	public ResponseEntity<ActionResponse> sendMessage(HttpServletRequest request, @PathVariable String friendName,
			@PathVariable String message) {
		try {
			User user = userService.getSessionUserNotNull();
			User friend = userService.getByName(friendName);
			friendService.validateFriend(user, friend);
			messageService.sendMessage(user, friend, "Friend", message);
			return ResponseEntity.ok(new ActionResponse(true, user, "Message sent to " + friend.getDisplayName()));
		} catch (BadCredentialsException e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			ActionResponse response = new ActionResponse(false, null, e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
}
