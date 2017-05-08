package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 18.03.2017 by K.N.K
 */
@Data
@NoArgsConstructor
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private int status;

    public Message(int fromId, int toId, String message, LocalDate date, LocalTime time, int status) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public Message(int id, int fromId, int toId, String message, Date date, Time time, int status) {
        this(fromId, toId, message,
                LocalDate.parse(date.toString()),
                LocalTime.parse(time.toString()),
                status);
        this.id = id;
    }

    public Date getSqlDate(){
        return Date.valueOf(date);
    }

    public Time getSqlTime(){
        return Time.valueOf(time);
    }


}
