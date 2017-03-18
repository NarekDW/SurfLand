package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 07.03.2017
 * <p>
 * Karapetyan N.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private int id;
    private String country;
    private String city;
    public Address(String country, String city){
        this.country = country;
        this.city = city;
    }
}
