package entities.message;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ANykytenko on 8/26/2016.
 */
public class MessageMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Message message = new Message();
        Timestamp timestamp = rs.getTimestamp(MessageDao.DATE_TIME_FIELD_NAME);
        message.setDate(new Date(timestamp.getTime()));
        message.setText(rs.getString(MessageDao.TEXT_FIELD_NAME));
        message.setUserId(rs.getInt(MessageDao.USER_ID_FIELD_NAME));
        return message;
    }
}
