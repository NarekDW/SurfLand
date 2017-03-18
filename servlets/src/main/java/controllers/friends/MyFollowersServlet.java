package controllers.friends;

import dao.FriendsDao;
import dao.UserSearchDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 18.03.2017 by K.N.K
 */
@WebServlet("/followers")
public class MyFollowersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        // Переписать с учето FOREIGN KEY
        List<Integer> allFollowersId = friendsDao.getAllFollowers(currentUser.getId());
        List<User> allMyFollowers = searchUser.getAll().stream()
                .filter(user -> allFollowersId.contains(user.getId()))
                .collect(Collectors.toList());

        session.setAttribute("result", allMyFollowers);

        request.setAttribute("allFriends", allMyFollowers);
        request.getRequestDispatcher("/user/friends/").forward(request, response);
    }
}
