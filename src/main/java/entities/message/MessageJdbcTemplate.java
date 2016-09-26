package entities.message;

import entities.AbstractJdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public class MessageJdbcTemplate extends AbstractJdbcTemplate implements MessageDao {

    public MessageJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public List<Message> getLast(int count) {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATE_TIME_FIELD_NAME + " DESC LIMIT " + count;
        List<Message> items = jdbcTemplateObject.query(sql, new MessageMapper());
        releaseConnection();
        return items;
    }

    public void create(Integer userId, String text) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" + TEXT_FIELD_NAME + ", " + USER_ID_FIELD_NAME + ", " +
                DATE_TIME_FIELD_NAME + ") VALUES (?, ?, ?)";
        Object[] objects = {text, userId, new Date(System.currentTimeMillis())};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }
}
