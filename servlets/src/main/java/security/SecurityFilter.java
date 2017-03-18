package security;

import dao.UserSearchDao;
import encrypt.StringEncryptUtil;
import filters.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * 08.03.2017 by K.N.K
 */
//@WebFilter("/main")// "/main", "/main/", "/main/*"
public class SecurityFilter implements HttpFilter {

    private static final String ID = "id";
    private static UserSearchDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("s get userDao");
        userDao = (UserSearchDao) filterConfig.getServletContext().getAttribute("SearchUserDao");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("s in doFilter");
        HttpSession session = request.getSession();

        // Когда user уже авторизован, пропускаем его
        if(session.getAttribute(ID)!=null){
            chain.doFilter(request, response);
            System.out.println("path = "+request.getRequestURI());
//            request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
//            System.out.println("After chain.doFilter(request, response);");
        }




        // Зашел в первый раз

        Optional<String> email = Optional.ofNullable(request.getParameter("j_username"));
        Optional<String> password = Optional.ofNullable(request.getParameter("j_password"));

        System.err.println("email = "+email.orElse("nothing"));
        System.err.println("pass = "+password.orElse("nothing"));

        if(email.isPresent() && password.isPresent()){
            System.out.println("s check pass and mail");
            // TODO: 09.03.2017 Проверка пароля и логина пользователя чтобы пропустить его
            int id;
            if((id = authorize(email.get(), password.get()) )!= -1){
                System.out.println("s right pair");
                session.setAttribute(ID, id);
                chain.doFilter(request, response);
            } else {
                System.out.println("s not right pair");
                request.setAttribute("errorLogin", "Incorrect email or password");
                request.getRequestDispatcher("/").forward(request, response);
            }

        } else {
            System.out.println("SSSSSSSSSSSSSs go to login page");
//
            request.getRequestDispatcher("/").forward(request, response);
            //            TODO: 09.03.2017 Исправить, чтобы не терять инфу о странице, куда пользователь заходил прежде чем мы его отправили на логинилку

        }


    }

    private int authorize(String email, String password) {
        String hash = StringEncryptUtil.encrypt(password);
        return userDao.isUserRegistered(email, hash);
    }
}

