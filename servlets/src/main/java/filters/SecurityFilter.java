package filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 08.03.2017 by K.N.K
 */
public class SecurityFilter implements HttpFilter {

    private static final String CURRENTUSER = "currentUser";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(session.getAttribute(CURRENTUSER) != null){
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/");
        }
    }
}

