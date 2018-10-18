package au.com.digitalspider.biblegame.model;

import org.apache.commons.lang3.StringUtils;

public enum Item {
	TOOL("tools", Item.DESCRIPTION_TOOL, Item.ACTION_KEY_TOOL, Item.GLYPHICON_TOOL, Item.COST_TOOL), 
	LOCK("locks", Item.DESCRIPTION_LOCK, Item.ACTION_KEY_LOCK, Item.GLYPHICON_LOCK, Item.COST_LOCK), 
	SLAVE("slaves", Item.DESCRIPTION_SLAVE, Item.ACTION_KEY_SLAVE, Item.GLYPHICON_SLAVE, Item.COST_SLAVE), 
	SCROLL("scrolls", Item.DESCRIPTION_SCROLL, Item.ACTION_KEY_SCROLL, Item.GLYPHICON_SCROLL, Item.COST_SCROLL), 
	BOOK("books", Item.DESCRIPTION_BOOK, Item.ACTION_KEY_BOOK, Item.GLYPHICON_BOOK, Item.COST_BOOK),
	NOTHING("nothing", Item.DESCRIPTION_NOTHING, Item.ACTION_KEY_NOTHING, Item.GLYPHICON_NOTHING, 0),
	HELP("h", Item.DESCRIPTION_HELP, Item.ACTION_KEY_HELP, Item.GLYPHICON_HELP, 0);

	private static final int COST_TOOL = 6;
	private static final int COST_LOCK = 6;
	private static final int COST_SLAVE = 15;
	private static final int COST_BOOK = 50;
	private static final int COST_SCROLL = 500;
	
	public static final String ACTION_KEY_TOOL = "t";
	public static final String ACTION_KEY_LOCK = "l";
	public static final String ACTION_KEY_SLAVE = "x";
	public static final String ACTION_KEY_SCROLL = "s";
	public static final String ACTION_KEY_BOOK = "b";
	public static final String ACTION_KEY_NOTHING = "n";
	public static final String ACTION_KEY_HELP = "?";
	
	public static final String GLYPHICON_TOOL = "glyphicon-wrench";
	public static final String GLYPHICON_LOCK = "glyphicon-lock";
	public static final String GLYPHICON_SLAVE = "glyphicon-user";
	public static final String GLYPHICON_SCROLL = "glyphicon-list-alt";
	public static final String GLYPHICON_BOOK = "glyphicon-book";
	public static final String GLYPHICON_NOTHING = "glyphicon-log-out";
	public static final String GLYPHICON_HELP = "glyphicon-question-sign";

	public static final String DESCRIPTION_TOOL = "tools will increase your riches while working in the field. Cost="+Item.COST_TOOL;
	public static final String DESCRIPTION_LOCK = "locks allow you to protect yourselves from thieves. Cost="+Item.COST_LOCK;
	public static final String DESCRIPTION_SLAVE = "slaves will earn you riches even while you are not working. Cost="+Item.COST_SLAVE;
	public static final String DESCRIPTION_SCROLL = "scrolls are collectible adventures. Cost="+Item.COST_SCROLL;
	public static final String DESCRIPTION_BOOK = "books will increase your knowledge while studying. Cost="+Item.COST_BOOK;
	public static final String DESCRIPTION_NOTHING = "Finish buying and leave the marketplace.";
	public static final String DESCRIPTION_HELP = "Merchants are selling: tools(t), locks(l), books(b), slaves(x), scrolls(s)\n"
			+ DESCRIPTION_TOOL + "\n" + DESCRIPTION_LOCK + "\n" + DESCRIPTION_BOOK + "\n" + DESCRIPTION_SLAVE + "\n"
			+ DESCRIPTION_SCROLL + "\nPress 'q' to exit buy menu\n";
	
	private String plural;
	private String description;
	private String actionKey;
	private String glyphicon;
	private int price;
	
	private Item(String plural, String description, String actionKey, String glyphicon, int price) {
		this.plural = plural;
		this.description = description;
		this.actionKey = actionKey;
		this.glyphicon = glyphicon;
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	public String getActionKey() {
		return actionKey;
	}

	public static Item parse(String itemName) {
		if (itemName == null) {
			throw new IllegalArgumentException("Item not valid: " + itemName);
		}
		Item item = null;
		if (itemName.trim().length() == 0) {
			item = Item.HELP;
		} else if (itemName.length() == 1) {
			item = Item.parseByKey(itemName.toLowerCase());
		} else {
			item = Item.parseByName(itemName);
		}
		if (item == null) {
			throw new IllegalArgumentException("Item not valid: " + itemName);
		}
		return item;
	}

	public static Item parseByName(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.toUpperCase();
			for (Item action : Item.values()) {
				if (action.name().equals(value) || action.plural.toUpperCase().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static Item parseByKey(String value) {
		if (StringUtils.isNotBlank(value)) {
			for (Item action : Item.values()) {
				if (action.getActionKey().equals(value)) {
					return action;
				}
			}
		}
		return null;
	}

	public static String getHelpMessage() {
		StringBuffer result = new StringBuffer();
		result.append("List of items:\n");
		for (Item item : Item.values()) {
			result.append(item.name().toLowerCase() + " (" + item.getActionKey() + "), desc=" + item.getDescription()
					+ ", price=" + item.getPrice() + "\n");
		}
		return result.toString();
	}

	public static String getHelpMessageAsJson() {
		StringBuffer result = new StringBuffer();
		result.append("{[\n");
		for (Item item : Item.values()) {
			result.append("{name:'" + item.name().toLowerCase() + "', key: '" + item.getActionKey() + "', desc: '"
					+ item.getDescription() + "', price:'" + item.getPrice() + "'},\n");
		}
		result.append("]}\n");
		return result.toString();
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public String getGlyphicon() {
		return glyphicon;
	}

}
