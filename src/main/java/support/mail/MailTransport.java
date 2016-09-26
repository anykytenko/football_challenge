package support.mail;

import entities.user.User;

/**
 * Created by ANykytenko on 9/15/2016.
 */
public interface MailTransport {
    void sendMessage(String subject, String text, User... users);
}
