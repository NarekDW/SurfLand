package controllers;

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
 *
 * MainPageServlet - достает из сессии выборку по поиску пользователся
 * и объкт пользователся по указанному selectedId отправляется на отображение.
 *
 * 14.03.2017 by K.N.K
 */
@WebServlet("/user")
public class MainPageServlet extends HttpServlet {

    /**
     * @param
     * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        //noinspection unchecked
        int selectedId = Integer.valueOf(request.getParameter("id"));

        //noinspection unchecked
        List<User> result = (List<User>) session.getAttribute("result");

        for (User user : result) {
            if (user.getId() == selectedId) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/user/").forward(request, response);
                break;
            }
        }
    }
}
