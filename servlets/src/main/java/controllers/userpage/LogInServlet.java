package controllers.userpage;

import dao.FriendsDao;
import dao.MessageDao;
import dao.UserSearchDao;
import encrypt.StringEncryptUtil;
import model.User;
import services.FollowersFriendsCount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 21.03.2017 by K.N.K
 */
@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    private static final String LOGERROR = "Invalid e-mail or password...";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");
        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        MessageDao messageDao = (MessageDao) getServletContext().getAttribute("MessageDao");

        String email = request.getParameter("j_username").trim();
        String password = request.getParameter("j_password").trim();
        String passwordHash = StringEncryptUtil.encrypt(password);
        User currentUser = searchUser.isUserRegistered(email, passwordHash);

        if(currentUser!=null){
            FollowersFriendsCount count = friendsDao.getFollowersCount(currentUser);
            int msgCount = messageDao.getMessagesCount(currentUser);

            request.setAttribute("count", count);
            request.setAttribute("msgcount", msgCount);

            request.getSession().setAttribute("currentUser", currentUser);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/user/").forward(request, response);
        } else {
            request.setAttribute("errorLogin", LOGERROR);
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
