package controllers.news;

import dao.NewsDao;
import model.News;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * 20.03.2017 by K.N.K
 */
@WebServlet("/createnews")
public class CreateNewsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsDao newsDao = (NewsDao) getServletContext().getAttribute("NewsDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        User currentUser = (User) session.getAttribute("currentUser");

        String message = request.getParameter("msg");
        if(message != null && !message.isEmpty()){
                    Date date = Date.valueOf(LocalDate.now());
                    Time time = new Time(new java.util.Date().getTime());

            try {
                newsDao.createNews(new News(currentUser.getId(), message, date, time));
            } catch (SQLException e) {
                Logger log = (Logger)getServletContext().getAttribute("log4j");
                log.error("News not created by - "+currentUser, e);
            }

        }

        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/user/").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
