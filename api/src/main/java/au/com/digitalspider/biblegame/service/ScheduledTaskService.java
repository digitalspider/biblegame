package au.com.digitalspider.biblegame.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

	private static final Logger LOG = Logger.getLogger(ScheduledTaskService.class);

	@Autowired
	private UserService userService;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 3600000)
	public void hourlyTasks() {
		LOG.info("Running hourlyTasks at " + dateFormat.format(new Date()));
		userService.processInactiveUsers();
	}
}
