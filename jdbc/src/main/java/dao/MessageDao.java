package dao;

import model.Message;
import model.User;
import services.MessageDirection;

import java.util.List;

/**
 * 18.03.2017 by K.N.K
 */
public interface MessageDao {
    void addMessage(Message message);
    List<Message> getMessages(User userTo, User userFrom);
    List<MessageDirection> getContacts(User user);
    int getMessagesCount(User user); // Not read messages count.
}
