package reports.messages;

import entities.user.User;

import java.util.List;

/**
 * Created by ANykytenko on 9/2/2016.
 */
public class MessagesReport {
    private List<Row> messages;
    private User user;

    public List<Row> getMessages() {
        return messages;
    }

    public void setMessages(List<Row> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
