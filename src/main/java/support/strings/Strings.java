package support.strings;

import entities.user.User;

/**
 * Created by ANykytenko on 9/22/2016.
 */
public interface Strings {

    String challengeCreatedMessage(User userWhoCreated);

    String challengeCreatedSubject(User userWhoCreated);

    String challengeApprovedMessage(User userWhoApproved);

    String challengeApprovedSubject(User userWhoApproved);

    String challengeRejectedMessage(User userWhoRejected);

    String challengeRejectedSubject(User userWhoRejected);

    String challengeReadyToStartMessage(User hostUser, User otherUser1, User receivingUser, User otherUser2);

    String challengeReadyToStartSubject();
}
