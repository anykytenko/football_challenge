package entities.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ANykytenko on 8/2/2016.
 */
public class UserMapper implements RowMapper {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(UserDao.ID_FIELD_NAME));
        user.setFirstName(rs.getString(UserDao.FIRST_NAME_FIELD_NAME));
        user.setLastName(rs.getString(UserDao.LAST_NAME_FIELD_NAME));
        user.setUserName(rs.getString(UserDao.USER_NAME_FIELD_NAME));
        user.setPassword(rs.getString(UserDao.PASSWORD_FIELD_NAME));
        user.setRole(User.Role.getById(rs.getInt(UserDao.ROLE_ID_FIELD_NAME)));
        user.setEmail(rs.getString(UserDao.EMAIL_ADDRESS_FIELD_NAME));
        user.setLinkToAvatar(rs.getString(UserDao.LINK_TO_AVATAR_FIELD_NAME));
        return user;
    }
}
