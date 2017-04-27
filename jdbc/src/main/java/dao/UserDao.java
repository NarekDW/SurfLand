package dao;

import model.User;

import java.sql.SQLException;

/**
 * 07.03.2017
 *
 * Narek.
 */
public interface UserDao {
    User createUser(User user) throws SQLException;
    void update(User user) throws SQLException;
    void remove(User user) throws SQLException;
    boolean isEmailRegistered(String email);
}
