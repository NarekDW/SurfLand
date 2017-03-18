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
@WebServlet("/myfriends")
public class MyFriendsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FriendsDao friendsDao = (FriendsDao) getServletContext().getAttribute("FriendsDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        // Переписать с учето FOREIGN KEY
        List<Integer> allFriendsId = friendsDao.getAllFriends(currentUser.getId());
        List<User> allFriends = searchUser.getAll().stream()
                .filter(user -> allFriendsId.contains(user.getId()))
                .collect(Collectors.toList());

        // Записываем выборку в сессию, чтобы контроллер MainPageServlet мог достать обекты текущих юзеров!
        session.setAttribute("result", allFriends);

        request.setAttribute("allFriends", allFriends);
        request.getRequestDispatcher("/user/friends/").forward(request, response);
    }
}