package au.com.digitalspider.biblegame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(value = MockitoJUnitRunner.class)
public class BibleGameAppTest {

	private BibleGameCli app;

	@Before
	public void setup() {
		app = new BibleGameCli();
	}

	@Test
	public void testApp() {
		Assert.assertNotNull(app);
	}
}
