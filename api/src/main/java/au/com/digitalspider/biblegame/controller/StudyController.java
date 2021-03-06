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
import au.com.digitalspider.biblegame.action.StudyAction;
import au.com.digitalspider.biblegame.model.Question;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.ControllerHelperService;
import au.com.digitalspider.biblegame.service.QuestionService;
import au.com.digitalspider.biblegame.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/study")
public class StudyController {

	@Autowired
	private UserService userService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private ControllerHelperService controllerHelperService;

	@GetMapping("/{questionId}/{answer}")
	public ResponseEntity<Action> execAction(HttpServletRequest request, @PathVariable int questionId,
			@PathVariable String answer) {
		StudyAction studyAction = new StudyAction(actionService);
		try {
			User user = userService.getSessionUserNotNull();
			Question question = questionService.getNotNull(questionId);
			Action response = studyAction.execute(user, question, answer);
			controllerHelperService.formatResponse(request, response);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			studyAction.setFailMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(studyAction);
		} catch (Exception e) {
			studyAction.setFailMessage(e.getMessage());
			return ResponseEntity.badRequest().body(studyAction);
		}
	}
}
