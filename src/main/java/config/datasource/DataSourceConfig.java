package config.datasource;

import entities.challenge.ChallengeDao;
import entities.challenge.ChallengeJdbcTemplate;
import entities.message.MessageDao;
import entities.message.MessageJdbcTemplate;
import entities.user.UserDao;
import entities.user.UserJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import support.holders.ChallengeHolder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ANykytenko on 8/16/2016.
 */

@Configuration
@ComponentScan({"config", "controllers", "entities.user"})
public class DataSourceConfig {

    private static final String DATA_BASE_URL_KEY = "data_base_url";
    private static final String DATA_BASE_USER_NAME_KEY = "data_base_user_name";
    private static final String DATA_BASE_PASSWORD_KEY = "data_base_password";

    @Bean
    public DriverManagerDataSource dataSource() {
        Properties properties = new Properties();
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding", "UTF-8");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setConnectionProperties(properties);
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://" + properties().getProperty(DATA_BASE_URL_KEY));
        driverManagerDataSource.setUsername(properties().getProperty(DATA_BASE_USER_NAME_KEY));
        driverManagerDataSource.setPassword(properties().getProperty(DATA_BASE_PASSWORD_KEY));
        return driverManagerDataSource;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("properties/config.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    @Bean
    public ChallengeDao challengeDao() {
        return new ChallengeJdbcTemplate(dataSource());
    }

    @Bean
    public UserDao userDao() {
        return new UserJdbcTemplate(dataSource());
    }

    @Bean
    public MessageDao messageDao() {
        return new MessageJdbcTemplate(dataSource());
    }

    @Bean
    public ChallengeHolder challengeHolder() {
        return new ChallengeHolder();
    }
}
