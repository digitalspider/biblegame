package au.com.digitalspider.biblegame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import au.com.digitalspider.biblegame.service.GameService;
import au.com.digitalspider.biblegame.service.UserService;

@SpringBootApplication
public class BibleGameApp {

	@Autowired
	private UserService userService;

	@Autowired
	private GameService gameService;

	public static final void main(String[] args) {
		System.out.println("START GAME");
		BibleGameApp app = new BibleGameApp();
		app.start();
		System.out.println("END GAME");
	}

	public void start() {
		System.out.print("Please enter your username?");
		gameService.start();
	}
}
