package support.strings;

import entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by ANykytenko on 9/22/2016.
 */
@Component
public class EnglishStrings implements Strings {

    private static final String APPLICATION_URL = "application_url";

    @Autowired
    private Properties properties;

    public String challengeCreatedMessage(User user) {
        return user.getFirstName() + " " + user.getLastName() + " known as ('" + user.getUserName() +
                "') has challenged you. \n\n You can approve or reject it here: " +
                properties.getProperty(APPLICATION_URL) + "/Pages/Ranks";
    }

    public String challengeCreatedSubject(User user) {
        return user.getUserName() + " has challenged you";
    }

    public String challengeApprovedMessage(User user) {
        return user.getFirstName() + " " + user.getLastName() + " known as ('" + user.getUserName() +
                "') has already approved your challenge. \n\n Now you are waiting for assigning other users. " +
                "You can find the state of your challenge here: " +
                properties.getProperty(APPLICATION_URL) + "/Pages/Challenges";
    }

    public String challengeApprovedSubject(User user) {
        return user.getUserName() + " has approved your challenge";
    }

    public String challengeRejectedMessage(User user) {
        return user.getFirstName() + " " + user.getLastName() + " known as ('" + user.getUserName() +
                ")' has rejected your challenge. \n\n You can find the state of all other challenges here: " +
                properties.getProperty(APPLICATION_URL) + "/Pages/Challenges";
    }

    public String challengeRejectedSubject(User user) {
        return user.getUserName() + " has rejected your challenge";
    }

    public String challengeReadyToStartMessage(User hostUser, User otherUser1, User receivingUser, User otherUser2) {
        return "The challenge (" + hostUser.getUserName() + ", " + otherUser1.getUserName() + ") VS (" +
                receivingUser.getUserName() + ", " + otherUser2.getUserName() + ") is ready to start. \n\n " +
                "You can find the state of all other challenges here: " +
                properties.getProperty(APPLICATION_URL) + "/Pages/Challenges";
    }

    public String challengeReadyToStartSubject() {
        return "Your challenge is ready to start";
    }

}
