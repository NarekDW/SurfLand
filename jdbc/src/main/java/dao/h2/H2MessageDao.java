package dao.h2;

import dao.MessageDao;
import lombok.SneakyThrows;
import model.Message;
import model.User;
import services.MessageDirection;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static status.MessageStatus.NOTREAD;
import static status.MessageStatus.READ;

/**
 * 18.03.2017 by K.N.K
 */
public class H2MessageDao implements MessageDao {

    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2MessageDao(DataSource ds) {
        dataSource = ds;
    }


    // My connection pool
//    private static ConnectionPool dataSource;
//    public H2MessageDao(ConnectionPool ds){
//        dataSource = ds;
//    }


    @Override
    @SneakyThrows
    public void addMessage(Message message) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement newMessage = connection.prepareStatement(
                     "INSERT INTO Message(from_id, to_id, message, date, time, status)" +
                             " VALUES (?,?,?,?,?,?)")) {
            newMessage.setInt(1, message.getFromId());
            newMessage.setInt(2, message.getToId());
            newMessage.setString(3, message.getMessage());
            newMessage.setDate(4, message.getSqlDate());
            newMessage.setTime(5, message.getSqlTime());
            newMessage.setInt(6, message.getStatus());
            newMessage.executeUpdate();
        }
    }


    @Override
    @SneakyThrows
    public List<Message> getMessages(User userTo, User userFrom) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement userMessages = connection.createStatement();
             ResultSet resultSet = userMessages.executeQuery(
                     "SELECT id, from_id, to_id, message, date, time, status FROM Message " +
                             "WHERE (from_id = " + userFrom.getId() + " OR to_id = " + userFrom.getId() + ")" +
                             " AND (from_id = " + userTo.getId() + " OR to_id = " + userTo.getId() + ")");
             PreparedStatement updateStatus = connection.prepareStatement(
                     "UPDATE Message SET status = (?) WHERE id = (?)")) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int fromId = resultSet.getInt("from_id");
                int toId = resultSet.getInt("to_id");
                String message = resultSet.getString("message");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                int status = resultSet.getInt("status");

                messages.add(new Message(id, fromId, toId, message, date, time, status));

                if (userFrom.getId() == toId && status == NOTREAD) {
                    updateStatus.setInt(1, READ);
                    updateStatus.setInt(2, id);
                    updateStatus.executeUpdate();
                }
            }
        }
        return messages;
    }


    @Override
    @SneakyThrows
    public List<MessageDirection> getContacts(User currentUser) {
        List<MessageDirection> contacts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement userContacts = connection.createStatement();
             ResultSet resultSet = userContacts.executeQuery(
                     "SELECT from_id, to_id, status FROM Message " +
                             "WHERE (from_id = " + currentUser.getId() +
                             " OR to_id = " + currentUser.getId() + ")")) {
            while (resultSet.next()) {
                int fromId = resultSet.getInt("from_id");
                int toId = resultSet.getInt("to_id");
                int status = resultSet.getInt("status");

                MessageDirection messageDirection = new MessageDirection(fromId, toId);

                if (status == 2) {
                    if (contacts.contains(messageDirection))
                        contacts.remove(messageDirection);
                    contacts.add(messageDirection.setCount(0)); // Если сообщение прочитано count = 0
                } else {
                    if (contacts.contains(messageDirection)) {
                        int indexOf = contacts.indexOf(messageDirection);
                        int newCount = contacts.get(indexOf).getCount() + 1; // Если есть непрочитанное сообщение до теккущего момента count увеличиваем на 1
                        contacts.remove(indexOf);
                        contacts.add(messageDirection.setCount(newCount));
                    } else {
                        contacts.add(messageDirection.setCount(1)); //Если донное сообщение не прочитанно и до этого непрочитанных не было, count = 1
                    }
                }
            }
        }
        return contacts;
    }

    @Override
    @SneakyThrows
    public int getMessagesCount(User user) {
        int count = 0;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT status FROM Message " +
                     "WHERE to_id = " + user.getId() + " AND status = " + NOTREAD)
        ) {
            while (resultSet.next())
                count++;
        }
        return count;
    }
}
