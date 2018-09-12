package au.com.digitalspider.biblegame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(value = MockitoJUnitRunner.class)
public class BibleGameAppTest {

	private BibleGameApp app;

	@Before
	public void setup() {
		app = new BibleGameApp();
	}

	@Test
	public void testApp() {
		Assert.assertNotNull(app);
	}
}
