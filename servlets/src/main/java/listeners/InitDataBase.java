package listeners;

import dao.h2.*;
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
 * Data base (H2 test) init, when web application start-up.
 *
 * 07.03.2017 by K.N.K
 *
 */
@WebListener
public class InitDataBase implements ServletContextListener {

    @Resource(name = "jdbc/TestDB")
    private DataSource dataSource;

    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {

        // My connection pool
//        Properties properties = new Properties();
//        Path path = Paths.get(
//                sce.getServletContext().getRealPath("/WEB-INF/classes/db.properties"));
//        try(FileInputStream fileInputStream =
//                    new FileInputStream(path.toString())){
//            properties.load(fileInputStream);
//        }
//        ConnectionPool dataSource = new ConnectionPool(properties);

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
            statement.executeBatch();
            encryptPasswords(connection);

            // set DAO Objects to Context
            sce.getServletContext().setAttribute("UserDao", new H2UserDao(dataSource));
            sce.getServletContext().setAttribute("UserSearchDao", new H2UserSearchDao(dataSource));
            sce.getServletContext().setAttribute("FriendsDao", new H2FriendsDao(dataSource));
            sce.getServletContext().setAttribute("MessageDao", new H2MessageDao(dataSource));
            sce.getServletContext().setAttribute("NewsDao", new H2NewsDao(dataSource));

            // With my connection pool
//            sce.getServletContext().setAttribute("UserDao", new H2UserDao(dataSource));
//            sce.getServletContext().setAttribute("UserSearchDao", new H2UserSearchDao(dataSource));
//            sce.getServletContext().setAttribute("FriendsDao", new H2FriendsDao(dataSource));
//            sce.getServletContext().setAttribute("MessageDao", new H2MessageDao(dataSource));
//            sce.getServletContext().setAttribute("NewsDao", new H2NewsDao(dataSource));
//            sce.getServletContext().setAttribute("ConnectionPool", dataSource);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("UserDao");
        sce.getServletContext().removeAttribute("UserSearchDao");
        sce.getServletContext().removeAttribute("FriendsDao");
        sce.getServletContext().removeAttribute("MessageDao");
        sce.getServletContext().removeAttribute("NewsDao");

        // My connection pool
//        ConnectionPool dataSourceCp = (ConnectionPool) sce.getServletContext().getAttribute("ConnectionPool");
//        dataSourceCp.dispose();
    }

    @SneakyThrows
    private void encryptPasswords(Connection connection) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, password FROM User")) {
            // Encrypt users password (this is for test users in 2.sql)
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
