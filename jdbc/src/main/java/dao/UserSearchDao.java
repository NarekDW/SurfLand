package dao;

import model.Address;
import model.User;

import java.util.List;
import java.util.Optional;

/**
 * 13.03.2017 by K.N.K
 */
public interface UserSearchDao {
    List<User> getAll();
    List<Address> geAllAddress();
    public List<User> getUserUniversal(String name, String nextTrip, String sex, String city,
                                       String country, String ageFrom, String ageTo, String sortType);

    default Optional<User> get(int id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findAny();
    }

    int isUserRegistered(String login, String password); // TODO: 09.03.2017 Переписать, чтобы возвращал объект пользователя
}
