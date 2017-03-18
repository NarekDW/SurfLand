package controllers;

import dao.UserDao;
import model.User;
import services.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * 16.03.2017 by K.N.K
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");


        if(request.getParameter("delete_account") != null){
            userDao.remove(currentUser);
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
            userDao.update(currentUser);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/user/").forward(request, response);
        }
    }
}
