package au.com.digitalspider.biblegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableAutoConfiguration
@EnableAsync
@ComponentScan("au.com.digitalspider.biblegame")
@EntityScan("au.com.digitalspider.biblegame.model")
@EnableJpaRepositories("au.com.digitalspider.biblegame.repo")
// public class BibleGameApp implements CommandLineRunner {
public class BibleGameApp {

	// @Autowired
	// private GameService gameService;

	public static final void main(String[] args) {
		SpringApplication.run(BibleGameApp.class, args);
	}

	// @Override
	// public void run(String... arg0) throws Exception {
	// System.out.println("START GAME");
	// gameService.start();
	// System.out.println("END GAME");
	// }
}
