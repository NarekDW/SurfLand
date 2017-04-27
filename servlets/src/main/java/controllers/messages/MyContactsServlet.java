package controllers.messages;

import dao.MessageDao;
import dao.UserSearchDao;
import dao.h2.H2MessageDao;
import model.User;
import services.Algorithm;
import services.MessageDirection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 19.03.2017 by K.N.K
 */
@WebServlet("/mycontacts")
public class MyContactsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MessageDao messageDao = (H2MessageDao) getServletContext().getAttribute("MessageDao");
        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
        User currentUser = (User) session.getAttribute("currentUser");

        List<MessageDirection> contacts = messageDao.getContacts(currentUser);
        List<User> all = searchUser.getAll();

        List<User> result = new ArrayList<>();
        Algorithm algorithm = new Algorithm();
        for(int i = contacts.size()-1; i>=0; i--){
            User cont;
            if(currentUser.getId() == contacts.get(i).getFromId())
                cont = algorithm.binarySearch(all, contacts.get(i).getToId());
            else
                cont = algorithm.binarySearch(all, contacts.get(i).getFromId());
            result.add(cont);
        }

        session.setAttribute("result", result);

        request.setAttribute("searchResult", result);
        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("/user/messages/contacts/").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
