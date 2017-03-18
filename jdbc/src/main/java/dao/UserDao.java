package dao;

import model.User;

/**
 * 07.03.2017
 * <p>
 * Narek.
 */
public interface UserDao {
    User createUser(User user);
    void update(User user);
    void remove(User user);

    boolean isEmailRegistered(String email);
}
