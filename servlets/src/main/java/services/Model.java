package services;

import encrypt.StringEncryptUtil;
import model.Address;
import model.Sex;
import model.User;

import java.time.LocalDate;

/**
 * 16.03.2017 by K.N.K
 */
public class Model {
    public static Address createAddress(String country, String city){
        if(country.isEmpty())
            country = null;
        if(city.isEmpty())
            city = null;
        return new Address(country, city);
    }

    public static User createUser(String firstName, String lastName, String dateOfBirth, String sex,
                                  String nextTrip, Address address, String email, String password){
        User newUser = new User();
        // First name, Last name
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        // Date of birth
        if (!dateOfBirth.isEmpty()) {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            newUser.setDateOfBirth(dob);
        }
        // Sex
        newUser.setSex(Sex.valueOf(sex.toUpperCase()));
        // Next trip
        if(!nextTrip.isEmpty())
            newUser.setNextTrip(nextTrip);

        // Address
        newUser.setAddress(address);

        // Email, password
        newUser.setEmail(email);
        String passwordHash = StringEncryptUtil.encrypt(password);
        newUser.setPasswordHash(passwordHash);

        return newUser;
    }

    public static User updateUser(String firstName, String lastName, String country, String city,
                                  String nextTrip, String dateOfBirth, String sex, User currentUser){

        if(!firstName.isEmpty())
            currentUser.setFirstName(firstName);
        if(!lastName.isEmpty())
            currentUser.setLastName(lastName);
        if(!country.isEmpty())
            currentUser.getAddress().setCountry(country);
        if(!city.isEmpty())
            currentUser.getAddress().setCity(city);
        if(!nextTrip.isEmpty())
            currentUser.setNextTrip(nextTrip);
        if (!dateOfBirth.isEmpty()) {
            LocalDate dob = LocalDate.parse(dateOfBirth);
            currentUser.setDateOfBirth(dob);
        }
        if(!sex.isEmpty())
            currentUser.setSex(Sex.valueOf(sex.toUpperCase()));

        return currentUser;
    }

    public static User deleteParameters(String country, String city, String dateOfBirth, String nextTrip, User currentUser){

        if(country != null)
            currentUser.getAddress().setCountry(null);
        if(city != null)
            currentUser.getAddress().setCity(null);
        if(dateOfBirth != null)
            currentUser.setDateOfBirth(null);
        if(nextTrip != null)
            currentUser.setNextTrip(null);

        return currentUser;
    }
}
