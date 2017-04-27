package dao.h2;

import dao.UserSearchDao;
import lombok.SneakyThrows;
import model.Address;
import model.Sex;
import model.User;
import services.SearchType;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 13.03.2017 by K.N.K
 */
public class H2UserSearchDao implements UserSearchDao {

    private static final String SELECT_ALL_SQL = "SELECT id, first_name, last_name," +
            " date_of_birth, sex_id, next_trip, email, password, address_id FROM User";


    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2UserSearchDao(DataSource ds) {
        dataSource = ds;
    }


    // My connection pool
//    private static ConnectionPool dataSource;
//    public H2UserSearchDao(ConnectionPool ds){
//        dataSource = ds;
//    }



    @Override
    @SneakyThrows
    public User get(int userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getUser = connection.prepareStatement(
                     "SELECT id, first_name, last_name, date_of_birth, sex_id," +
                             " next_trip, email, password, address_id " +
                             "FROM User WHERE id = (?)")) {
            getUser.setInt(1, userId);
            try (ResultSet resultSet = getUser.executeQuery()) {
                if (resultSet.next()) {
                    return createUser(resultSet, connection);
                }
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {
            while (resultSet.next()) {
                users.add(createUser(resultSet, connection));
            }
        }
        return users;
    }

    @Override
    public List<User> getUserUniversal(String name, String nextTrip, String sex, String city,
                                       String country, String ageFrom, String ageTo, String sortType) {

        Stream<User> userStream = getAll().stream();

        if (!country.isEmpty())
            userStream = userStream.parallel()
                    .filter(user -> user.getAddress().getCountry() != null)
                    .filter(user -> user.getAddress().getCountry().matches("(?u)(?i).*" + country + ".*"));
        if (!city.isEmpty())
            userStream = userStream.parallel()
                    .filter(user -> user.getAddress().getCity() != null)
                    .filter(user -> user.getAddress().getCity().matches("(?u)(?i).*" + city + ".*"));
        if (!sex.equalsIgnoreCase(String.valueOf(Sex.NONE)))
            userStream = userStream.parallel()
                    .filter(user -> user.getSex().equals(Sex.valueOf(sex.toUpperCase())));
        if (!nextTrip.isEmpty())
            userStream = userStream.parallel()
                    .filter(user -> user.getNextTrip() != null)
                    .filter(user -> user.getNextTrip().matches("(?u)(?i).*" + nextTrip + ".*"));
        if (!name.isEmpty())
            userStream = userStream.parallel()
                    .filter(user -> String.format("%s %s", user.getFirstName(), user.getLastName())
                    .matches("(?u)(?i).*" + name + ".*"));
        if (!ageFrom.isEmpty()) {
            int age = Integer.valueOf(ageFrom.trim());
            userStream = userStream.parallel()
                    .filter(user -> user.getDateOfBirth() != null)
                    .filter(user -> user.getAge() >= age);
        }
        if (!ageTo.isEmpty()) {
            int age = Integer.valueOf(ageTo.trim());
            userStream = userStream.parallel()
                    .filter(user -> user.getDateOfBirth() != null)
                    .filter(user -> user.getAge() <= age);
        }
        if (!sortType.isEmpty()) {
            SearchType type = SearchType.valueOf(sortType.toUpperCase());
            switch (type) {
                case NAMESORT:
                    userStream = userStream.sequential()
                            .sorted(Comparator.comparing(User::getFullName));
                    break;
                case AGESORT:
                    userStream = userStream.sequential()
                            .filter(user -> user.getDateOfBirth() != null)
                            .sorted(Comparator.comparingInt(User::getAge));
            }
        }
        return userStream.collect(Collectors.toList());
    }

    // Check Registration
    @Override
    public User isUserRegistered(String email, String password) {
        Optional<User> any = getAll().stream()
                .filter(user -> (user.getEmail().equals(email)
                        && user.getPasswordHash().equals(password))).findAny();

        return any.isPresent() ? any.get() : null;
    }


    @SneakyThrows
    private User createUser(ResultSet resultSet, Connection connection) {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        LocalDate dateOfBirth = null;
        Date dob = resultSet.getDate("date_of_birth");
        if (dob != null) dateOfBirth = dob.toLocalDate();
        int sexId = resultSet.getInt("sex_id");
        String nextTrip = resultSet.getString("next_trip");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        Sex sex = null;
        try (Statement statement2 = connection.createStatement();
             ResultSet resultSet2 =
                     statement2.executeQuery("SELECT name FROM Sex WHERE id = " + sexId)) {
            if (resultSet2.next()) {
                sex = Sex.valueOf(resultSet2.getString("name").toUpperCase());
            }
        }
        int addressId = resultSet.getInt("address_id");
        Address address = new Address();
        address.setId(addressId);
        try (Statement statement3 = connection.createStatement();
             ResultSet resultSet3 =
                     statement3.executeQuery("SELECT country, city FROM Address WHERE id = " + addressId)) {
            if (resultSet3.next()) {
                address.setCountry(resultSet3.getString("country"));
                address.setCity(resultSet3.getString("city"));
            }
        }
        return new User(id, firstName, lastName, dateOfBirth, sex, nextTrip, email, password, address);
    }

}
