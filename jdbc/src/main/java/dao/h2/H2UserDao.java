package dao.h2;

import dao.UserDao;
import lombok.SneakyThrows;
import model.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 07.03.2017 by K.N.K
 */
public class H2UserDao implements UserDao {

    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2UserDao(DataSource ds) {
        dataSource = ds;
    }


    @Override
    @SneakyThrows
    public User createUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(
                     "INSERT INTO User(first_name, last_name, date_of_birth, sex_id, next_trip," +
                             " email, password, address_id) VALUES(?,?,?,?,?,?,?,?)")) {
            userStatement.setString(1, user.getFirstName());
            userStatement.setString(2, user.getLastName());
            userStatement.setDate(3, user.getSQLDate());
            userStatement.setInt(4, user.getSex().ordinal() + 1);
            userStatement.setString(5, user.getNextTrip());
            userStatement.setString(6, user.getEmail());
            userStatement.setString(7, user.getPasswordHash());

            try (PreparedStatement addressStatement = connection.prepareStatement(
                    "INSERT INTO Address(country, city) VALUES(?,?)")) {
                addressStatement.setString(1, user.getAddress().getCountry());
                addressStatement.setString(2, user.getAddress().getCity());
                addressStatement.executeUpdate();
                try (ResultSet generatedKeys = addressStatement.getGeneratedKeys()) {
                    if (generatedKeys.next())
                        userStatement.setInt(8, generatedKeys.getInt(1));
                        user.getAddress().setId(generatedKeys.getInt(1));
                }
            }
            userStatement.executeUpdate();

            try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    user.setId(generatedKeys.getInt(1));
            }
        }
        System.err.println("user.getId() = " + user.getId());
        System.out.println("New User from DB = "+user);
        return user;
    }

    @Override
    @SneakyThrows
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             PreparedStatement userStatement = connection.prepareStatement(
                     "UPDATE User SET first_name = (?), last_name = (?), date_of_birth = (?)," +
                             "sex_id = (?), next_trip = (?) WHERE id = (?)");
             PreparedStatement addressStatement = connection.prepareStatement(
                     "UPDATE Address SET country = (?), city = (?) WHERE id = (?)")){

            userStatement.setString(1, user.getFirstName());
            userStatement.setString(2, user.getLastName());
            userStatement.setDate(3, user.getSQLDate());
            userStatement.setInt(4, user.getSex().ordinal() + 1);
            userStatement.setString(5, user.getNextTrip());
            userStatement.setInt(6, user.getId());

            addressStatement.setString(1, user.getAddress().getCountry());
            addressStatement.setString(2, user.getAddress().getCity());
            addressStatement.setInt(3, user.getAddress().getId());

            addressStatement.executeUpdate();
            userStatement.executeUpdate();
        }
    }


    @Override
    @SneakyThrows
    public void remove(User user) {
        try (Connection connection = dataSource.getConnection();
             Statement userDelete = connection.createStatement()){
            userDelete.addBatch("DELETE FROM User WHERE id = "+user.getId());
            // Адрес не удалился
            userDelete.addBatch("DELETE FROM Address WHERE id = "+user.getAddress().getId());
            userDelete.executeBatch();
        }
    }

    @Override
    @SneakyThrows
    public boolean isEmailRegistered(String email) {
        try (Connection connection = dataSource.getConnection();
             Statement emailStatement = connection.createStatement();
             ResultSet resultSet = emailStatement.executeQuery("SELECT (email) FROM User")) {
            while (resultSet.next())
                if(email.equals(resultSet.getString(1)))
                    return true;
        }
        return false;
    }


}
