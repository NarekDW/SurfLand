package dao;

import model.News;
import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 20.03.2017 by K.N.K
 */
public interface NewsDao {
    void createNews(News news) throws SQLException;
    List<News> getUserNews(User user);
    List<News> getNews();
}
