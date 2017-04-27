package dao;

import model.User;
import services.FollowersFriendsCount;

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
    List<Integer> getAllFollowAndFriends(int userId);
    FollowersFriendsCount getFollowersCount(User user);
}
