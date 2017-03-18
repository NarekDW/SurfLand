package model;

/**
 * 07.03.2017
 *
 * Karapetyan N.
 */
public enum Sex {
    MALE("male"), FEMALE("female"), NONE("none");

    private final String sex;

    Sex(String sex) {
        this.sex = sex;
    }
}
