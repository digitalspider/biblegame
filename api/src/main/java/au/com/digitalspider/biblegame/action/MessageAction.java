package au.com.digitalspider.biblegame.action;

import org.springframework.beans.factory.annotation.Autowired;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.MessageService;

public class MessageAction extends ActionBase {

	@Autowired
	private MessageService messageService;

	public MessageAction() {
		super(ActionMain.MESSAGE.name(), null);
		this.actionUrl = "/message/send/";
	}

	@Override
	public Action execute(User user, String input) {
		return messageService.doMessage(user, input);
	}

	@Override
	public void init(User user) {
		success = true;
	}
}
