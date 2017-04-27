package services;

import model.User;

import java.util.List;

/**
 * <p>Binary search algorithm realisation.</p>
 *
 * 20.03.2017 by K.N.K
 */
public class Algorithm {

    public User binarySearch(List<User> array, int userId) {
        int l = 0;
        int r = array.size() - 1;
        while (r - l > 1) {
            int med = l + (r - l) / 2;
            if (array.get(med).getId() < userId)
                l = med;
            else
                r = med;
        }
        for (int i = l; i <= r; i++) { //find element х on range [l..r]
            if (array.get(i).getId() == userId)
                return array.get(i);
        }
        return null;
    }

}
