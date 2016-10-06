package entities.challenge;

import java.util.Date;
import java.util.List;

/**
 * Created by ANykytenko on 8/12/2016.
 */
public interface ChallengeDao {
    String TABLE_NAME = "Challenge";
    String ID_FIELD_NAME = "ID";
    String HOST_USER_ID_FIELD_NAME = "HOST_USER_ID";
    String RECEIVING_USER_ID_FIELD_NAME = "RECEIVING_USER_ID";
    String OTHER_USER1_ID_FIELD_NAME = "OTHER_USER1_ID";
    String OTHER_USER2_ID_FIELD_NAME = "OTHER_USER2_ID";
    String STATUS_ID_FIELD_NAME = "STATUS_ID";
    String TEAM1_GOALS_FIELD_NAME = "TEAM1_GOALS";
    String TEAM2_GOALS_FIELD_NAME = "TEAM2_GOALS";
    String DATE_OF_CLOSING_FIELD_NAME = "DATE_OF_CLOSING";
    String DATE_OF_CREATION_FIELD_NAME = "DATE_OF_CREATION";

    void create(int user1Id, int user2Id);

    void create(int hostUserId, int otherUser1Id, int guestUserId, int otherUser2Id, int team1Goals, int team2Goals);


    List<Challenge> getActive(int year, int month);

    List<Challenge> getClosed(int year, int month);

    void approve(Integer id);

    void reject(Integer id);


    void close(Integer id, int team1Goals, int team2Goals);

    void assign(int challengeId, int otherUserNumber/*random in (1, 2)*/, int userId);
}
