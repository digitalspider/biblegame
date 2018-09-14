package au.com.digitalspider.biblegame.model;

import au.com.digitalspider.biblegame.model.base.BaseLongNamedEntity;

/**
 * The "name" will be the body of the message
 */
public class Message extends BaseLongNamedEntity<Message> {

	private User from;
	private User to;
}
