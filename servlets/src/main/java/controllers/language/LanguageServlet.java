package controllers.language;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 23.03.2017 by K.N.K
 */
@WebServlet("/language")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String language = request.getParameter("lang");
        if(language != null && !language.isEmpty()){
            if(language.equals("english"))
                request.getSession().setAttribute("locale", "en_US");
            else
                request.getSession().setAttribute("locale", "ru_RU");
        }

        response.sendRedirect("/");
    }
}
