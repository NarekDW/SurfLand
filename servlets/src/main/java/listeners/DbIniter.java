package listeners;

import dao.h2.H2FriendsDao;
import dao.h2.H2MessageDao;
import dao.h2.H2UserDao;
import dao.h2.H2UserSearchDao;
import encrypt.StringEncryptUtil;
import lombok.SneakyThrows;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * 07.03.2017
 * <p>
 * Narek.
 */
@WebListener
public class DbIniter implements ServletContextListener {

    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {
        Path sqlDirPath = Paths.get(
                sce.getServletContext().getRealPath("/WEB-INF/classes/sql"));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             DirectoryStream<Path> paths = Files.newDirectoryStream(sqlDirPath,
                     entry -> entry.getFileName().toString().matches("^\\d+\\.sql$"))) {
            for (Path filePath : paths)
                    statement.addBatch(
                            Files.lines(filePath)
                                    .collect(Collectors.joining())
                    );
            statement.executeBatch(); // this.getClass().getResourceAsStream("/sql")
            encryptPasswords(connection);

            // Запихиваем объекты ДАО в контекст, чтобы вытаскивать его откуда угодно
            sce.getServletContext().setAttribute("UserDao", new H2UserDao(dataSource));
            sce.getServletContext().setAttribute("UserSearchDao", new H2UserSearchDao(dataSource));
            sce.getServletContext().setAttribute("FriendsDao", new H2FriendsDao(dataSource));
            sce.getServletContext().setAttribute("MessageDao", new H2MessageDao(dataSource));
        }
    }

    @SneakyThrows
    private void encryptPasswords(Connection connection) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, password FROM User")) {
            // Обновляем пароли пользователей с учетом MD5
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String password = resultSet.getString("password");
                statement.addBatch(
                        "UPDATE User SET password = '" +
                                StringEncryptUtil.encrypt(password) +
                                "' WHERE id = " + id);
            }
            statement.executeBatch();
        }
    }
}
