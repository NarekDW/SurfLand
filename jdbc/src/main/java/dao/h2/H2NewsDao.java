package dao.h2;

import dao.NewsDao;
import lombok.SneakyThrows;
import model.News;
import model.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 20.03.2017 by K.N.K
 */
public class H2NewsDao implements NewsDao {

    @Resource(name = "jdbc/TestDB")
    private static DataSource dataSource;


    public H2NewsDao(DataSource ds) {
        dataSource = ds;
    }


    // My connection pool
//    private static ConnectionPool dataSource;
//    public H2NewsDao(ConnectionPool ds){
//        dataSource = ds;
//    }


    @Override
    public void createNews(News news) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement createNews = connection.prepareStatement(
                     "INSERT INTO News(user_id, news_message, news_date, news_time) " +
                             "VALUES (?,?,?,?)")) {
            createNews.setInt(1, news.getUserId());
            createNews.setString(2, news.getMessage());
            createNews.setDate(3, news.getDate());
            createNews.setTime(4, news.getTime());
            createNews.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public List<News> getUserNews(User user) {
        List<News> news = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, user_id, news_message, news_date, news_time FROM News" +
                             " WHERE  user_id = "+user.getId())
             ){
            while (resultSet.next()){
                news.add(
                        new News(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("news_message"),
                                resultSet.getDate("news_date"),
                                resultSet.getTime("news_time")
                        )
                );
            }
        }
        return news;
    }

    /**
     * return all news
     * */
    @Override
    @SneakyThrows
    public List<News> getNews() {
        List<News> news = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, user_id, news_message, news_date, news_time FROM News"
            )){
            while (resultSet.next()){
                news.add(
                        new News(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("news_message"),
                                resultSet.getDate("news_date"),
                                resultSet.getTime("news_time")
                        )
                );
            }

        }
        return news;
    }
}
