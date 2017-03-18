package dao;

import model.Friend;
import model.User;

import java.util.List;

/**
 * 17.03.2017 by K.N.K
 */
public interface FriendsDao {
    void addFriend(int idFrom, int idTo);
    void removeFriend(int idFrom, int idTo);
    int getStatus(int firstId, int secondId);
    List<Integer> getAllFriends(int userId);
    List<Integer> getAllFollow(int userId);
    List<Integer> getAllFollowers(int userId);

    List<Friend> geAllFriends();
    List<User> getFriends(User user);
}
