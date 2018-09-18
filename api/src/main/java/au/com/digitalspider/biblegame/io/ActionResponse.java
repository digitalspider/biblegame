package au.com.digitalspider.biblegame.io;

import au.com.digitalspider.biblegame.model.User;

public class ActionResponse {

	private boolean success;
	private User user;
	private String messsage;
	private String nextActionMessage;
	private String nextActionUrl;

	@Override
	public String toString() {
		return "ActionResponse [user=" + user + ", messsage=" + messsage + ", nextActionUrl=" + nextActionUrl + "]";
	}

	public ActionResponse(boolean success, User user, String messsage) {
		this.success = success;
		this.user = user;
		this.messsage = messsage;
	}

	public ActionResponse(boolean success, User user, String messsage, String nextActioMessage, String nextActionUrl) {
		this.success = success;
		this.user = user;
		this.messsage = messsage;
		this.nextActionMessage = nextActioMessage;
		this.nextActionUrl = nextActionUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
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
