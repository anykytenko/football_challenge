package entities.user;

import entities.AbstractJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANykytenko on 8/2/2016.
 */
public class UserJdbcTemplate extends AbstractJdbcTemplate implements UserDao {

    public UserJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public void create(String firstName, String lastName) {
        String sql = "INSERT INTO " + TABLE_NAME +" (" + FIRST_NAME_FIELD_NAME + ", " + LAST_NAME_FIELD_NAME +
                ") VALUES (?, ?)";
        Object[] objects = {firstName, lastName};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }

    public boolean contains(String firstName, String lastName) {
        for (User player : get()) {
            if (player.getFirstName().equalsIgnoreCase(firstName) && player.getLastName().equalsIgnoreCase(lastName)) {
                releaseConnection();
                return true;
            }
        }
        releaseConnection();
        return false;
    }

    public User get(Integer id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {id};
        User player = (User) jdbcTemplateObject.queryForObject(sql, objects, new UserMapper());
        releaseConnection();
        return player;
    }

    public List<User> get() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<User> players = jdbcTemplateObject.query(sql, new UserMapper());
        releaseConnection();
        return players;
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD_NAME + " = ?";
        Object[] ids = {id};
        jdbcTemplateObject.update(sql, ids);
        releaseConnection();
    }

    public void update(Integer id, String firstName, String lastName) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME_FIELD_NAME + " = ?, " + LAST_NAME_FIELD_NAME +
                " = ? WHERE " + ID_FIELD_NAME + " = ?";
        Object[] objects = {firstName, lastName, id};
        jdbcTemplateObject.update(sql, objects);
        releaseConnection();
    }
}
