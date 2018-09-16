package au.com.digitalspider.biblegame.service;

import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.User;

@Service
public class LoggingService {

	public void log(User user, String message) {
		System.out.println(message);
	}

	public void logError(User user, String message) {
		System.err.println(message);
	}
}
