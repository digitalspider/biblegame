package au.com.digitalspider.biblegame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

	@RequestMapping("/")
	public String index() {
		return "Hello game";
	}
}
