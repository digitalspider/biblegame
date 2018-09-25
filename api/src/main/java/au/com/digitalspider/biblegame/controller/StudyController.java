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
import au.com.digitalspider.biblegame.service.StudyService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/study")
public class StudyController {

	@Autowired
	private StudyService studyService;
	@Autowired
	private UserService userService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("")
	public ResponseEntity<?> listActions() {
		return ResponseEntity.ok(Action.getHelpMessageAsJson());
	}

	@GetMapping("/{questionId}/{answer}")
	public ResponseEntity<?> execAction(HttpServletRequest request, @PathVariable int questionId,
			@PathVariable String answer) {
		try {
			User user = userService.getSessionUserNotNull();
			ActionResponse response = studyService.doStudy(user, questionId, answer);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
