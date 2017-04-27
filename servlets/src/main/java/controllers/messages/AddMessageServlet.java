package controllers.messages;

import dao.MessageDao;
import dao.UserSearchDao;
import dao.h2.H2MessageDao;
import model.Message;
import model.User;
import status.MessageStatus;

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
 * <p>
 *    Method post - write message.
 *    Method get - go to chat page with user.
 *    If parameter id incorrect set userTo - null
 * </p>
 *
 * 18.03.2017 by K.N.K
 */
@WebServlet("/message")
public class AddMessageServlet extends HttpServlet {

    private static final String SEND = "Send";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MessageDao messageDao = (H2MessageDao) getServletContext().getAttribute("MessageDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");


        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        User currentUser = (User) session.getAttribute("currentUser");
        User userTo = searchUser.get(Integer.parseInt(request.getParameter("user_to")));

        String action = request.getParameter("submit");
        if(action != null && userTo != null){
            if(action.equals(SEND)){
                String message = request.getParameter("msg");
                if(message!=null && !message.isEmpty()){
                    Date date = Date.valueOf(LocalDate.now());
                    Time time = new Time(new java.util.Date().getTime());
                    messageDao.addMessage(
                            new Message(currentUser.getId(), userTo.getId(), message,
                                    date, time, MessageStatus.NOTREAD));
                }
            }
        }

        request.setAttribute("user", userTo);
        request.getRequestDispatcher("/user/messages/").forward(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");
        User userTo;
        try{
            userTo = searchUser.get(Integer.valueOf(request.getParameter("id")));
        } catch (NumberFormatException e){
            userTo = null;
        }

        request.setAttribute("user", userTo);
        request.getRequestDispatcher("/user/messages/").forward(request, response);
    }
}
