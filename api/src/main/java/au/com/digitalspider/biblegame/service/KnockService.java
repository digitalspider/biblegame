package au.com.digitalspider.biblegame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.ActionResponse;
import au.com.digitalspider.biblegame.model.Item;
import au.com.digitalspider.biblegame.model.User;

@Service
public class KnockService {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingService loggingService;

	public ActionResponse doKnock(User user, String itemInput, String action, Integer amount) {
		String question = "What would you like to buy?";
		String nextUrl = "/buy/";
		boolean success = true;
		if (amount < 1) {
			success = false;
			String reply = "The merchants are not happy with you!";
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply, question, nextUrl);
		}
		String reply = null;
		if (itemInput != null) {
			itemInput = itemInput.toLowerCase().trim();
			if (itemInput.equals("q") || itemInput.equals("quit") || itemInput.equals("stop")
					|| itemInput.equals("exit") || itemInput.equals("n") || itemInput.equals("nothing")) {
				reply = user.getDisplayName() + " finishes buying things";
				loggingService.log(user, reply);
				return new ActionResponse(success, user, reply);
			}
			Item item = Item.parse(itemInput);
			if (item == null) {
				item = Item.HELP;
			}
			switch (item) {
			case TOOL:
			case LOCK:
			case SLAVE:
			case BOOK:
				int price = item.getPrice();
				String itemName = item.getPlural();
				int currentStock = 0;
				if (item == Item.TOOL) {
					currentStock = user.getTools();
				} else if (item == Item.LOCK) {
					currentStock = user.getLocks();
				} else if (item == Item.SLAVE) {
					currentStock = user.getSlaves();
				} else if (item == Item.BOOK) {
					currentStock = user.getBooks();
				}
				if (user.getRiches() < price) {
					success = false;
					reply = "You cannot afford any " + itemName + "!";
				} else if (currentStock > user.getLevel()) {
					success = false;
					reply = "You already have " + itemName + ". No need for more!";
				} else {
					reply = "You buy some " + itemName + ".";
					if (item == Item.TOOL) {
						user.setTools(currentStock + 1);
					} else if (item == Item.LOCK) {
						user.setLocks(currentStock + 1);
					} else if (item == Item.SLAVE) {
						user.setSlaves(currentStock + 1);
					} else if (item == Item.BOOK) {
						user.setBooks(currentStock + 1);
					}
					user.decreaseRiches(price);
					userService.save(user);
				}
				break;
			case SCROLL:
				success = false;
				reply = "Scrolls are currently not on sale!";
				break;
			case HELP:
				reply = item.getDescription();
				break;
			default:
				success = false;
				reply = "The merchants are not happy with you!";
			}
			loggingService.log(user, reply);
			return new ActionResponse(success, user, reply, question, nextUrl);
		}
		ActionResponse response = new ActionResponse(success, user, reply, question, nextUrl);
		loggingService.log(user, reply);
		return response;
	}
}
