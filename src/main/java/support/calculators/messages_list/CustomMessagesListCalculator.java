package support.calculators.messages_list;

import entities.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reports.messages.Row;
import support.holders.SessionsHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANykytenko on 8/26/2016.
 */
@Component
public class CustomMessagesListCalculator implements MessagesListCalculator {

    @Autowired
    private SessionsHolder sessionsHolder;

    public List<Row> calculate(List<Message> messages) {
        List<Row> messagesList = new ArrayList<Row>();
        for (Message message : messages) {
            Row row = new Row();
            row.setText(message.getText());
            row.setDate(message.getDate());
            row.setUser(sessionsHolder.getUserById(message.getUserId()));
            messagesList.add(row);
        }
        return messagesList;
    }
}
