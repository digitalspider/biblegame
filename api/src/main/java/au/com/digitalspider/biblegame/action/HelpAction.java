package au.com.digitalspider.biblegame.action;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;
import au.com.digitalspider.biblegame.service.ActionService;

public class HelpAction extends ActionBase {

	public HelpAction(ActionService actionService) {
		super(ActionMain.HELP.name(), actionService);
	}

	@Override
	public Action execute(User user, String input) {
		postMessage = ActionMain.getHelpMessage();
		return this;
	}

	@Override
	public void init(User user) {
		success = true;
	}
}
