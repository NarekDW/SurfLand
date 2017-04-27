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
 * <p>Define current user, search whom he subscribe (id) in data base,
 * find users by id and send to jsp(/user/friends/index.jsp)
 * </p>
 *
 * 18.03.2017 by K.N.K
 */
@WebServlet("/follow")
public class IFollowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        List<Integer> allFriendsId = friendsDao.getAllFollow(currentUser.getId());
        List<User> allIFollow = searchUser.getAll().stream()
                .filter(user -> allFriendsId.contains(user.getId()))
                .collect(Collectors.toList());

        session.setAttribute("result", allIFollow);

        request.setAttribute("allFriends", allIFollow);
        request.getRequestDispatcher("/user/friends/").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
