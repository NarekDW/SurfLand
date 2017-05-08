package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate date;
    private LocalTime time;

    public News(int userId, String message, LocalDate date, LocalTime time) {
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public Date getSqlDate() {
        return Date.valueOf(date);
    }

    public Time getSqlTime() {
        return Time.valueOf(time);
    }
}
