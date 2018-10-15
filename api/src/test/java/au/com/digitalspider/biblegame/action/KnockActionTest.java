package au.com.digitalspider.biblegame.action;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.digitalspider.biblegame.service.ActionService;

@RunWith(value = MockitoJUnitRunner.class)
public class KnockActionTest {
	private KnockAction action;
	@Autowired
	private ActionService actionService;

	@Before
	public void setup() {
		action = new KnockAction(actionService);
	}

	@Test
	public void testAction() {
		Assert.assertNotNull(action);
	}
}
