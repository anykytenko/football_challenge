package entities.user;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ANykytenko on 8/2/2016.
 */
public interface UserDao {
    String TABLE_NAME = "User";
    String ID_FIELD_NAME = "ID";
    String FIRST_NAME_FIELD_NAME = "FIRST_NAME";
    String LAST_NAME_FIELD_NAME = "LAST_NAME";
    String USER_NAME_FIELD_NAME = "USER_NAME";
    String PASSWORD_FIELD_NAME = "PASSWORD";
    String ROLE_ID_FIELD_NAME = "ROLE_ID";
    String EMAIL_ADDRESS_FIELD_NAME = "EMAIL_ADDRESS";
    String LINK_TO_AVATAR_FIELD_NAME = "LINK_TO_AVATAR";

    void create(String firstName, String lastName);

    User get(Integer id);

    List<User> get();

    void delete(Integer id);

    void update(Integer id, String firstName, String lastName);

    boolean contains(String firstName, String lastName);
}
