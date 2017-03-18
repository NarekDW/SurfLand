package controllers.messages;

import dao.UserSearchDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 18.03.2017 by K.N.K
 */
@WebServlet("/writemessage")
public class GoToWriteMessageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        int toId = Integer.valueOf(request.getParameter("id"));
        Optional<User> userTo = searchUser.get(toId);

        request.setAttribute("user", userTo.get());
        request.getRequestDispatcher("/user/messages/").forward(request, response);
    }
}
