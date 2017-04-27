package controllers.news;

import dao.FriendsDao;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Servlet get all friends and subscribe for current user, and
 * show only they news.</p>
 *
 * @see services.Algorithm
 * @since 1.8
 * 20.03.2017 by K.N.K
 */
@WebServlet("/mynews")
public class MyNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsDao newsDao = (NewsDao) getServletContext().getAttribute("NewsDao");
        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");


        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        List<Integer> allFollowAndFriends = friendsDao.getAllFollowAndFriends(currentUser.getId());
        List<News> myNews = newsDao.getNews()
                .stream()
                .filter(news -> allFollowAndFriends.contains(news.getUserId()))
                .collect(Collectors.toList());

        String newsSearch = request.getParameter("news");
        if(newsSearch!=null && !newsSearch.isEmpty()){
            myNews = myNews.stream()
                    .filter(news -> news.getMessage().matches("(?u)(?i).*" + newsSearch + ".*"))
                    .collect(Collectors.toList());
        }

        List<User> allUsers = searchUser.getAll();

        List<UserAndNews> resultNews = new ArrayList<>();
        List<User> resultUsers = new ArrayList<>();
        for(int i = myNews.size()-1; i>=0; i--){
            User user = new Algorithm().binarySearch(allUsers, myNews.get(i).getUserId());
            resultUsers.add(user);
            resultNews.add(new UserAndNews(user, myNews.get(i)));
        }

        session.setAttribute("result", resultUsers);

        request.setAttribute("resultNews", resultNews);
        request.getRequestDispatcher("/user/news/").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
