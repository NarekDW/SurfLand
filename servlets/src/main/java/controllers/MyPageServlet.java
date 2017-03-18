package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * 15.03.2017 by K.N.K
 */
@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        request.setAttribute("user", session.getAttribute("currentUser"));
        request.getRequestDispatcher("/user/").forward(request, response);
    }
}
