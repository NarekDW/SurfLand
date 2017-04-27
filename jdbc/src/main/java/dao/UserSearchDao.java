package dao;

import model.User;

import java.util.List;

/**
 * 13.03.2017 by K.N.K
 */
public interface UserSearchDao {
    User get(int id);
    List<User> getAll();
    List<User> getUserUniversal(String name, String nextTrip, String sex, String city,
                                       String country, String ageFrom, String ageTo, String sortType);
    User isUserRegistered(String login, String password);
}