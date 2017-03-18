package controllers.messages;

import dao.MessageDao;
import dao.h2.H2MessageDao;
import model.Message;
import model.User;
import services.MessageStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * 18.03.2017 by K.N.K
 */
@WebServlet("/message")
public class AddMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MessageDao messageDao = (H2MessageDao) getServletContext().getAttribute("MessageDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        User currentUser = (User) session.getAttribute("currentUser");
        String message = request.getParameter("msg");
        int secondUserId = Integer.parseInt(request.getParameter("user_to"));
        Date date = Date.valueOf(LocalDate.now());
        Time time = new Time(new java.util.Date().getTime());


        messageDao.addMessage(
                new Message(currentUser.getId(), secondUserId, message,
                        date, time, MessageStatus.NOTREAD));

    }
}
