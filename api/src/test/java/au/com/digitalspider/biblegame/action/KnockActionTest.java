package au.com.digitalspider.biblegame.action;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import au.com.digitalspider.biblegame.service.ActionService;

@RunWith(value = MockitoJUnitRunner.class)
public class KnockActionTest {
	private KnockAction action;
	@Mock
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
