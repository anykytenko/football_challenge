package entities.message;

import java.util.List;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public interface MessageDao {

    String TABLE_NAME = "Message";
    String DATE_TIME_FIELD_NAME = "DATE_TIME";
    String TEXT_FIELD_NAME = "TEXT";
    String USER_ID_FIELD_NAME = "USER_ID";

    List<Message> getLast(int count);

    void create(Integer id, String text);
}
