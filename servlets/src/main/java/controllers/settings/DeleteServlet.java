package controllers.settings;

import dao.UserDao;
import model.User;
import org.apache.log4j.Logger;
import services.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * <p> If parameter of request delete_account != null Servlet delete user from data base.
 * Else Servlet read user parameters from request and update it in data base.</p>
 *
 * <p>Errors of update or delete processing writing in logs/log.txt</p>
 *
 * 16.03.2017 by K.N.K
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        if(request.getParameter("delete_account") != null){

            try {
                userDao.remove(currentUser);
            } catch (SQLException e) {
                Logger log = (Logger)getServletContext().getAttribute("log4j");
                log.error("User page not removed - "+currentUser, e);
            }

            session.invalidate();
            response.sendRedirect("/");
        } else{

            currentUser = Model.deleteParameters(
                    request.getParameter("country"),
                    request.getParameter("city"),
                    request.getParameter("date_of_birth"),
                    request.getParameter("next_trip"),
                    currentUser
            );

            try {
                userDao.update(currentUser);
            } catch (SQLException e) {
                Logger log = (Logger)getServletContext().getAttribute("log4j");
                log.error("User page parameters not updated - "+currentUser, e);
            }

            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/user/").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
