package controllers;

import dao.UserDao;
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
 * 16.03.2017 by K.N.K
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");

        String newFirstName = request.getParameter("first_name").trim();
        String newLastName = request.getParameter("last_name").trim();
        String newCountry = request.getParameter("country").trim();
        String newCity = request.getParameter("city").trim();
        String newNextTrip = request.getParameter("next_trip").trim();
        String newDateOfBirth = request.getParameter("date_of_birth");
        String newSex = request.getParameter("sex");

        List<String> validationErrors = Validator.validate(newFirstName, newLastName, newCountry,
                                                            newCity, newNextTrip, newDateOfBirth);

        if(!validationErrors.isEmpty()){
            request.setAttribute("errors", validationErrors);
            request.getRequestDispatcher("/user/settings/").forward(request, response);
        } else {
            HashMap sessionMap = (HashMap) getServletContext().getAttribute("sessionMap");
            HttpSession session = (HttpSession) sessionMap.get(request.getSession().getId());
            User currentUser = (User) session.getAttribute("currentUser");

            currentUser = Model.updateUser(newFirstName, newLastName, newCountry, newCity,
                                            newNextTrip, newDateOfBirth, newSex, currentUser);

            userDao.update(currentUser);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/user/").forward(request, response);
        }

    }
}
