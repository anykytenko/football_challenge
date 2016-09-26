package support.calculators.messages_list;

import entities.message.Message;
import reports.messages.Row;

import java.util.List;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public interface MessagesListCalculator {

    List<Row> calculate(List<Message> messages);
}
