package dao.h2;

import dao.FriendsDao;
import lombok.SneakyThrows;
import model.User;
import services.FollowersFriendsCount;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 17.03.2017 by K.N.K
 */
public class H2FriendsDao implements FriendsDao {

    private static final int NONE = 0;
    private static final int FOLLOW = 1;
    private static final int FRIEND = 2;
    private static final int FOLLOWER = 3;

    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2FriendsDao(DataSource ds) {
        dataSource = ds;
    }

    // My connection pool
//    private static ConnectionPool dataSource;
//    public H2FriendsDao(ConnectionPool ds){
//        dataSource = ds;
//    }


    /**
     * status 2 -is friend;
     * status 1 - is follower;
     */
    @Override
    @SneakyThrows
    public void addFriend(int fromId, int toId) {
        boolean added = false;
        try (Connection connection = dataSource.getConnection();
             Statement checkUser = connection.createStatement();
             ResultSet resultSet = checkUser.executeQuery(
                     "SELECT id, first_user_id, second_user_id, status FROM Friends");
             PreparedStatement addFriend = connection.prepareStatement(
                     "UPDATE Friends SET status = (?) WHERE first_user_id = (?) AND second_user_id = (?)");
             PreparedStatement query = connection.prepareStatement(
                     "INSERT INTO Friends(first_user_id, second_user_id, status) VALUES (?,?,?)")) {
            while (resultSet.next()) {
                int from = resultSet.getInt("first_user_id");
                int to = resultSet.getInt("second_user_id");
                if (from == toId && to == fromId) {
                    addFriend.setInt(1, FRIEND);
                    addFriend.setInt(2, from);
                    addFriend.setInt(3, to);
                    addFriend.executeUpdate();
                    added = true;
                }
            }
            if (isUserRegistered(toId)) {
                if (!added) {
                    query.setInt(1, fromId);
                    query.setInt(2, toId);
                    query.setInt(3, FOLLOW);
                    query.executeUpdate();
                }
            }
        }
    }


    /**
     * @param idFrom - тот кто хочет удалить
     * @param idTo   - тот кого хотят удалить
     */
    @Override
    @SneakyThrows
    public void removeFriend(int idFrom, int idTo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement findFriends = connection.prepareStatement(
                     "SELECT first_user_id, second_user_id, status " +
                             "FROM Friends WHERE first_user_id = (?) AND second_user_id = (?) AND status = (?)")) {
            boolean deleted;

            // delete follow
            findFriends.setInt(1, idFrom);
            findFriends.setInt(2, idTo);
            findFriends.setInt(3, FOLLOW);
            deleted = deleteRow(idFrom, idTo, connection, findFriends);

            // delete follower
            if (!deleted) {
                findFriends.setInt(1, idTo);
                findFriends.setInt(2, idFrom);
                findFriends.setInt(3, FOLLOW);
                deleted = deleteRow(idTo, idFrom, connection, findFriends);
            }

            // delete friend
            if (!deleted) {

                // when fromId = current user id
                findFriends.setInt(1, idFrom);
                findFriends.setInt(2, idTo);
                findFriends.setInt(3, FRIEND);
                try (ResultSet resultSet = findFriends.executeQuery();
                     PreparedStatement updateAndChange = connection.prepareStatement(
                             "UPDATE Friends SET status = (?), first_user_id = (?), second_user_id = (?)" +
                                     " WHERE first_user_id = (?) AND second_user_id = (?)"
                     )) {
                    if (resultSet.next()) {
                        updateAndChange.setInt(1, FOLLOW);
                        updateAndChange.setInt(2, idTo);
                        updateAndChange.setInt(3, idFrom);
                        updateAndChange.setInt(4, idFrom);
                        updateAndChange.setInt(5, idTo);
                        updateAndChange.executeUpdate();
                        deleted = true;
                    }
                }


                // when toId = current user id
                if (!deleted) {
                    findFriends.setInt(1, idTo);
                    findFriends.setInt(2, idFrom);
                    findFriends.setInt(3, FRIEND);
                    try (ResultSet resultSet = findFriends.executeQuery();
                         PreparedStatement updateStatus = connection.prepareStatement(
                                 "UPDATE Friends SET status = (?) WHERE first_user_id = (?) AND second_user_id = (?)"
                         )) {
                        if (resultSet.next()) {
                            updateStatus.setInt(1, FOLLOW);
                            updateStatus.setInt(2, idTo);
                            updateStatus.setInt(3, idFrom);
                            updateStatus.executeUpdate();
                        }
                    }
                }
            } // end of delete friend
        }
    }


    /**
     * @param firstId  - id нынешнего пользователя пользователя.
     * @param secondId - id пользователя, на чей странице мы находимся.
     */
    @Override
    @SneakyThrows
    public int getStatus(int firstId, int secondId) {
        int status = NONE;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statusStatementFirst = connection.prepareStatement(
                     "SELECT first_user_id, second_user_id, status " +
                             "FROM Friends WHERE first_user_id = (?) AND second_user_id = (?)")) {

            /* Случай когда firstId первый послал запрос на добавление в друзья,
            * status из базы соответсвует отношению firstId -> secondId*/
            statusStatementFirst.setInt(1, firstId);
            statusStatementFirst.setInt(2, secondId);

            try (ResultSet resultSetFrom = statusStatementFirst.executeQuery()) {
                if (resultSetFrom.next()) {
                    status = resultSetFrom.getInt("status");
                    return status;
                }
            }
            /* Случай когда secondId первый послал запрос на добавление в друзья,
            * отношению firstId -> secondId соответсвуют: FOLLOW -> FOLLOWER; NOT FRIEND -> NONE*/
            statusStatementFirst.setInt(1, secondId);
            statusStatementFirst.setInt(2, firstId);
            ResultSet resultSetTo = statusStatementFirst.executeQuery();
            if (resultSetTo.next()) {
                status = resultSetTo.getInt("status");
                if (status == FOLLOW)
                    return FOLLOWER;
                if (status != FRIEND)
                    return NONE;
            }
        }
        return status;
    }


    @Override
    @SneakyThrows
    public List<Integer> getAllFriends(int userId) {
        List<Integer> allFriendsId = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement allFriends = connection.createStatement();
             ResultSet resultSet = allFriends.executeQuery(
                     "SELECT first_user_id, second_user_id, status " +
                             "FROM Friends WHERE first_user_id = " + userId +
                             " OR second_user_id = " + userId)) {
            while (resultSet.next()) {
                if (resultSet.getInt("status") == FRIEND) {
                    if (resultSet.getInt("first_user_id") == userId)
                        allFriendsId.add(resultSet.getInt("second_user_id"));
                    if (resultSet.getInt("second_user_id") == userId)
                        allFriendsId.add(resultSet.getInt("first_user_id"));
                }
            }
        }
        return allFriendsId;
    }


    @Override
    @SneakyThrows
    public List<Integer> getAllFollow(int userId) {
        List<Integer> allFollowsId = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement follow = connection.createStatement();
             ResultSet resultSet = follow.executeQuery(
                     "SELECT second_user_id, status " +
                             "FROM Friends WHERE first_user_id = " + userId)) {
            while (resultSet.next())
                if (resultSet.getInt("status") == FOLLOW)
                    allFollowsId.add(resultSet.getInt("second_user_id"));
        }
        return allFollowsId;
    }


    @Override
    @SneakyThrows
    public List<Integer> getAllFollowers(int userId) {
        List<Integer> allFollowersId = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement follow = connection.createStatement();
             ResultSet resultSet = follow.executeQuery(
                     "SELECT first_user_id, status " +
                             "FROM Friends WHERE second_user_id = " + userId)) {
            while (resultSet.next())
                if (resultSet.getInt("status") == FOLLOW)
                    allFollowersId.add(resultSet.getInt("first_user_id"));
        }
        return allFollowersId;
    }


    @Override
    @SneakyThrows
    public List<Integer> getAllFollowAndFriends(int userId) {
        List<Integer> allFollowsAndFriendsId = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT first_user_id, second_user_id " +
                             "FROM Friends WHERE (first_user_id = " + userId +
                             " AND status = " + FOLLOW + ") OR (first_user_id = " + userId +
                             " OR second_user_id = " + userId +
                             " AND status = " + FRIEND + " )")
        ) {


            while (resultSet.next()) {
                if (resultSet.getInt("first_user_id") == userId)
                    allFollowsAndFriendsId.add(resultSet.getInt("second_user_id"));
                else
                    allFollowsAndFriendsId.add(resultSet.getInt("first_user_id"));
            }
        }
        allFollowsAndFriendsId.add(userId); // Учитываем новости текущего пользователя тоже
        return allFollowsAndFriendsId;
    }


    @Override
    @SneakyThrows
    public FollowersFriendsCount getFollowersCount(User user) {
        int followersCount = 0;
        int friendsCount = 0;
        try (Connection connection = dataSource.getConnection();
             Statement followers = connection.createStatement();
             ResultSet followersSet = followers.executeQuery(
                     "SELECT second_user_id FROM Friends WHERE second_user_id = " + user.getId() + " AND status = " + FOLLOW);
             Statement friends = connection.createStatement();
             ResultSet friendsSet = friends.executeQuery(
                     "SELECT status FROM Friends WHERE (first_user_id = " + user.getId() + " AND status = " + FRIEND + ")" +
                             " OR (second_user_id = " + user.getId() + " AND status = " + FRIEND + ")")) {
            while (followersSet.next())
                followersCount++;
            while (friendsSet.next())
                friendsCount++;
        }
        return new FollowersFriendsCount(followersCount, friendsCount);
    }



    @SneakyThrows
    private boolean deleteRow(int firstUser, int secondUser, Connection connection, PreparedStatement findFriends) {
        try (ResultSet resultSet = findFriends.executeQuery();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM Friends WHERE first_user_id = (?) AND second_user_id = (?) AND status = (?)"
             )) {
            if (resultSet.next()) {
                deleteStatement.setInt(1, firstUser);
                deleteStatement.setInt(2, secondUser);
                deleteStatement.setInt(3, FOLLOW);
                deleteStatement.executeUpdate();
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    private boolean isUserRegistered(int toId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkUser = connection.prepareStatement(
                     "SELECT id FROM User WHERE id = (?)")) {
            checkUser.setInt(1, toId);
            ResultSet resultSet = checkUser.executeQuery();
            return resultSet.next();
        }
    }

}
