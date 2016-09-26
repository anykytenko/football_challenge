package entities;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public abstract class AbstractJdbcTemplate {

    protected JdbcTemplate jdbcTemplateObject;

    public AbstractJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        jdbcTemplateObject.execute("SET NAMES 'utf8mb4'");
    }

    protected void releaseConnection() {
        try {
            jdbcTemplateObject.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
