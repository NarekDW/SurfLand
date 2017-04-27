package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * Save all new sessions id and sessions object to HashMap from servlet context.
 * */
@WebListener()
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        HashMap sessionMap = (HashMap) servletContext.getAttribute("sessionMap");
        //noinspection unchecked
        sessionMap.put(session.getId(), session);
        session.setAttribute("locale", "ru_RU");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext servletContext = session.getServletContext();
        HashMap sessionMap = (HashMap) servletContext.getAttribute("sessionMap");
        sessionMap.remove(session.getId());
        session.removeAttribute("locale");
    }
}
