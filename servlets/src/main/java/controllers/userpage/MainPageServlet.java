package controllers.userpage;

import dao.UserSearchDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <p>Servlet find user with parameter "id" from result in session,
 *  if result not exist try to find user from Data Base, if user with this "id"
 *  not exist, go to current user page</p>
 *
 * @since 1.8
 * 14.03.2017 by K.N.K
 */
@WebServlet("/user")
public class MainPageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserSearchDao searchUser = (UserSearchDao) getServletContext().getAttribute("UserSearchDao");

        HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
        HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());

        String id = request.getParameter("id");

        User userTo;
        if (id != null) {
            Integer userId = getUserId(id);

            //noinspection unchecked
            List<User> result = (List<User>) session.getAttribute("result");

            if(result != null){ // no binary search, case it's not all users
                Optional<User> findUser = result.stream().filter(user -> userId.equals(user.getId())).findAny();
                userTo = findUser.orElseGet(() -> searchUser.get(userId));
            } else{
                userTo =  searchUser.get(userId);
            }


        } else { // if id = null go to my page
            userTo = (User) session.getAttribute("currentUser");
        }

        request.setAttribute("user", userTo);
        request.getRequestDispatcher("/user/").forward(request, response);
    }


    private int getUserId(String id){
        Integer userId;
            try{
                userId = Integer.valueOf(id.trim());
            }catch (NumberFormatException e){
                userId = 0;
            }

        return userId;
    }
}
