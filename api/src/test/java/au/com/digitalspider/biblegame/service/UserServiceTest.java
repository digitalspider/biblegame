package au.com.digitalspider.biblegame.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import au.com.digitalspider.biblegame.model.User;

@RunWith(value = MockitoJUnitRunner.class)
public class UserServiceTest {
	private UserService userService;

	@Before
	public void setup() {
		userService = new UserService();
	}

	@Test
	public void testLevelXp() {
		User user = new User();
		Assert.assertEquals(6, userService.getLevelXp(0));
		Assert.assertEquals(12, userService.getLevelXp(1));
		Assert.assertEquals(18, userService.getLevelXp(2));
		Assert.assertEquals(30, userService.getLevelXp(3));
		Assert.assertEquals(78, userService.getLevelXp(5));
		Assert.assertEquals(864, userService.getLevelXp(10));
		Assert.assertEquals(106266, userService.getLevelXp(20));
	}

	@Test
	public void testLevelDiff() {
		User user = new User();
		user.setLevel(1);
		User player = new User();
		player.setLevel(5);
		Assert.assertEquals(-4, userService.getLevelDiff(user, player));
		Assert.assertEquals(4, userService.getLevelDiff(player, user));
		player.setLevel(1);
		Assert.assertEquals(0, userService.getLevelDiff(user, player));
		Assert.assertEquals(0, userService.getLevelDiff(player, user));
		player.setLevel(0);
		Assert.assertEquals(1, userService.getLevelDiff(user, player));
		Assert.assertEquals(-1, userService.getLevelDiff(player, user));
	}

	@Test
	public void testLevelDiffAbs() {
		User user = new User();
		user.setLevel(1);
		User player = new User();
		player.setLevel(5);
		Assert.assertEquals(4, userService.getLevelDiffAbs(user, player));
		Assert.assertEquals(4, userService.getLevelDiffAbs(player, user));
		player.setLevel(1);
		Assert.assertEquals(0, userService.getLevelDiffAbs(user, player));
		Assert.assertEquals(0, userService.getLevelDiffAbs(player, user));
		player.setLevel(0);
		Assert.assertEquals(1, userService.getLevelDiffAbs(user, player));
		Assert.assertEquals(1, userService.getLevelDiffAbs(player, user));
	}

	@Test
	public void testLostLocks() {
		User user = new User();
		user.setLevel(15);
		User player = new User();
		player.setLevel(1);
		int locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 15);
		locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 15);
		locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 15);
		player.setLevel(5);
		locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 10);
		locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 10);
		locksLost = userService.getLocksLost(user, player);
		Assert.assertTrue(locksLost <= 10);
		user.setLevel(1);
		player.setLevel(1);
		Assert.assertTrue(userService.getLocksLost(user, player) <= 1);
		Assert.assertTrue(userService.getLocksLost(user, player) <= 1);
		Assert.assertTrue(userService.getLocksLost(user, player) <= 1);
		user.setLevel(1);
		player.setLevel(3);
		Assert.assertTrue(userService.getLocksLost(user, player) <= 1);
		Assert.assertTrue(userService.getLocksLost(user, player) <= 1);
	}

}
