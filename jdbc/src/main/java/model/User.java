package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

/**
 * 07.03.2017
 * <p>
 * Karapetyan N.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Sex sex;
    private String nextTrip;
    private String email;
    private String passwordHash;
    private Address address;


    public User(String firstName, String lastName, LocalDate dateOfBirth, Sex sex,
                String nextTrip, Address address, String email, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.nextTrip = nextTrip;
        this.address = address;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    public Date getSQLDate() {
        if (dateOfBirth != null)
            return Date.valueOf(dateOfBirth);
        return null;
    }


    public int getAge() {
        LocalDate current = LocalDate.now();
        int age = current.getYear() - dateOfBirth.getYear();
        if (current.getDayOfYear() < dateOfBirth.getDayOfYear())
            age--;
        return age;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
