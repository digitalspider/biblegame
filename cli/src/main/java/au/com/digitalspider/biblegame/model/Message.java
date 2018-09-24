package au.com.digitalspider.biblegame.model;

import au.com.digitalspider.biblegame.io.SimpleUser;

public class Message {
	private long id;
	private String name;
	private SimpleUser from;
	private SimpleUser to;
	private String message;
	private boolean read;

	@Override
	public String toString() {
		return "[id=" + id + ", title=" + name + ", from=" + from + ", to=" + to + ", message=" + message + "]";
	}

	public SimpleUser getFrom() {
		return from;
	}

	public void setFrom(SimpleUser from) {
		this.from = from;
	}

	public SimpleUser getTo() {
		return to;
	}

	public void setTo(SimpleUser to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
