package dao.h2;

import dao.MessageDao;
import lombok.SneakyThrows;
import model.Message;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 18.03.2017 by K.N.K
 */
public class H2MessageDao implements MessageDao {

    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2MessageDao(DataSource ds) {
        dataSource = ds;
    }

    @Override
    @SneakyThrows
    public void addMessage(Message message) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement newMessage = connection.prepareStatement(
                     "INSERT INTO Message(first_user_id, second_user_id, message, date, time, status)" +
                             " VALUES (?,?,?,?,?,?)")) {
            newMessage.setInt(1, message.getFirstUserId());
            newMessage.setInt(2, message.getSecondUserId());
            newMessage.setString(3, message.getMessage());
            newMessage.setDate(4, message.getDate());
            newMessage.setTime(5, message.getTime());
            newMessage.setInt(6, message.getStatus());
            newMessage.executeUpdate();
        }
    }



    @SneakyThrows
    public List<Message> getAll(){
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Message")
        ){
            while (resultSet.next()){
                messages.add(new Message(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getTime(6),
                        resultSet.getInt(7)
                ));
            }
            return messages;
        }
    }
}
