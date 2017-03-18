package controllers;

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

/**
 * 13.03.2017 by K.N.K
 */
@WebServlet("/search")
public class UserSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");
        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        List<User> result = searchUser.getUserUniversal(
                String.valueOf(request.getParameter("name")).trim(),
                String.valueOf(request.getParameter("next_trip")).trim(),
                String.valueOf(request.getParameter("sex")),
                String.valueOf(request.getParameter("city")).trim(),
                String.valueOf(request.getParameter("country")).trim(),
                String.valueOf(request.getParameter("agefrom")).trim(),
                String.valueOf(request.getParameter("ageto")).trim(),
                String.valueOf(request.getParameter("sort"))
        );

        session.setAttribute("result", result);

        request.setAttribute("searchResult", result);
        request.getRequestDispatcher("/finduser/").forward(request, response);
    }
}
