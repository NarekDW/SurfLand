package services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.News;
import model.User;

/**
 * 20.03.2017 by K.N.K
 */
@AllArgsConstructor
@Getter
public class UserAndNews {
    private User user;
    private News news;
}
