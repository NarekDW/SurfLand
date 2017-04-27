package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

/**
 * 18.03.2017 by K.N.K
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String message;
    private Date date;
    private Time time;
    private int status;

    public Message(int fromId, int toId, String message, Date date, Time time, int status) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.date = date;
        this.time = time;
        this.status = status;
    }
}
