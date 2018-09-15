package au.com.digitalspider.biblegame.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.model.Action;
import au.com.digitalspider.biblegame.model.User;

@Service
public class ActionService {
	private static final Logger LOG = Logger.getLogger(ActionService.class);

	@Autowired
	private UserService userService;

	public Action get(String value) {
		return Action.parse(value);
	}

	public User doAction(User user, Action action) {
		try {
			switch (action) {
			case HELP:
				Action.printHelp();
				break;
			case STUDY:
				study(user);
				break;
			case WORK:
				work(user);
				break;
			case PRAY:
				pray(user);
				break;
			case BEG:
				beg(user);
				break;
			case STEAL:
				steal(user);
				break;
			case GIVE:
				give(user);
				break;
			case READ:
				read(user);
				break;
			case BUY:
				buy(user);
				break;
			case KNOCK:
				knock(user);
				break;
			case CHAT:
				chat(user);
				break;
			case DONATE:
				donate(user);
				break;
			case STATS:
				System.out.println(user.getStats());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	public void study(User user) {
		Action action = Action.STUDY;
		if (!user.hasStamina()) {
			System.err.println("You are to tired to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				user.decreaseStamina();
				user.addKnowledge();
				System.out.println(user + " " + action.getDescription() + " stamina=" + user.getStamina()
						+ ", knowledge=" + user.getKnowledge());
			}
		}
	}

	public void work(User user) {
		Action action = Action.WORK;
		if (!user.hasStamina()) {
			System.err.println("You are to tired to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				user.decreaseStamina();
				user.addRiches();
				user.addRiches(user.getTools()); // Additional riches for having tools
				System.out.println(user + " " + action.getDescription() + " stamina=" + user.getStamina()
						+ ", riches=" + user.getRiches());
			}
		}
	}

	public void steal(User user) {
		Action action = Action.STEAL;
		if (!user.hasStamina()) {
			System.err.println("You are to tired to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				user.decreaseStamina();
				user.decreaseLove(2);
				user.addRiches();
				System.out.println(user + " " + action.getDescription() + " stamina=" + user.getStamina()
						+ ", riches=" + user.getRiches());
			}
		}
	}

	public void give(User user) {
		Action action = Action.GIVE;
		if (!user.hasRiches()) {
			System.err.println("You are to poor to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				user.decreaseRiches();
				user.addLove();
				System.out.println(user + " " + action.getDescription() + " love=" + user.getLove()
						+ ", riches=" + user.getRiches());
			}
		}
	}

	public void beg(User user) {
		Action action = Action.BEG;
		userService.updateLocation(user, action.getLocation(), true);
		user.addRiches();
		System.out.println(user + " " + action.getDescription());
		try {
			int waitTime = (int) (Math.random() * 5); // between 0 and 5 seconds
			Thread.sleep(waitTime * 1000);
			if (waitTime <= 2) {
				user.decreaseCharacter(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("After much begging "+user+" recieves riches. riches=" + user.getRiches());
	}

	public void pray(User user) {
		Action action = Action.PRAY;
		if (!user.hasStamina()) {
			System.err.println("You are to tired to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				user.decreaseStamina();
				user.addCharacter();
				System.out.println(user + " " + action.getDescription() + " stamina=" + user.getStamina()
						+ ", character=" + user.getCharacter());
			}
		}
	}

	public void read(User user) {
		Action action = Action.READ;
		if (!user.hasStamina()) {
			System.err.println("You are to tired to " + action);
		} else {
			if (userService.updateLocation(user, action.getLocation())) {
				// TODO: Implement
				System.out.println(user + " " + action.getDescription());
			}
		}
	}

	public void buy(User user) {
		Action action = Action.BUY;
		if (userService.updateLocation(user, action.getLocation())) {
			if (!user.hasRiches()) {
				System.err.println("You are to poor to " + action);
			} else {
				// TODO: IMplement
				System.out.println(user + " " + action.getDescription());
			}
		}
	}

	public void chat(User user) {
		Action action = Action.CHAT;
		if (userService.updateLocation(user, action.getLocation())) {
			// TODO: Implement
			System.out.println(user + " " + action.getDescription());
		}
	}

	public void knock(User user) {
		Action action = Action.KNOCK;
		if (userService.updateLocation(user, action.getLocation())) {
			// TODO: Implement
			System.out.println(user + " " + action.getDescription());
		}
	}

	public void donate(User user) {
		Action action = Action.DONATE;
		// TODO: Implement
		System.out.println(user + " " + action.getDescription());
	}
}
