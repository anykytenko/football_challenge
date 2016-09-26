package entities.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public class Message {
    private String text;
    private int userId;
    private Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
