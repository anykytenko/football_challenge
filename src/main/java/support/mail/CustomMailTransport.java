package support.mail;

import entities.user.User;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by ANykytenko on 9/15/2016.
 */
public class CustomMailTransport implements MailTransport {

    private static String APPLICATION_MAIL_ADDRESS = "luxoft.football.challenge@gmail.com";
    private static String APPLICATION_MAIL_PASSWORD = "gu2ar2dia3no2";

    private Properties properties;

    public CustomMailTransport() {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }

    public void sendMessage(String subject, String text, User... users) {
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APPLICATION_MAIL_ADDRESS, APPLICATION_MAIL_PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(APPLICATION_MAIL_ADDRESS));
            List<String> addresses = new ArrayList<String>();
            for (User user : users) {
                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                    addresses.add(user.getEmail());
                }
            }
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(StringUtils.join(addresses, ",")));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
