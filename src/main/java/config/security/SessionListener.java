package config.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by ANykytenko on 9/19/2016.
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(5*60*60);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("==== Session is destroyed ====");
    }
}
