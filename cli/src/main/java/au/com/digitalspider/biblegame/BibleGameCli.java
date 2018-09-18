package au.com.digitalspider.biblegame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import au.com.digitalspider.biblegame.service.GameService;

@SpringBootApplication
@ComponentScan("au.com.digitalspider.biblegame")
public class BibleGameCli implements CommandLineRunner {

	@Autowired
	private GameService gameService;

	public static final void main(String[] args) {
		SpringApplication.run(BibleGameCli.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("START GAME");
		gameService.start();
		System.out.println("END GAME");
	}
}
