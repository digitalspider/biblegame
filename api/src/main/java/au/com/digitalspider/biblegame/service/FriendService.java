package au.com.digitalspider.biblegame.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.digitalspider.biblegame.io.SimpleUser;
import au.com.digitalspider.biblegame.model.Friends;
import au.com.digitalspider.biblegame.model.User;

@Service
public class FriendService {

	private static final Logger LOG = Logger.getLogger(FriendService.class);

	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;

	public void addFriendRequest(User user, User newFriend) {
		for (SimpleUser friend : user.getFriendRequests()) {
			if (friend.getName().equals(newFriend.getName())) {
				throw new IllegalArgumentException(
						"You already requested " + newFriend.getDisplayName() + " to be your friend");
			}
		}
		for (SimpleUser friend : user.getFriends()) {
			if (friend.getName().equals(newFriend.getName())) {
				throw new IllegalArgumentException(newFriend.getDisplayName() + " is already your friend");
			}
		}
		newFriend.getFriendList().add(new Friends(newFriend, user));
		userService.save(newFriend);
		userService.populateFriendLists(user);
		messageService.sendMessage(user, newFriend, "Friends", " would like to be friends?");
	}

	public void acceptFriend(User user, User friend) {
		if (friend == null) {
			return;
		}
		for (Friends friends : user.getFriendList()) {
			if (friends.getFriend().getId() == friend.getId()) {
				// Create reverse friend link
				Friends reverseFriendLink = new Friends(friend, user);
				friend.getFriendList().add(friends);
				Date acceptDate = new Date();
				friends.setAcceptedAt(acceptDate);
				reverseFriendLink.setAcceptedAt(acceptDate);
				saveAndUpdate(user, friend);
				break;
			}
		}
	}

	public void removeFriend(User user, User friend) {
		if (friend==null) {
			return ;
		}
		Friends reverseFriendLink = getReverseFriendLink(user, friend);
		if (reverseFriendLink != null) {
			friend.getFriendList().remove(reverseFriendLink);
		}
		for (Friends friends : user.getFriendList()) {
			if (friends.getFriend().getId() == friend.getId()) {
				user.getFriendList().remove(friends);
				break;
			}
		}
		saveAndUpdate(user, friend);
	}

	public void validateFriend(User user, User friend) {
		if (friend == null) {
			throw new IllegalArgumentException("null is not your friend");
		}
		for (Friends friends : user.getFriendList()) {
			if (friends.getFriend().getId() == friend.getId()) {
				if (friends.isAccepted()) {
					return;
				} else {
					throw new IllegalArgumentException(
							friend.getDisplayName() + " has not accepted your friend request");
				}
			}
		}
		throw new IllegalArgumentException(friend.getDisplayName() + " is not your friend");
	}

	private void saveAndUpdate(User user, User friend) {
		userService.save(friend);
		userService.save(user);
		userService.populateFriendLists(user);
	}

	private Friends getReverseFriendLink(User user, User friend) {
		Friends reverseFriendLink = null;
		for (Friends reverseFriends : friend.getFriendList()) {
			if (reverseFriends.getFriend().getId() == user.getId()) {
				reverseFriendLink = reverseFriends;
				break;
			}
		}
		return reverseFriendLink;
	}
}
