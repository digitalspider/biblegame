package au.com.digitalspider.biblegame.io;

import au.com.digitalspider.biblegame.model.User;

public class ActionResponse {

	private boolean success;
	private User user;
	private String message;
	private String nextActionMessage;
	private String nextActionUrl;

	@Override
	public String toString() {
		return "ActionResponse [user=" + user + ", message=" + message + ", nextActionUrl=" + nextActionUrl + "]";
	}

	public ActionResponse() {

	}

	public ActionResponse(boolean success, User user, String message) {
		this.success = success;
		this.user = user;
		this.message = message;
	}

	public ActionResponse(boolean success, User user, String message, String nextActioMessage, String nextActionUrl) {
		this.success = success;
		this.user = user;
		this.message = message;
		this.nextActionMessage = nextActioMessage;
		this.nextActionUrl = nextActionUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNextActionUrl() {
		return nextActionUrl;
	}

	public void setNextActionUrl(String nextActionUrl) {
		this.nextActionUrl = nextActionUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getNextActionMessage() {
		return nextActionMessage;
	}

	public void setNextActionMessage(String nextActionMessage) {
		this.nextActionMessage = nextActionMessage;
	}

}
