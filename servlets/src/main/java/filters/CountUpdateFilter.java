package filters;

import dao.FriendsDao;
import dao.MessageDao;
import dao.h2.H2MessageDao;
import model.User;
import services.FollowersFriendsCount;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>This Filter update info about new followers,
 * friends and new messages </p>
 *
 * 21.03.2017 by K.N.K
 */
public class CountUpdateFilter implements HttpFilter {

    private static FriendsDao friendsDao;
    private static MessageDao messageDao;

    @Override
    public void init(FilterConfig config) throws ServletException {
        friendsDao = (FriendsDao) config.getServletContext().getAttribute("FriendsDao");
        messageDao = (H2MessageDao) config.getServletContext().getAttribute("MessageDao");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        FollowersFriendsCount count = friendsDao.getFollowersCount(currentUser);
        int msgCount = messageDao.getMessagesCount(currentUser);

        request.setAttribute("msgcount", msgCount);
        request.setAttribute("count", count);
        chain.doFilter(request, response);
    }
}
