package controllers.search;

import dao.UserSearchDao;
import model.User;
import services.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>This servlet search users by parameters and put List(User) to session,
 * computer sublist by page and put in request.
 * If some errors in parameter page, Servlet set page=1 (First page)</p>
 *
 * 13.03.2017 by K.N.K
 */
@WebServlet("/search")
public class UserSearchServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 2;
    private static final int FIRST_PAGE = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");
        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());


        String name = request.getParameter("name");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String sex = request.getParameter("sex");
        String nextTrip = request.getParameter("next_trip");
        String agefrom = request.getParameter("agefrom");
        String ageto = request.getParameter("ageto");
        String sort = request.getParameter("sort");

        String postfix = Model.createPostfix(name, country, city, sex, nextTrip, agefrom, ageto, sort);

        List<User> searchSet;
        List<User> searchResult;
        String page = request.getParameter("page");

        if (page != null) { // Search by id

            int pageTo;
            try{
                pageTo = Integer.valueOf(page.trim());
            } catch (NumberFormatException e){
                pageTo = FIRST_PAGE;
            }

            //noinspection unchecked
            searchSet = (List<User>) session.getAttribute("result");

            if (searchSet == null || searchSet.isEmpty())
                searchSet = searchUser.getAll();

            int pageCount = searchSet.size() / ITEMS_PER_PAGE;
            if (searchSet.size() % ITEMS_PER_PAGE != 0)
                pageCount++;

            if (pageTo <= 0 || pageTo > pageCount)
                pageTo = FIRST_PAGE;

            int start = pageTo * ITEMS_PER_PAGE - ITEMS_PER_PAGE;
            int end = start + ITEMS_PER_PAGE;

            if (end > searchSet.size())
                end = searchSet.size();

            searchResult = searchSet.subList(start, end);

            request.setAttribute("page", pageTo); // current page
            request.setAttribute("totalItems", searchSet.size()); // total items
            request.setAttribute("postfix", postfix); // add to response

        } else {

            if (request.getParameter("do_search") != null) {
                searchSet = searchUser.getUserUniversal(name.trim(), nextTrip.trim(), sex, city.trim(), country.trim(),
                        agefrom.trim(), ageto.trim(), sort);
            } else {
                searchSet = searchUser.getAll();
            }

            request.setAttribute("page", FIRST_PAGE);
            request.setAttribute("totalItems", searchSet.size());
            request.setAttribute("postfix", postfix);

            if(searchSet.size()>1)
                searchResult = searchSet.subList(0, ITEMS_PER_PAGE);
            else searchResult = searchSet;

            session.setAttribute("result", searchSet);
        }

        request.setAttribute("searchResult", searchResult); // current users List
        request.getRequestDispatcher("/finduser/").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
