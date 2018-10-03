package au.com.digitalspider.biblegame.action;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;

public class HelpAction extends ActionBase {

	public HelpAction() {
		super(ActionMain.HELP.name());
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
