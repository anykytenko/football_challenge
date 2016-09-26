package reports.messages;

import entities.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public class Row {
    private String text;
    private String date;
    private User user;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.date = simpleDateFormat.format(date);
    }
}
