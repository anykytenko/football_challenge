package support.holders;

import entities.user.User;
import entities.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * todo: needs refactoring
 */
@Component
@Configurable
public class SessionsHolder {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserDao userDao;

    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication;

    private List<User> allUsers;
    private boolean needUpdate = true;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public synchronized List<User> getAllUsers() {
        if (isNeedUpdate()) {
            update();
        }
        return allUsers;
    }

    public Collection<User> getActiveUsers() {
        Map<String, User> usersMap = new HashMap<String, User>();
        for (SessionInformation sessionInformation : getActiveSessions()) {
            Object principal = sessionInformation.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User user =
                        (org.springframework.security.core.userdetails.User) principal;
                usersMap.put(user.getUsername(), getUserByName(user.getUsername()));
            }
        }
        return usersMap.values();
    }

    public User getUserByName(String userName) {
        for (User user : getAllUsers()) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User user : getAllUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<SessionInformation> getActiveSessions() {
        List<SessionInformation> activeSessions = new ArrayList<SessionInformation>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
        }
        return activeSessions;
    }

    public List<SessionInformation> getActiveSessions(Object principal) {
        return sessionRegistry.getAllSessions(principal, false);
    }

    public void update() {
        allUsers = userDao.get();
        for (User user : allUsers) {
            inMemoryAuthentication.withUser(user.getUserName()).password(user.getPassword()).roles(user.getRole().name()).and();
        }
        needUpdate = false;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public void setAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        try {
            inMemoryAuthentication = authenticationManagerBuilder.inMemoryAuthentication();
        } catch (Exception ex) {
            int a = 0;
        }
    }
}
