package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 15.03.2017 by K.N.K
 */
/*
* При попытке зайти на страницу пользователя(/user/*), фильтр проверяет
* не закончилась ли у пользователя сессия и если она закончилась, то перенаправляет его
* на главную страницу(/index.jsp)
* */
public class CheckSessionFilter implements HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/");
        } else chain.doFilter(request, response);
    }
}
