package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * 18.03.2017 by K.N.K
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private int id;
    private int firstUserId;
    private int secondUserId;
    private String message;
    private Date date;
    private Time time;
    private int status;

    public Message(int firstUserId, int secondUserId, String message, Date date, Time time, int status){
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.message = message;
        this.date = date;
        this.time = time;
        this.status = status;
    }
}
