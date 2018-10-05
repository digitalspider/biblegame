package au.com.digitalspider.biblegame.action;

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
		userService = actionService.getUserService();
	}

	public BuyAction(ActionService actionService, Item item) {
		this(actionService);
		this.name = item.name();
		this.actionKey = item.getActionKey();
		this.actionUrl = actionUrl + item.name().toLowerCase();
		this.helpMessage = item.getDescription();
		this.tooltip = item.getDescription();
	}

	@Override
	public Action execute(User user, String itemInput) {
		return execute(user, itemInput, 1);
	}

	public Action execute(User user, String itemInput, int amount) {
		init(user);
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
					|| itemInput.equals("exit")) {
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
			case NOTHING:
				postMessage = user.getDisplayName() + " finishes buying things";
				loggingService.log(user, postMessage);
				completed = true;
				return this;
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
					postMessage = "You cannot afford any " + itemName + "!. Cost=" + price + ". Riches="
							+ user.getRiches();
				} else if (currentStock > user.getLevel()) {
					success = false;
					postMessage = "You already have " + currentStock + " " + itemName + ". Level up to get for more!";
				} else {
					currentStock++;
					if (item == Item.TOOL) {
						user.setTools(currentStock);
					} else if (item == Item.LOCK) {
						user.setLocks(currentStock);
					} else if (item == Item.SLAVE) {
						user.setSlaves(currentStock);
					} else if (item == Item.BOOK) {
						user.setBooks(currentStock);
					}
					user.decreaseRiches(price);
					userService.save(user);
					init(user);
					postMessage = "You buy some " + itemName + ". " + itemName + "=" + currentStock + ". Riches="
							+ user.getRiches();
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
	public void init(User user) {
		user.setState(State.SHOP);
		preMessage = "What would you like to buy?";
		actions.clear();
		success = true;
		completed = false;
		for (Item actionItem : Item.values()) {
			if (!actionItem.equals(Item.HELP)) {
				BuyAction action = new BuyAction(actionService, actionItem);
				if (actionItem.getPrice() > user.getRiches()) {
					action.setEnabled(false);
					action.setTooltip("You cannot afford this item");
				}
				actions.add(action);
			}
		}
	}
}
