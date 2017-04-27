package services;

import dao.UserDao;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 15.03.2017 by K.N.K
 */
public class Validator {

    public List<String> validate(String firstName, String lastName, String email,
                                 String password, String country, String city,
                                 String nextTrip, String dateOfBirth, UserDao userDao) {
        List<String> errors = new ArrayList<>();

        String fullNameRegex = "^[\\p{L} .'-]{2,55}$";
        String passwordRegex = "^[a-zA-Z0-9]{6,20}$";
        String emailRegex = "^[a-zA-Z0-9]{3,}@[A-Z]{0,1}[a-z]{3,8}.[a-z]{2,5}$";

        // Check full name
        String fullName = String.format("%s %s", firstName, lastName);

        if (isNotRightValue(fullName, fullNameRegex)) {
            errors.add("Invalid First name or Last name");
        }

        // Check email
        if (isNotRightValue(email, emailRegex))
            errors.add("Invalid email");
        else if (userDao.isEmailRegistered(email))
            errors.add("Email is used");

        // Check password
        if (isNotRightValue(password, passwordRegex))
            errors.add("Password must contain 6 symbols");

        errors.addAll(checkCityCountryNextTripDob(country, city, nextTrip, dateOfBirth));

        return errors;
    }

    public List<String> validate(String firstName, String lastName, String country,
                                        String city, String nextTrip, String dateOfBirth){
        List<String> errors = new ArrayList<>();
        String fullNameRegex = "^[\\p{L} .'-]{2,55}$";

        // Check full name
        if(!firstName.isEmpty() || !lastName.isEmpty()){
            String fullName = String.format("%s %s", firstName, lastName);
            if (isNotRightValue(fullName, fullNameRegex))
                errors.add("Invalid First name or Last name");
        }

        errors.addAll(checkCityCountryNextTripDob(country, city, nextTrip, dateOfBirth));

        return errors;
    }

    @SneakyThrows
    private List<String> checkCityCountryNextTripDob(String country, String city, String nextTrip, String dateOfBirth){

        List<String> errors = new ArrayList<>();
        String countryAndCityRegex = new String(("^[A-ZА-ЯЁ]{1,5}[a-zа-яеё]*(-{1}[A-ZА-ЯЕЁ]{0,1}[a-zа-яеё]*)*$").getBytes(),
                "UTF-8");
        String nextTripRegex = new String(("^[A-ZА-ЯЁ]{1,5}[a-zа-яеё]{1,}([-;\\s]{1}[A-ZА-ЯЕЁ]{0,1}[a-zа-яеё]*)*$").getBytes(),
                "UTF-8");

        // Check country and city
        if (!country.isEmpty())
            if (isNotRightValue(country, countryAndCityRegex))
                errors.add("Invalid country");
        if (!city.isEmpty())
            if (isNotRightValue(city, countryAndCityRegex))
                errors.add("Invalid city");
        if (!nextTrip.isEmpty())
            if (isNotRightValue(nextTrip, nextTripRegex))
                errors.add("Invalid next trip");

        // Check date of birth
        if (!dateOfBirth.isEmpty()) {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            if (isNotRightDate(dob))
                errors.add("Invalid date of birth");
        }

        return errors;
    }

    private boolean isNotRightValue(String value, String regex) {
        return !value.matches(regex);
    }

    private boolean isNotRightDate(LocalDate dateOfBirth) {
        return LocalDate.now().compareTo(dateOfBirth) < 0;
    }
}
