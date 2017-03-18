package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.HashSet;

@WebListener()
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        HashMap sessionMap = (HashMap) servletContext.getAttribute("sessionMap");
        sessionMap.put(session.getId(), session);

        session.setAttribute("friends", new HashSet<Integer>());
        session.setAttribute("follows", new HashSet<Integer>());
        session.setAttribute("followers", new HashSet<Integer>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        HashMap sessionMap = (HashMap) servletContext.getAttribute("sessionMap");
        sessionMap.remove(session.getId());

        session.removeAttribute("friends");
        session.removeAttribute("follows");
        session.removeAttribute("followers");
    }
}
