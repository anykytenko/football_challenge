package support.holders;

import entities.user.User;
import entities.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
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

    private List<User> allUsers;

    public List<User> getAllUsers() {
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
        for (User user : allUsers) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User user : allUsers) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<SessionInformation> getActiveSessions() {
        List<SessionInformation> activeSessions = new ArrayList<SessionInformation>();
        for(Object principal : sessionRegistry.getAllPrincipals()) {
            activeSessions.addAll(sessionRegistry.getAllSessions(principal, false));
        }
        return activeSessions;
    }

    public List<SessionInformation> getActiveSessions(Object principal) {
        return sessionRegistry.getAllSessions(principal, false);
    }

    public void update() throws SQLException {
        allUsers = userDao.get();
    }
}
