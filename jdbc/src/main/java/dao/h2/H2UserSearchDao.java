package dao.h2;

import dao.UserSearchDao;
import lombok.SneakyThrows;
import model.Address;
import model.Sex;
import model.User;
import services.SearchType;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
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

    @Override
    @SneakyThrows
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

            while (resultSet.next()) {
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
                User user = new User(id, firstName, lastName, dateOfBirth, sex, nextTrip, email, password, address);
                users.add(user);
            }
        }
        return users;
    }

    @Deprecated
    @Override
    @SneakyThrows
    public List<Address> geAllAddress() {
        List<Address> all = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Address")) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                all.add(new Address(id, country, city));
            }
        }
        return all;
    }
    
    @Override
    public List<User> getUserUniversal(String name, String nextTrip, String sex, String city,
                                       String country, String ageFrom, String ageTo, String sortType) {

        Stream<User> userStream = getAll().stream();

        if (!country.isEmpty())
            userStream = userStream.filter(user -> user.getAddress().getCountry() != null)
                    .filter(user -> user.getAddress().getCountry().matches("(?u)(?i).*" + country + ".*"));
        if (!city.isEmpty())
            userStream = userStream.filter(user -> user.getAddress().getCity() != null)
                    .filter(user -> user.getAddress().getCity().matches("(?u)(?i).*" + city + ".*"));
        if (!sex.equalsIgnoreCase(String.valueOf(Sex.NONE)))
            userStream = userStream.filter(user -> user.getSex().equals(Sex.valueOf(sex.toUpperCase())));
        if (!nextTrip.isEmpty())
            userStream = userStream.filter(user -> user.getNextTrip() != null)
                    .filter(user -> user.getNextTrip().matches("(?u)(?i).*" + nextTrip + ".*"));
        if (!name.isEmpty())
            userStream = userStream.filter(user -> String.format("%s %s", user.getFirstName(), user.getLastName())
                    .matches("(?u)(?i).*" + name + ".*"));
        if (!ageFrom.isEmpty()) {
            int age = Integer.valueOf(ageFrom.trim());
            userStream = userStream.filter(user -> user.getDateOfBirth() != null)
                    .filter(user -> user.getAge() >= age);
        }
        if (!ageTo.isEmpty()) {
            int age = Integer.valueOf(ageTo.trim());
            userStream = userStream.filter(user -> user.getDateOfBirth() != null)
                    .filter(user -> user.getAge() <= age);
        }
        if (!sortType.isEmpty()) {
            SearchType type = SearchType.valueOf(sortType.toUpperCase());
            switch (type) {
                case NAMESORT:
                    userStream = userStream.sorted(Comparator.comparing(User::getFullName));
                    break;
                case AGESORT:
                    userStream = userStream.filter(user -> user.getDateOfBirth() != null)
                            .sorted(Comparator.comparingInt(User::getAge));
            }
        }
        return userStream.collect(Collectors.toList());
    }

    // Check Registration
    @Override
    public int isUserRegistered(String login, String password) {
        Optional<User> any = getAll().stream()
                .filter(user -> (user.getEmail().equals(login)
                        && user.getPasswordHash().equals(password))).findAny();

        return any.isPresent() ? any.get().getId() : -1;
    }
}
