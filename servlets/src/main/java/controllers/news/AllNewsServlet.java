package controllers.news;

import dao.NewsDao;
import dao.UserSearchDao;
import model.News;
import model.User;
import services.Algorithm;
import services.UserAndNews;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Servlet find all news, or news with search parameter matches.
 * Authors of result news, find from all users by binary search.</p>
 *
 * @see services.Algorithm
 * @since 1.8
 * 21.03.2017 by K.N.K
 */
@WebServlet("/allnews")
public class AllNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsDao newsDao = (NewsDao) getServletContext().getAttribute("NewsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        List<News> myNews = newsDao.getNews();
        List<User> allUsers = searchUser.getAll();
        String newsSearch = request.getParameter("news");

        if(newsSearch!=null && !newsSearch.isEmpty()){
            myNews = myNews.stream()
                    .filter(news -> news.getMessage().matches("(?u)(?i).*" + newsSearch + ".*"))
                    .collect(Collectors.toList());
        }

        Algorithm algorithm = new Algorithm();
        List<UserAndNews> resultNews = new ArrayList<>();
        List<User> resultUsers = new ArrayList<>(); // to Session

        for(int i = myNews.size()-1; i>=0; i--){ // reverse for getting fresh news firstly
            User user = algorithm.binarySearch(allUsers, myNews.get(i).getUserId());
            resultUsers.add(user); // for session "result"
            resultNews.add(new UserAndNews(user, myNews.get(i)));
        }

        session.setAttribute("result", resultUsers);
        request.setAttribute("resultNews", resultNews);
        request.getRequestDispatcher("/user/news/allnews/").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
