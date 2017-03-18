package controllers;

import dao.UserDao;
import model.Address;
import model.User;
import services.Model;
import services.Validator;

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
 * 12.03.2017 by K.N.K
 */
@WebServlet("/authorization")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");

        String firstName = request.getParameter("first_name").trim();
        String lastName = request.getParameter("last_name").trim();
        String email = request.getParameter("j_username").trim();
        String password = request.getParameter("j_password").trim();
        String country = request.getParameter("country").trim();
        String city = request.getParameter("city").trim();
        String nextTrip = request.getParameter("next_trip").trim();
        String sex = request.getParameter("sex");
        String dateOfBirth = request.getParameter("date_of_birth");

        List<String> validationErrors = Validator.validate(firstName, lastName, email, password, country,
                                                            city, nextTrip, dateOfBirth, userDao);

        if(!validationErrors.isEmpty()){
            request.setAttribute("errors", validationErrors);
            request.getRequestDispatcher("/registration/").forward(request, response);
        } else{
            Address address = Model.createAddress(country, city);
            User user = Model.createUser(firstName, lastName, dateOfBirth, sex, nextTrip, address, email, password);
            User currentUser = userDao.createUser(user);

            HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
            HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
            session.setAttribute("currentUser", currentUser);

            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/user/").forward(request, response);
        }
    }
}
