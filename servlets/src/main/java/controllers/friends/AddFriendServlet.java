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

/**
 * <p>Define user id and add him in data base to friends or follow.</p>
 *
 * 17.03.2017 by K.N.K
 */
@WebServlet("/addfriend")
public class AddFriendServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        int fromId = currentUser.getId();
        User userTo;
        try{
            int toId = Integer.valueOf(request.getParameter("id"));
            friendsDao.addFriend(fromId, toId);
            userTo = searchUser.get(toId);
        } catch (NumberFormatException e){
            userTo = null;
        }

        request.setAttribute("user", userTo);
        request.getRequestDispatcher("/user/").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
