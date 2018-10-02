package au.com.digitalspider.biblegame.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.digitalspider.biblegame.model.ActionMain;
import au.com.digitalspider.biblegame.model.User;

@Component
public class HelpAction extends ActionBase {

	@Autowired
	private RootAction rootAction;

	public HelpAction() {
		setName("Help");
	}

	@Override
	public Action execute(User user, String input) {
		rootAction.setPostMessage(ActionMain.getHelpMessage());
		return rootAction;
	}

	@Override
	public String getActionUrl() {
		return super.getActionUrl() + "/help";
	}

	@Override
	public void init(User user) {

	}
}
