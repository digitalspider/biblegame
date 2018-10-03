package au.com.digitalspider.biblegame.action;

import java.util.List;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.Item;
import au.com.digitalspider.biblegame.model.State;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;
import au.com.digitalspider.biblegame.service.LoggingService;
import au.com.digitalspider.biblegame.service.UserService;

public class BuyAction extends ActionBase {

	private UserService userService;
	private LoggingService loggingService;
	private ActionService actionService;

	public BuyAction(ActionService actionService) {
		super(ActionMain.BUY.name());
		this.actionService = actionService;
		loggingService = actionService.getLoggingService();
	}

	public BuyAction(ActionService actionService, Item item) {
		this(actionService);
		this.name = item.name();
		this.actionKey = item.getActionKey();
		this.actionUrl = "/action/buy/" + item.name().toLowerCase();
		this.helpMessage = item.getDescription();
		this.tooltip = item.getDescription();
	}

	@Override
	public Action execute(User user, String itemInput) {
		return execute(user, itemInput, 1);
	}

	public Action execute(User user, String itemInput, int amount) {
		user.setState(State.SHOP);
		success = true;
		if (amount < 1) {
			success = false;
			postMessage = "The merchants are not happy with you!";
			loggingService.log(user, postMessage);
			completed = true;
			return this;
		}
		if (itemInput != null) {
			itemInput = itemInput.toLowerCase().trim();
			if (itemInput.equals("q") || itemInput.equals("quit") || itemInput.equals("stop")
					|| itemInput.equals("exit") || itemInput.equals("n") || itemInput.equals("nothing")) {
				postMessage = user.getDisplayName() + " finishes buying things";
				loggingService.log(user, postMessage);
				completed = true;
				return this;
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
					postMessage = "You cannot afford any " + itemName + "!";
				} else if (currentStock > user.getLevel()) {
					success = false;
					postMessage = "You already have " + itemName + ". No need for more!";
				} else {
					postMessage = "You buy some " + itemName + ".";
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
				postMessage = "Scrolls are currently not on sale!";
				break;
			case HELP:
				postMessage = item.getDescription();
				break;
			default:
				success = false;
				postMessage = "The merchants are not happy with you!";
			}
			loggingService.log(user, postMessage);
			return this;
		}
		loggingService.log(user, postMessage);
		return this;
	}

	@Override
	public String getActionUrl() {
		return super.getActionUrl() + "/buy/";
	}

	@Override
	public void init(User user) {
		preMessage = "What would you like to buy?";
		actions.clear();
		success = true;
		for (Item actionItem : Item.values()) {
			actions.add(new BuyAction(actionService, actionItem));
		}
	}

	@Override
	public List<Action> getActions() {
		return actions;
	}
}
