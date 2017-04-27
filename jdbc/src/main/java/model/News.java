package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

/**
 * 20.03.2017 by K.N.K
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private int id;
    private int userId;
    private String message;
    private Date date;
    private Time time;

    public News(int userId, String message, Date date, Time time) {
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.time = time;
    }
}
